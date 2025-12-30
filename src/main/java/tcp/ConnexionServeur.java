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

	// SINGLETON

	private static ConnexionServeur instance;

	// VARIABLES

	public Socket socket;

	BufferedReader in;

	PrintStream out;

    private int idConversation = 0;

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

		Thread t = new Thread(this);
		t.start();
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

                synchronized (this.map)
                {
                    map.put(repSocket.idConversation, repSocket);
					map.notifyAll();
                }
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

    public ReponseSocket send(SendableObject sendableObject)
    {
        try
        {
            sendableObject.setIdConversation(idConversation);
            out.println(sendableObject);
            synchronized(this.map)
            {
                while(this.map.get(idConversation) == null)
                    this.map.wait();

                ReponseSocket reponseSocket = this.map.get(idConversation);

                map.remove(idConversation);

                this.map.notifyAll();

                idConversation++;
                return  reponseSocket;
            }
        }
        catch (InterruptedException e)
        {
            idConversation++;
            return null;
        }
    }
}
