// PACKAGE

package tcp;

// IMPORTS

import com.fasterxml.jackson.databind.ObjectMapper;
import typesCode.CodeTypeRequete;

import java.io.IOException;
import java.io.StringWriter;

// CLASSE

/**
 * Classe qui représente une reqûete que l'on veut envoyer au serveur
 * @param <T> la donnée envoyée au serveur
 */
public class RequeteSocket<T>
{

    // VARIABLES

    /**
     * Type de la reqûete reçue
     */
    public CodeTypeRequete type;

    public T data;

    // CONSTRUCTEUR

    /**
     * Construit une reqûete à envoyer sur le réseau ayant un type et des données
     * @param type
     * @param data
     */
    public RequeteSocket(CodeTypeRequete type, T data)
    {
        this.type = type;
        this.data = data;
    }

    // METHODES

    public String serializeToJSON() throws IOException
    {
        StringWriter writer = new StringWriter();

        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(writer, this);

        return writer.toString();
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
