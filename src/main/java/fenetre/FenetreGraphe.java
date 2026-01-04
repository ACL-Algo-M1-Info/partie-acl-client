// PACKAGE

package fenetre;

// IMPORTS

import math.Vecteur2D;

import java.awt.*;

// CLASSE

public class FenetreGraphe extends Fenetre
{

    // CONSTANTES

    public final static int RAYON_POINT = 4;

    public final static int DIAMETRE_POINT = RAYON_POINT * 2;


    // CONSTRUCTEUR

    public FenetreGraphe(int x, int y, int width, int height)
    {
        super(x, y, width, height);
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
    public void dessinerArete(Vecteur2D point1, Vecteur2D point2)
    {

        this.graphics = this.strategy.getDrawGraphics();
        this.graphics.setColor(Color.BLUE);
        this.graphics.drawLine((int)point1.X, (int)point1.Y, (int)point2.X, (int)point2.Y);

        this.afficherEtDiposer();

    }
}
