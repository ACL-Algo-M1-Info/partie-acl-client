// PACKAGE

package outils.mondeEcran.obtentionCoordonnee;

// INTERFACE

/**
 * Interface qui permet de récupérer des coordonnées de n'importe quel objet
 * @param <T> l'objet dont on veut récupérer les coordonnées
 */
public interface ObtentionCoordonnee<T> {

    /**
     * Obtient la coordonnée X d'un objet
     * @param obj l'objet dont on veut récupérer les coordonnées
     * @return la coordonnée X de l'objet
     */
    double getX(T obj);

    /**
     * Obtient la coordonnée Y d'un objet
     * @param obj l'objet dont on veut récupérer les coordonnées
     * @return la coordonnée Y de l'objet
     */
    double getY(T obj);
}
