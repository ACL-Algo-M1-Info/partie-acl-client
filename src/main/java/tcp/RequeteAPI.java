// PACKAGE

package tcp;

// IMPORTS

import typesCode.CodeTypeRequete;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

// CLASSE

public class RequeteAPI implements SendableObject
{

    // VARIABLES

    /** Reqûete à construire */
    private RequeteSocket<RequeteAPI> requeteSocket;

    public String cmd;

    public ArrayList<Object> args = new ArrayList<>();

    // CONSTRUCTEUR

    public RequeteAPI(String cmd, Object... args)
    {
        this.cmd = cmd;

        Collections.addAll(this.args, args);

        requeteSocket = new RequeteSocket<>(CodeTypeRequete.API, this);
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
