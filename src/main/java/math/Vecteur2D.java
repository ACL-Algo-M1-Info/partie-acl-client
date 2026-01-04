package math;

public class Vecteur2D {
    /* CHAMPS */

    /** Coordonnée X */
    public double X;

    /** Coordonnée Y */
    public double Y;

    /**
     * Génération d'un vecteur aux coordonnées (x, y).
     * @param x Coordonnée x
     * @param y Coordonnée y
     */
    public Vecteur2D(double x, double y)
    {
        X = x;
        Y = y;
    }

    public Vecteur2D(Vecteur2D point)
    {
        this.X = point.X;
        this.Y = point.Y;
    }

    /**
     * Génération d'un vecteur vide (0, 0).
     */
    public Vecteur2D()
    {
        X = 0;
        Y = 0;
    }

    /**
     * toString
     * @return Représentation sous la forme (X, Y)
     */
    @Override
    public String toString()
    {
        return "(" + X + ", " + Y + ")";
    }
}
