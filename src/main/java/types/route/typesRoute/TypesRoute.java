package types.route.typesRoute;

import types.typesCode.CodeStatutReponse;

import java.awt.*;

public enum TypesRoute
{
    AUTOROUTE(31, Color.BLACK),
    AUTRE(0, Color.GRAY),
    COMMUNALE(999, Color.GREEN),
    DEPARTEMENTALE(752, Color.ORANGE),
    NATIONALE(4, Color.RED),
    ROUTE_EUROPEENNE(336, Color.BLUE),
    VOIE_RAPIDE(114, Color.DARK_GRAY);


    int code;
    Color couleur;

    TypesRoute(int code, Color couleur)
    {
        this.code = code;
        this.couleur = couleur;
    }

    public Color getCouleur()
    {
        return couleur;
    }

    public static TypesRoute fromCode(int code)
    {
        for(TypesRoute t : TypesRoute.values())
        {
            if(t.code == code)
            {
                return t;
            }
        }
        throw new IllegalArgumentException("Le code n'est pas valide...");
    }

}
