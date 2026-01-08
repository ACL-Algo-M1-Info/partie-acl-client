// PACKAGE

package fenetre;

// IMPORTS

import math.Vecteur2D;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

// CLASSE

public abstract class Fenetre
{

    // VARIABLES

    /** Fenêtre de l'application */
    public Frame frame;

    /** Dessinateur de formes */
    public Graphics graphics;

    /** Stratégie de mise en tampon */
    public BufferStrategy strategy;

    /** Bordures de la fenêtre */
    public Insets insets;

    /** Padding de la fenêtre */
    public int padding;


    // CONSTRUCTEUR

    public Fenetre(int x, int y, int width, int height, int padding)
    {

        // Mise en place de l'affichage

        this.frame = new Frame("Solution du problème");
        frame.setBounds(x, y, width, height);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setIgnoreRepaint(true);

        int nbBuffers = 2;
        frame.createBufferStrategy(nbBuffers);

        try
        {
            Thread.sleep(150);
        }
        catch (InterruptedException i)
        {
            // Rien à faire
        }

        // Récupération des variables de la frame

        this.strategy = frame.getBufferStrategy();
        this.graphics = strategy.getDrawGraphics();
        this.insets = frame.getInsets();

        // On met le padding
        this.padding = padding;

        // Ecouteur qui permet de fermer la fenêtre (!!!ferme aussi le client!!!)

        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowDeiconified(WindowEvent e)
            {
                strategy.show();
                graphics.dispose();
            }
        });

        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                frame.dispose();
            }
        });

    }

    /**
     * Permet d'afficher les modifications effectuées sur la fenêtre
     */
    public void afficherEtDiposer()
    {
        this.graphics.dispose();
        this.strategy.show();
    }

    public Vecteur2D obtenirPointMinimal()
    {
        return new Vecteur2D(this.insets.left + padding, this.frame.getHeight() - this.insets.bottom - padding);
    }

    public Vecteur2D obtenirPointMaximal()
    {
        return new Vecteur2D(this.frame.getWidth() - this.insets.right - padding, insets.top + padding);
    }

    /**
     * Méthode qui permet de dessiner un sommet (représenté par un cercle plein)
     * @param point les coordonnées du sommet à placer
     */
    public abstract void dessinerSommet(Vecteur2D point);

    /**
     * Méthode qui permet de créer une arête (représenté par une ligne)
     * @param point1 les coordonnées du début de l'arête
     * @param point2 les coordonnées de la fin de l'arête
     */
    public abstract void dessinerArete(Vecteur2D point1, Vecteur2D point2, Color couleur);
}
