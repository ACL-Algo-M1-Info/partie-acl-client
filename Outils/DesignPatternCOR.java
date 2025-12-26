package Outils;

/**
 * Classe représentant un maillon d'une COR
 * @param <R> Type de retour attendu de la COR
 * @param <P> Type de paramètre attendu de la COR
 */
public abstract class DesignPatternCOR<R, P> implements InterDesignPatternCOR<R,P>{
	protected DesignPatternCOR<R, P> suivant;
	/**
	 * Valeur de retour de la COR en cas d'erreur (traitement non aboutit malgré le parcours de la totalité de la chaine)
	 */
	private R erreur;

	public DesignPatternCOR(DesignPatternCOR<R,P> maillonSuivant, R valeurErreur) {
		suivant = maillonSuivant;
		erreur=valeurErreur;
	}

	@Override
	public R traitement(P parametre){
		R resultat;
		resultat = this.traitementConcret(parametre);

		if(resultat != erreur)return resultat;

		if(this.suivant != null) return this.suivant.traitement(parametre);

		return resultat;
	}

	/**
	 * Traitement concret effectué par un maillon de la COR
	 * @param parametre Paramètre à traiter
	 * @return Valeur de retour (erreur en cas d'erreur)
	 */
	protected abstract R traitementConcret(P parametre);
}
