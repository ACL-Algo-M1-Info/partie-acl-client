// PACKAGE

package parseur;

// IMPORTS

import types.livraisonVille.LivraisonVille;

import java.util.HashMap;

// CLASSE

public class ReponseParseur
{

    // VARIABLES

    public int nbMachines;

    public HashMap<Integer, LivraisonVille> livraisons = new HashMap<>();

    // CONSTRUCTEURS

    public ReponseParseur(int nbMachines)
    {
        this.nbMachines = nbMachines;
    }

    public ReponseParseur(int nbMachines, HashMap<Integer, LivraisonVille> livraisons)
    {
        this.nbMachines = nbMachines;
        this.livraisons = livraisons;
    }

    @Override
    public String toString() {
        return "Nombre de camions : " + this.nbMachines + "\nLivraisons : \n" + livraisons;
    }

}
