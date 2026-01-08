// PACKAGE

package types.ville;

// CLASSE

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ville
{

    // VARIABLES

    @JsonProperty("ville")
    public String nom;

    @JsonProperty("longitude")
    public double longitude;

    @JsonProperty("latitude")
    public double latitude;

    // CONSTRUCTEUR

    public Ville() { }

    public Ville(String nom, double longitude, double latitude)
    {

        this.nom = nom;
        this.longitude = longitude;
        this.latitude = latitude;

    }

}
