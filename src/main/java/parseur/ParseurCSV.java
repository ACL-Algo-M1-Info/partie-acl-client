// PACKAGE

package parseur;

// IMPORTS

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import types.livraisonVille.LivraisonVille;

import java.io.*;
import java.util.HashMap;

// CLASSE

public class ParseurCSV implements Parseur
{

    // VARIABLES

    private final char separateur;


    // CONSTRUCTEUR

    public ParseurCSV(char separateur)
    {
        this.separateur = separateur;
    }


    // METHODES

    @Override
    public HashMap<Integer, LivraisonVille> parser(String nomFichier)
    {

        // Vérification du fichier

        File fichier = new File(nomFichier);
        verifierFichier(fichier, ".csv");

        HashMap<Integer, LivraisonVille> resultat = new HashMap<>();

        // Parsing des lignes du fichier

        // Compteur pour les ids de la hashmap de ReponseParseur

        int compteur = -1;

        // Mapping du parseur

        CsvMapper mapper = new CsvMapper();

        // Construction du schéma du parseur

        CsvSchema schema = CsvSchema.emptySchema()
                .withHeader()
                .withColumnSeparator(separateur);

        // Définition de l'itérateur pour le parseur

        MappingIterator<LivraisonVille> it;

        try
        {
            it = mapper.readerFor(LivraisonVille.class)
                    .with(schema)
                    .readValues(fichier);
        }
        catch (IOException io)
        {
            throw new RuntimeException("Impossible de parser le fichier...");
        }

        // On peut maintenant parser le fichier

        while(it.hasNext())
        {
            LivraisonVille livraison = it.next();
            resultat.put(++compteur, livraison);
        }

        return resultat;

    }
}
