// PACKAGE

package typesCode;

// ENUM

public enum CodeStatutReponse
{

    // CONSTANTES
    OK(200);

    // VARIABLES

    /** Représente le code du statut de la réponse */
    private int code;

    // CONSTRUCTEUR

    CodeStatutReponse(int i)
    {
        this.code = i;
    }

    public int getCode()
    {
        return code;
    }
}
