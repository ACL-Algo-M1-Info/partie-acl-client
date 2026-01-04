// PACKAGE

package types.livraisonVille;

// CLASSE

import com.fasterxml.jackson.annotation.JsonProperty;

public class LivraisonVille {

    // VARIABLES

    /** Nom de la ville */
    @JsonProperty("ville")
    public String nomVille;

    /** Représente la quantité à livrer dans la ville */
    @JsonProperty("quantite")
    public int quantiteALivrer;

    // CONSTRUCTEUR

    public LivraisonVille() { }

    public LivraisonVille(String nomVille, int quantiteALivrer)
    {
        this.nomVille = nomVille;
        this.quantiteALivrer = quantiteALivrer;
    }

    @Override
    public String toString() {
        return "Ville : " + nomVille + "; Quantité à livrer :" + quantiteALivrer;
    }
}
