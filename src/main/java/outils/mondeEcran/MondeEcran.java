// PACKAGE

package outils.mondeEcran;

// IMPORTS

import fenetre.Fenetre;
import math.Vecteur2D;
import outils.mondeEcran.obtentionCoordonnee.ObtentionCoordonnee;

import java.util.ArrayList;

// CLASSE

/**
 * Calcul les coordonnées monde écran
 * @param <T> l'objet dont on veut calculer les coordonnées
 */
public class MondeEcran<T>
{

    // VARIABLES

    /** Obtenteur des coordonnées de l'objet */
    public ObtentionCoordonnee<T> obtenteur;

    /** Coordonnées minimum de la fenêtre  */
    public Vecteur2D minFenetre;

    /** Coordonnées maximum de la fenêtre */
    public Vecteur2D maxFenetre;

    /** Contient les valeurs du x min et du y min des points */
    public Vecteur2D min;

    /** Contient les valeurs du x max et du y max des points */
    public Vecteur2D max;

    /** Coefficient lambda pour le monde écran */
    double lambda;

    /** Coordonnées epsilon pour le monde écran */
    Vecteur2D epsilon;

    /** Coordonnées de l'offset pour le monde écran */
    Vecteur2D offset;


    // CONSTRUCTEUR

    /**
     * Construit tout les coefficients et les vecteurs pour le monde écran à partir d'une fenêtre donnée
     * @param obtenteur l'obtenteur des coordonnées
     * @param fenetre la fenêtre dans laquelle on va mettre du monde écran
     * @param objets les objets que l'on veut mettre dans la fenêtre
     */
    public MondeEcran(ObtentionCoordonnee obtenteur, Fenetre fenetre, ArrayList<T> objets)
    {

        this.obtenteur = obtenteur;

        this.minFenetre = fenetre.obtenirPointMinimal();
        this.maxFenetre = fenetre.obtenirPointMaximal();

        // Initialisation des vecteurs min et max
        calculerVecteurs(objets);

        lambda = MondeEcran.lambda(this.min, this.max, this.minFenetre, this.maxFenetre);

        Vecteur2D vecteurX = new Vecteur2D(this.min.X, this.max.X);
        Vecteur2D vecteurY = new Vecteur2D(this.min.Y, this.max.Y);

        Vecteur2D xFenetre = new Vecteur2D(this.minFenetre.X, this.maxFenetre.X);
        Vecteur2D yFenetre = new Vecteur2D(this.minFenetre.Y, this.maxFenetre.Y);

        epsilon = new Vecteur2D(
                MondeEcran.epsilon(vecteurX, xFenetre),
                MondeEcran.epsilon(vecteurY, yFenetre)
        );

        offset = new Vecteur2D(
                MondeEcran.calculOffset(vecteurX, xFenetre, lambda, epsilon.X),
                MondeEcran.calculOffset(vecteurY, yFenetre, lambda, epsilon.Y)
        );
    }


    // METHODES

    /**
     * Calcul les vecteurs nécessaires au monde écran d'une liste d'objet
     * @param objets les objets que l'on veut passer en monde écran
     */
    private void calculerVecteurs(ArrayList<T> objets)
    {
        if (objets.isEmpty())
            throw new IllegalArgumentException("Impossible de calculer monde écran sans référentiel");

        this.min = new Vecteur2D(this.obtenteur.getX(objets.getFirst()), this.obtenteur.getY(objets.getFirst()));
        this.max = new Vecteur2D(this.min);

        for(int i = 1; i < objets.size(); i++)
        {
            T obj = objets.get(i);

            double xObj = obtenteur.getX(obj);
            double yObj = obtenteur.getY(obj);

            this.min.X = Math.min(this.min.X, xObj);
            this.min.Y = Math.min(this.min.Y, yObj);

            this.max.X = Math.max(this.max.X, xObj);
            this.max.Y = Math.max(this.max.Y, yObj);
        }

    }

    /**
     * Permet de donner les coordonnées monde écran d'un objet
     * @param obj l'objet dont on veut donner les coordonnées monde écran
     * @return les coordonnées monde écran de l'objet
     */
    public Vecteur2D resoudreMondeEcran(T obj)
    {
        double xObj = this.obtenteur.getX(obj);
        double yObj = this.obtenteur.getY(obj);

        return new Vecteur2D(
                MondeEcran.transform(xObj, this.lambda, this.epsilon.X, this.offset.X),
                MondeEcran.transform(yObj, this.lambda, this.epsilon.Y, this.offset.Y)
        );
    }

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
        // On fixe 10e-8 comme valeur pour rendre ça compatible avec une seule coord.
        return Math.abs(xPrimes.Y - xPrimes.X) / Math.max(10e-8, Math.abs(x.Y - x.X));
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
            lambdaUnit(new Vecteur2D(v1.X, v2.X), new Vecteur2D(vp1.X, vp2.X)),
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
