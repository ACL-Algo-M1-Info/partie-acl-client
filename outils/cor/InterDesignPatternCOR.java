package outils.cor;

/**
 * Interface descriptive du traitement d'une COR
 * @param <R> Type de retour attendu de la COR
 * @param <P> Type de paramètre attendu de la COR
 */
public interface InterDesignPatternCOR <R, P>{
	/**
	 * Traitement effectué par chaque maillon de la chaine sur l'entrée (paramètre)
	 * @param parametre Paramètre à traiter de type P (type de paramètre attendu de la COR)
	 * @return @param <R> Retour de la fonction traitement de type R (type de retour attendu de la COR)
	 */
	public R traitement(P parametre);
}
