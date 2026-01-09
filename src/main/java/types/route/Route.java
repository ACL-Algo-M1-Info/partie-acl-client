// PACKAGE

package types.route;

// IMPORTS

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import types.route.typesRoute.TypesRoute;

// CLASSE

public class Route
{

    // VARIABLES

    public double distance;

    public TypesRoute typeRoute;

    public String villeA;

    public String villeB;

    // CONSTRUCTEUR

    public Route() { }

    public Route(double distance, TypesRoute typeRoute)
    {

        this.distance = distance;
        this.typeRoute = typeRoute;

    }

    @JsonCreator
    public Route(@JsonProperty("distance") double distance, @JsonProperty("type") int numType, @JsonProperty("villeA") String villeA, @JsonProperty("villeB") String villeB)
    {
        this.distance = distance;
        this.typeRoute = TypesRoute.fromCode(numType);
    }

}
