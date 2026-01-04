// PACKAGE

package parseur;


// IMPORTS

import types.livraisonVille.LivraisonVille;

import java.io.File;
import java.util.HashMap;

// INTERFACE

public interface Parseur
{

    // METHODES

    /**
     * Parse un fichier pour récupérer les villes et la quantité à livrer
     * @param nomFichier le nom du fichier à parser
     * @return la hashmap aves un id par ville et par livraison
     */
    HashMap<Integer, LivraisonVille> parser(String nomFichier);

    /**
     * Vérifier que le fichier existe bien et qu'il est dans le bon format
     * @param fichier le fichier à vérifier
     * @param extension l'extension à avoir sur le fichier donné
     */
    default void verifierFichier(File fichier, String extension)
    {

        if(!fichier.exists())
            throw new IllegalArgumentException("Le fichier donné n'existe pas...");

        if(!fichier.getName().endsWith(extension))
        {
            throw new IllegalArgumentException("Le fichier donné n'est pas au format : " + extension);
        }

    }
}
