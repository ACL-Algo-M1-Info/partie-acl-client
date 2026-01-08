package types.camion;

import types.livraisonVille.LivraisonVille;

import java.util.ArrayList;

public class Camion
{

    /** Liste des livraisons que le camion doit effectuer */
    public ArrayList<LivraisonVille> livraisons = new ArrayList<>();

    public Camion()
    {

    }

    public Camion(ArrayList<LivraisonVille> livraisons)
    {
        this.livraisons = livraisons;
    }

    @Override
    public String toString() {
        String res = "--Camion--\n Livraisons : ";
        for(LivraisonVille l: livraisons)
        {
            res += l + "\n";
        }

        return res;
    }


    /**
     * Permet d'obtenir le nom des villes que le camion doit livrer
     * @return une liste de String avec le nom des villes que le camion doit livrer
     */
    public ArrayList<String> obtenirVilles()
    {

        ArrayList<String> villes = new ArrayList<>();

        for(LivraisonVille l: livraisons)
            villes.add(l.nomVille);


        return villes;

    }

    /**
     * Permet d'obtenir une liste de liste des villes que plusieurs camions doivent parcourir
     * @param camions les camions dont on veut lister les villes
     * @return une liste de liste des villes que les camions doivent livrer
     */
    public static ArrayList<ArrayList<String>> obtenirVillesParCamion(ArrayList<Camion> camions)
    {

        ArrayList<ArrayList<String>> villesParCamion = new ArrayList<>();

        for(Camion camion: camions)
        {
            villesParCamion.add(camion.obtenirVilles());
        }

        return villesParCamion;

    }
}
