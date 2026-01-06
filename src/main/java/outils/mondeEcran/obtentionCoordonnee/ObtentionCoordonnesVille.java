package outils.mondeEcran.obtentionCoordonnee;

import types.ville.Ville;

public class ObtentionCoordonnesVille implements ObtentionCoordonnee<Ville>
{

    @Override
    public double getX(Ville obj) {
        return obj.longitude;
    }

    @Override
    public double getY(Ville obj) {
        return obj.latitude;
    }

}
