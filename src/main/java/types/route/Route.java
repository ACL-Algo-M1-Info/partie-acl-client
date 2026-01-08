// PACKAGE

package types.route;

// IMPORTS

import com.fasterxml.jackson.annotation.JsonProperty;
import types.route.typesRoute.TypesRoute;

// CLASSE

public class Route
{

    // VARIABLES

    @JsonProperty("distance")
    public double distance;

    @JsonProperty("type")
    public TypesRoute typeRoute;

    // CONSTRUCTEUR

    public Route() { }

    public Route(double distance, TypesRoute typeRoute)
    {

        this.distance = distance;
        this.typeRoute = typeRoute;

    }

}
