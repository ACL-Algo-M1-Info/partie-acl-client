// PACKAGE

package types.typesCode;

// ENUM

import com.fasterxml.jackson.annotation.JsonValue;

public enum CodeStatutReponse
{

    // CONSTANTES

    OK(200),
    ERREUR_INEXISTANT(404),
    ERREUR_FORMAT_INVALIDE(419),
    ERREUR_INTERNE(500);

    // VARIABLES

    /** Représente le code du statut de la réponse */
    private final int code;

    // CONSTRUCTEURS

    CodeStatutReponse(int i)
    {
        this.code = i;
    }

    public static CodeStatutReponse fromCode(int code)
    {
        for(CodeStatutReponse c : CodeStatutReponse.values())
        {
            if(c.code == code)
            {
                return c;
            }
        }
        throw new IllegalArgumentException("Le code n'est pas valide...");
    }

    // GETTERS

    @JsonValue
    public int getCode()
    {
        return code;
    }
}
