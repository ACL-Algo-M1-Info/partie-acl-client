package parseur;

import org.junit.Test;
import types.livraisonVille.LivraisonVille;

import java.io.File;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParseurCSVTest {

    @Test
    public void testaupif()
    {
        ParseurCSV parseur = new ParseurCSV(";");

        ReponseParseur reponse = parseur.parser("data/testParseur/test.csv");

        System.out.println(reponse);
    }

}