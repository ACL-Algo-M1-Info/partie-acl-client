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

	public final static double TIMEOUT_MS = 10_000;

	// SINGLETON

	private static ConnexionServeur instance;

	// VARIABLES

	public Socket socket;

	BufferedReader in;

	PrintStream out;

	/** ID de conversation, 0 implique erreur */
    private int idConversation = 1;

    private final HashMap<Integer, ReponseSocket> map = new HashMap<>();

	// METHODES DU SINGLETON

	public static void initialiser(String adresseServeur, int adressePort) throws IOException {
		if(instance != null)
		{
			return;
		}
		instance = new ConnexionServeur(adresseServeur, adressePort);
	}

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
						map.put(repSocket.idConversation, repSocket);
						map.notifyAll();
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void demarrer()
	{
		start();
	}

    public ReponseSocket envoyer(SendableObject sendableObject)
    {
        return envoyer(sendableObject, TIMEOUT_MS);
    }

	public ReponseSocket envoyer(SendableObject sendableObject, double timeoutMS)
	{
		timeoutMS = Math.max(timeoutMS, 10e-6);

		try
		{
			int id;

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
				while (true)
				{
					ReponseSocket reponse = map.get(id);

					// Réponse valide, on renvoie (sans oublier de notifier)
					if (reponse != null)
					{
						map.remove(id);
						map.notifyAll();
						return reponse;
					}

					double waitTime = deadline - System.currentTimeMillis();

					// Timeout
					if (waitTime <= 0)
					{
						// Au cas où
						map.remove(id);

						// Rien à renvoyer
						map.notifyAll();
						return null;
					}

					// Pas de réponse ni de timeout, on attend
					map.wait((long)waitTime);
				}
			}
		}
		catch (InterruptedException e)
		{
			return null;
		}
	}
}
