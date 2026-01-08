// PACKAGE

package tcp;

// IMPORTS

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

// CLASSE

public class ConnexionServeur extends Thread
{

	// CONSTANTES

	/** Temps définit pour le time out */
	public final static double TIMEOUT_MS = 10_000;

	// SINGLETON

	/** Instance de la classe pour le singleton */
	private static ConnexionServeur instance;

	// VARIABLES

	/** Socket de connexion */
	public Socket socket;

	/** Permet de lire les données reçues par la socket */
	BufferedReader in;

	/** Permet d'envoyer des données dans la socket */
	PrintStream out;

	/** ID de conversation, 0 implique erreur */
    private int idConversation = 1;

	/** Hashmap qui permet de numéroter les interactions avec le serveur */
    private final HashMap<Integer, ReponseSocket> map = new HashMap<>();

	// METHODES DU SINGLETON

	/**
	 * Initialise le singleton avec des valeurs données
	 * @param adresseServeur l'adresse du serveur
	 * @param adressePort l'adresse du port
	 * @throws IOException si la création de la socket se passe mal
	 */
	public static void initialiser(String adresseServeur, int adressePort) throws IOException
	{
		if(instance != null)
		{
			return;
		}

		instance = new ConnexionServeur(adresseServeur, adressePort);

	}

	/**
	 * Récupère l'instance de connexion au serveur
	 * @return l'instance de la connexion au serveur
	 */
	public static ConnexionServeur getInstance()
	{
		if (instance == null)
		{
			throw new RuntimeException("Il faut d'abord initialiser ConnexionServeur");
		}
		return instance;
	}

	// CONSTRUCTEURS

	private ConnexionServeur(String adresseServeur, int adressePort) throws IOException
	{
		InetAddress adresse = InetAddress.getByName(adresseServeur);
		socket = new Socket(adresse, adressePort);

		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintStream(socket.getOutputStream());
	}

    // METHODES

	@Override
	public void run()
    {
		try {
			while (!this.isInterrupted() && !socket.isClosed())
			{
                String message = in.readLine();

                ReponseSocket repSocket = new ReponseSocket(message);

				// Réponse valide (0 veut dire que le serveur n'a pas pu parser le message initial)
				if (repSocket.idConversation > 0)
				{
					synchronized (this.map)
					{
						// On insère uniquement s'il y a une intention d'insertion. Utile si jamais le thread envoyeur s'est désisté et que le serveur a envoyé une réponse APRES coup
						if (map.containsKey(repSocket.idConversation))
							map.put(repSocket.idConversation, repSocket);

						map.notifyAll();
					}
				}
			}
		}
		catch (IOException e)
		{
			// Erreur pendant socket ouvert
			if (!socket.isClosed())
				e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		System.err.println("Socket fermé prématurément");
		try
		{
			socket.close();
		}
		catch (IOException e)
		{ /* */ }
	}

	/**
	 * Démarre le thread du serveur
	 */
	public void demarrer()
	{
		start();
	}

	/**
	 * Arrête le thread du serveur
	 */
	public void arreter()
	{
		try
		{
			socket.close();
		}
		catch(IOException e)
		{ /* Rien */ }

		System.out.println("Fermeture du socket de connexion avec le serveur");

		interrupt();

		// S'autolibère
		instance = null;
	}

	/**
	 * Envoie une requête au serveur
	 * @param sendableObject l'objet que l'on veut envoyer au serveur
	 * @return la réponse du serveur
	 */
    public ReponseSocket envoyer(SendableObject sendableObject)
    {
        return envoyer(sendableObject, TIMEOUT_MS);
    }

	/**
	 * Envoie une reqûete au serveur
	 * @param sendableObject l'objet que l'on veut envoyer au serveur
	 * @param timeoutMS le temps avant de considérer que le serveur ne réponde pas
	 * @return la réponse du serveur
	 */
	public ReponseSocket envoyer(SendableObject sendableObject, double timeoutMS)
	{
		try
		{
			int id;
			boolean timeoutAllowed = timeoutMS >= 0;

			// Incrémentation de l'id de conversation thread-safe.
			synchronized (this)
			{
				id = idConversation++;
				notifyAll();
			}

			sendableObject.setIdConversation(id);
			out.println(sendableObject);

			// Enregistrement du temps
			double deadline = System.currentTimeMillis() + timeoutMS;

			// Lecture/modification de la map
			synchronized(this.map)
			{
				// Met "null" dans la map, pour déterminer l'intention d'obtenir une donnée au récepteur.
				map.put(id, null);

				while (true)
				{
					ReponseSocket reponse = map.get(id);
					double waitTime = deadline - System.currentTimeMillis();

					// Réponse valide OU timeout, on renvoie (sans oublier de notifier)
					if (reponse != null || (waitTime <= 0 && timeoutAllowed))
					{
						// Réponse valide ou timeout, soit la réponse est valide, soit null (rien).
						// Il ne faut pas oublier de clear le dictionnaire & de notifier les autres threads.

						map.remove(id);
						map.notifyAll();
						return reponse;
					}

					// Pas de réponse ni de timeout, on attend.
					if (timeoutAllowed)
						map.wait((long)waitTime);
					else
						map.wait();
				}
			}
		}
		catch (InterruptedException e)
		{
			return null;
		}
	}
}
