package types.camion;

import types.livraisonVille.LivraisonVille;

import java.util.ArrayList;

public class Camion
{

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


    public ArrayList<String> obtenirVilles()
    {

        ArrayList<String> villes = new ArrayList<>();

        for(LivraisonVille l: livraisons)
            villes.add(l.nomVille);


        return villes;

    }

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
