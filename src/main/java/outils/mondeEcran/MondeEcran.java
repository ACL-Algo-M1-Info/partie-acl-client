package outils.mondeEcran;

import math.Vecteur2D;

public class MondeEcran
{
    /**
     * Calcul du epsilon pour les coordonnées monde écran
     * @param x Vecteur (x1, x2)
     * @param xPrimes Vecteur (x'1, x'2)
     * @return Epsilon selon l'égalité des |i2 - i1| (1 si égal, -1 sinon)
     */
    public static double epsilon(Vecteur2D x, Vecteur2D xPrimes)
    {
        if (Math.signum(x.Y - x.X) == Math.signum(xPrimes.Y - xPrimes.X))
            return 1.;

        return -1.;
    }

    /**
     * Calcul unitaire de la fraction |x'2 - x'1| / |x2 - x1|
     * @param x Vecteur (x1, x2)
     * @param xPrimes Vecteur (x'1, x'2)
     * @return |x'2 - x'1| / |x2 - x1|
     */
    private static double lambdaUnit(Vecteur2D x, Vecteur2D xPrimes)
    {
        return Math.abs(xPrimes.Y - xPrimes.X) / Math.abs(x.Y - x.X);
    }

    /**
     * Calcul du lambda pour le changement monde/écran
     * @param v1 Vecteur P1
     * @param v2 Vecteur P2
     * @param vp1 Vecteur P'1
     * @param vp2 Vecteur P'2
     * @return min{lambdaUnit(xi), lambdaUnit(yi)}
     */
    public static double lambda(Vecteur2D v1, Vecteur2D v2, Vecteur2D vp1, Vecteur2D vp2)
    {
        return Math.min(
            lambdaUnit(new Vecteur2D(v1.X, v2.X), new Vecteur2D(vp1.X, vp2.X) ),
                lambdaUnit(new Vecteur2D(v1.Y, v2.Y), new Vecteur2D(vp1.Y, vp2.Y))
        );
    }

    /**
     * Calcule la moyenne de x et y
     * @param v Vecteur (x, y)
     * @return (x + y) / 2
     */
    private static double moyennePoints(Vecteur2D v)
    {
        return (v.X + v.Y) / 2;
    }

    /**
     * Calcul l'offset
     * @param x Vecteur (x1, x2)
     * @param xPrimes Vecteur (x'1, x'2)
     * @param lambda lambda calculé
     * @param epsilon epsilon calculé
     * @return (x'1 + x'2)/2 - (lambda * epsilon * (x1 + x2)/2))
     */
    public static double calculOffset(Vecteur2D x, Vecteur2D xPrimes, double lambda, double epsilon)
    {
        return moyennePoints(xPrimes) - (lambda * epsilon * moyennePoints(x));
    }

    /**
     * Vient transformer la coordonnée x en résultat x'. x doit être dans le rectangle décrit
     * @param x coordonnée à transformer
     * @param lambda lambda calculé
     * @param epsilon epsilon calculé
     * @param offset offset calculé
     * @return (lambda * epsilon * x) + offset
     */
    public static double transform(double x, double lambda, double epsilon, double offset)
    {
        return (lambda * epsilon * x) + offset;
    }
}
