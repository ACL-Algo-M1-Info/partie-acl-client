package parseur;

import org.junit.Test;
import types.livraisonVille.LivraisonVille;

import java.util.HashMap;
import java.util.Map;


public class ParseurCSVTest {

    @Test
    public void testParseurCSV()
    {
        ParseurCSV parseur = new ParseurCSV(';');

        HashMap<Integer, LivraisonVille> map = parseur.parser("data/testParseur/test.csv");

        for(Map.Entry m: map.entrySet())
        {
            System.out.println("Id : " + m.getKey() + "; Ville à livrer : [ " + m.getValue() + " ]");
        }
    }

}