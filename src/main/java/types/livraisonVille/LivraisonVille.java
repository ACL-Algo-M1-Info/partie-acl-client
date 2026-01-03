// PACKAGE

package types.livraisonVille;

// CLASSE

public class LivraisonVille {

    // VARIABLES

    /** Nom de la ville */
    public String nomVille;

    /** Représente la quantité à livrer dans la ville */
    public int quantiteALivrer;

    // CONSTRUCTEUR

    public LivraisonVille(String nomVille, int quantiteALivrer)
    {
        this.nomVille = nomVille;
        this.quantiteALivrer = quantiteALivrer;
    }

    @Override
    public String toString() {
        return "Ville : " + nomVille + "; Quantite a livrer :" + quantiteALivrer + "\n";
    }
}
