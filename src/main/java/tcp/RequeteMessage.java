package tcp;

import types.typesCode.CodeTypeRequete;

import java.io.IOException;

/**
 * Représente une reqûete qu'on veut envoyer au serveur
 */
public class RequeteMessage implements SendableObject
{
    // VARIABLES

    /** Reqûete à construire */
    private RequeteSocket<String> requeteSocket;

    private String msg;

    // CONSTRUCTEUR

    /**
     * Créer une reqûete à partir d'un message donné
     * @param msg le message à envoyer
     */
    public RequeteMessage(String msg)
    {
        this.msg = msg;

        requeteSocket = new RequeteSocket<>(CodeTypeRequete.MESSAGE, msg);
    }

    // METHODES

    @Override
    public String serializeToJSON() throws IOException {
        return requeteSocket.serializeToJSON();
    }

    @Override
    public void setIdConversation(int idConversation)
    {
        this.requeteSocket.idConversation = idConversation;
    }

    @Override
    public String toString()
    {
        try
        {
            return this.serializeToJSON();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
