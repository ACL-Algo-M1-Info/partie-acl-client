package tcp;

import org.junit.Test;

import java.io.IOException;

public class ServeurTest
{
    @Test
    public void testServeur() throws IOException
    {
        ConnexionServeur.initialiser("127.0.0.1", 9111);
        ConnexionServeur c = ConnexionServeur.getInstance();

        c.demarrer();

        System.out.println(">>> 1re requête ========");
        ReponseSocket reponse = c.envoyer(new RequeteAPI("ordo", 1, 2, 3));
        System.out.println("Réponse 1 reçue : " + reponse + "\n");

        System.out.println(">>> 2e requête ========");
        ReponseSocket reponse2 = c.envoyer(new RequeteAPI("bonsoir", 1, 2, 3));
        System.out.println("Réponse 2 reçue : " + reponse2 + "\n");

        System.out.println(">>> 3e requête ========");
        ReponseSocket reponse3 = c.envoyer(new RequeteMessage("Je suis une limace"));
        System.out.println("Réponse 3 reçue : " + reponse3);
    }
}
