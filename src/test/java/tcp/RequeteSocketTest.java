package tcp;

import org.junit.Test;
import typesCode.CodeTypeRequete;

import java.util.ArrayList;
import java.util.HashMap;

public class RequeteSocketTest
{

    @Test
    public void testJSON()
    {
        RequeteSocket<String> requeteSocket = new RequeteSocket<>(CodeTypeRequete.API, "RAYMI");

        try
        {
            System.out.println(requeteSocket);
        }
        catch (Exception e)
        {
            // COUCOU
        }

        ArrayList<String> liste = new ArrayList<>();
        liste.add("test");
        liste.add("RAYMI");
        liste.add("oui oui baguette");

        RequeteSocket<ArrayList<String>> requeteSocket2 = new RequeteSocket<>(CodeTypeRequete.API, liste);
        try
        {
            System.out.println(requeteSocket2);
        }
        catch (Exception e)
        {
            // COUCOU
        }

        HashMap<String, Integer> map = new HashMap<>();
        map.put("test", 1);
        map.put("oui oui baguette", 2);
        RequeteSocket<HashMap<String, Integer>> requeteSocket3 = new RequeteSocket<>(CodeTypeRequete.API, map);
        try
        {
            System.out.println(requeteSocket3);
        }
        catch (Exception e)
        {
            // COUCOU
        }
    }
}