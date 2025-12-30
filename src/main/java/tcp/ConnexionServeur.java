// PACKAGE

package tcp;

// IMPORTS

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

// CLASSE

public class ConnexionServeur extends Thread
{

	// SINGLETON

	private static ConnexionServeur instance;

	// VARIABLES

	public Socket socket;
	BufferedReader in;
	PrintStream out;

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

		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		try {
			while (!this.isInterrupted() && !socket.isClosed())
			{
				// Réception des données
				// Message : chaine de caractère qui va prendre une ligne (fin de ligne \r\n)
				// { "type": "api", "data": {"commande": string, "args": [...Object]}  }
				// On recevra du serveur un message du type { status: 200, resp: ? }


			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
