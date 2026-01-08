// PACKAGE

package fenetre;

// IMPORTS

import math.Vecteur2D;
import types.route.typesRoute.TypesRoute;
import types.ville.Ville;

import java.awt.*;
import java.util.HashMap;

// CLASSE

public class FenetreGraphe extends Fenetre
{

    // CONSTANTES

    public final static int RAYON_POINT = 4;

    public final static int DIAMETRE_POINT = RAYON_POINT * 2;

    public final static HashMap<TypesRoute, Color> couleurs = new HashMap<>(){{
        put(TypesRoute.AUTOROUTE, Color.black);
        put(TypesRoute.AUTRE, Color.GRAY);
        put(TypesRoute.ROUTE_EUROPEENNE, Color.BLUE);
        put(TypesRoute.COMMUNALE, Color.GREEN);
        put(TypesRoute.DEPARTEMENTALE, Color.ORANGE);
        put(TypesRoute.NATIONALE, Color.RED);
        put(TypesRoute.VOIE_RAPIDE, Color.DARK_GRAY);
    }};


    // CONSTRUCTEUR

    public FenetreGraphe(int x, int y, int width, int height, int padding)
    {
        super(x, y, width, height, padding);
    }

    // METHODES

    @Override
    public void dessinerSommet(Vecteur2D point)
    {

        int x = (int)(point.X - RAYON_POINT);
        int y = (int)(point.Y - RAYON_POINT);

        this.graphics = this.strategy.getDrawGraphics();
        this.graphics.setColor(Color.black);
        this.graphics.fillOval(x, y, DIAMETRE_POINT, DIAMETRE_POINT);

        this.afficherEtDiposer();

    }

    @Override
    public void dessinerArete(Vecteur2D point1, Vecteur2D point2, Color couleur)
    {

        this.graphics = this.strategy.getDrawGraphics();
        this.graphics.setColor(couleur);
        this.graphics.drawLine((int)point1.X, (int)point1.Y, (int)point2.X, (int)point2.Y);

        this.afficherEtDiposer();

    }

    public void dessinerVille(Vecteur2D point, String nomVille)
    {
        dessinerSommet(point);
        afficherNomVille(point, nomVille);
    }

    public void afficherNomVille(Vecteur2D point, String nomVille)
    {

        this.graphics = this.strategy.getDrawGraphics();
        this.graphics.setColor(Color.black);

        if(point.X > (double) this.frame.getWidth() /2)
        {
            FontMetrics fm = graphics.getFontMetrics();

            point = new Vecteur2D(point.X - fm.stringWidth(nomVille), point.Y);
        }

        this.graphics.drawString(nomVille, (int) (point.X + RAYON_POINT +10), (int) (point.Y + RAYON_POINT + 10));
        this.afficherEtDiposer();
    }

    public void dessinerRoute(Vecteur2D point1, Vecteur2D point2, TypesRoute route, double distance)
    {
        dessinerArete(point1, point2, couleurs.get(route));
        afficherDistance(point1, point2, distance);
    }

    public void afficherDistance(Vecteur2D point1, Vecteur2D point2, double distance)
    {
        Vecteur2D moyenne = Vecteur2D.moyenneVecteurs(point1, point2);

        this.graphics = this.strategy.getDrawGraphics();
        this.graphics.setColor(Color.black);
        this.graphics.drawString(String.valueOf((int)distance), (int)moyenne.X + 10, (int)moyenne.Y + 10);

    }
}
