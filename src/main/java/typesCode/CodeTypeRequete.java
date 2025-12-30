// PACKAGE

package typesCode;

// ENUM

import com.fasterxml.jackson.annotation.JsonValue;

public enum CodeTypeRequete {

    // CONSTANTES

    API(10);

    // VARIABLES

    /** Représente le code du statut de la réponse */
    int code;

    // CONSTRUCTEUR

    CodeTypeRequete(int code)
    {
        this.code = code;
    }

    // GETTERS

    @JsonValue
    public int getCode()
    {
        return code;
    }
}
