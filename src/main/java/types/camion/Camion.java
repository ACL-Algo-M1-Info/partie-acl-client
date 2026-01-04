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
}
