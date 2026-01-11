// PACKAGE

package fenetre;

// IMPORTS

import math.Vecteur2D;
import types.route.Route;
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

    public void dessinerVille(Vecteur2D point, Ville ville)
    {
        dessinerSommet(point);
        afficherNomVille(point, ville.nom);
    }

    public void afficherNomVille(Vecteur2D point, String nomVille)
    {

        this.graphics = this.strategy.getDrawGraphics();
        this.graphics.setColor(Color.black);

        int offsetX = RAYON_POINT + 10;
        int offsetY = offsetX;

        FontMetrics fm = graphics.getFontMetrics();
        int taille = fm.stringWidth(nomVille);

        if(point.X + taille >= (double) this.frame.getWidth())
        {
            point = new Vecteur2D(point.X - taille, point.Y);
            offsetX = -offsetX;
        }

        this.graphics.drawString(nomVille, (int) (point.X + offsetX), (int) (point.Y + offsetY));
        this.afficherEtDiposer();
    }

    public void dessinerRoute(Vecteur2D point1, Vecteur2D point2, Route route, double distance)
    {
        dessinerArete(point1, point2, route.typeRoute.getCouleur());
        afficherDistance(point1, point2, distance);
    }

    public void afficherDistance(Vecteur2D point1, Vecteur2D point2, double distance)
    {
        Vecteur2D moyenne = Vecteur2D.moyenneVecteurs(point1, point2);

        this.graphics = this.strategy.getDrawGraphics();
        this.graphics.setColor(Color.black);
        this.graphics.drawString(String.valueOf((int)distance), (int)moyenne.X + 10, (int)moyenne.Y + 10);
        this.afficherEtDiposer();
    }

    public void afficherResume(String texte)
    {
        this.graphics = this.strategy.getDrawGraphics();
        this.graphics.setColor(Color.BLACK);
        this.graphics.drawString(texte, 30, (int)obtenirPointMinimal().Y - 5);
        this.afficherEtDiposer();
    }

    public void afficherDistanceTotale(double distanceTotale, int idCamion)
    {
        String contenu = "Distance totale : " + (int)distanceTotale + " pour le camion " + idCamion;
        afficherResume(contenu);
    }
}
