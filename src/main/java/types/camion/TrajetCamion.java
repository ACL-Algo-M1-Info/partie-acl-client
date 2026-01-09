// PACKAGE

package types.camion;

// CLASSE

import com.fasterxml.jackson.annotation.JsonProperty;
import types.route.Route;
import types.ville.Ville;

import java.util.ArrayList;

public class TrajetCamion
{

    // VARIABLES

    @JsonProperty("distanceTotale")
    public double distanceTotale;

    @JsonProperty("villes")
    public ArrayList<Ville> villesParcourues;

    @JsonProperty("routes")
    public ArrayList<Route> routesParcourues;

    // CONSTRUCTEUR

    public TrajetCamion() { }


    // METHODES

    public static ArrayList<Ville> obtenirVillesParcourues(ArrayList<TrajetCamion> trajets)
    {

        ArrayList<Ville> res = new ArrayList<>();

        for(TrajetCamion trajet: trajets)
        {
            res.addAll(trajet.villesParcourues);
        }

        return res;
    }

    @Override
    public String toString() {

        return "TrajetCamion{" +
                "distanceTotale=" + distanceTotale +
                ", villesParcourues=" + villesParcourues +
                ", routesParcourues=" + routesParcourues +
                '}';

    }

}
