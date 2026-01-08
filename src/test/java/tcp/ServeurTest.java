package tcp;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class ServeurTest
{
    @Test
    public void testServeur() throws IOException
    {
        ConnexionServeur.initialiser("127.0.0.1", 9111);
        ConnexionServeur c = ConnexionServeur.getInstance();

        c.demarrer();

        {
            System.out.println(">>> 1re requête ========");
            ReponseSocket reponse = c.envoyer(new RequeteAPI("ordo", 1, 2, 3));
            System.out.println("Réponse 1 reçue : " + reponse + "\n");
        }

        {
            System.out.println(">>> 2e requête ========");
            ReponseSocket reponse = c.envoyer(new RequeteAPI("bonsoir", 1, 2, 3));
            System.out.println("Réponse 2 reçue : " + reponse + "\n");
        }

        {
            System.out.println(">>> 3e requête ========");
            ReponseSocket reponse = c.envoyer(new RequeteMessage("Je suis une limace"));
            System.out.println("Réponse 3 reçue : " + reponse + "\n");
        }

        {
            System.out.println(">>> 4e requête ========");
            int[] p = new int[] {3, 2, 2, 2, 3};
            int n = p.length;
            int m = 2;

            System.out.println("Instance: n = " + n + ", m = " + m + ", p = " + Arrays.toString(p));

            ReponseSocket reponse = c.envoyer(new RequeteAPI("ordo", m, p));

            System.out.println("Réponse 4 reçue : " + reponse + "\n");
        }

        {
            System.out.println(">>> 5e requête =========");
            String[][] villes = new String[][] {
                    {"Metz", "Nancy"},
                    {"Lorient", "Quimper", "Vannes"}
            };

            ReponseSocket reponse = c.envoyer(new RequeteAPI("tsp", (Object)villes), -1);
            System.out.println("Réponse 5 reçue : " + reponse + "\n");
        }
    }
}
