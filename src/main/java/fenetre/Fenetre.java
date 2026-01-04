// PACKAGE

package fenetre;

// IMPORTS

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

// CLASSE

public class Fenetre
{

    // VARIABLES

    public Graphics graphics;
    public BufferStrategy strategy;
    public Insets insets;


    // CONSTRUCTEUR

    public Fenetre(int x, int y, int width, int height)
    {

        // Mise en place de l'affichage

        Frame frame = new Frame("Solution du problème");
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

    }

    /**
     * Permet d'afficher les modifications effectuées sur la fenêtre
     */
    public void afficherEtDiposer()
    {
        this.graphics.dispose();
        this.strategy.show();
    }
}
