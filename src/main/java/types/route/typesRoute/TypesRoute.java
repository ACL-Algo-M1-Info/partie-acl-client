package types.route.typesRoute;

public enum TypesRoute
{
    AUTOROUTE(31),
    AUTRE(0),
    COMMUNALE(999),
    DEPARTEMENTALE(752),
    NATIONALE(4),
    ROUTE_EUROPEENNE(336),
    VOIE_RAPIDE(114);


    int code;

    TypesRoute(int code)
    {
        this.code = code;
    }
}
