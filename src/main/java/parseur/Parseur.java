// PACKAGE

package parseur;


// IMPORTS

import types.livraisonVille.LivraisonVille;

import java.io.BufferedReader;
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
    ReponseParseur parser(String nomFichier);

    /**
     * Vérifier que le fichier existe bien et qu'il est dans le bon format
     * @return true si le fichier existe et qu'il est dans le bon format
     */
    BufferedReader verifierFichier(String nomFichier);

}
