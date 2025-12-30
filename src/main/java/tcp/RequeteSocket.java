// PACKAGE

package tcp;

// IMPORTS

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

// CLASSE

/**
 * Classe qui représente une reqûete que l'on veut envoyer au serveur
 * @param <T> la donnée envoyée au serveur
 */
public class RequeteSocket<T>
{

    // CONSTANTES

    /** Segment JSON pour le champ du type */
    public final static String SEGMENT_TYPE = "type";

    /** Segment JSON pour le champ de la data */
    public final static String SEGMENT_DATA = "data";

    // VARIABLES

    /**
     * Type de la reqûete reçue
     */
    public String type;

    public T data;

    // CONSTRUCTEUR

    /**
     * Construit une reqûete à envoyer sur le réseau ayant un type et des données
     * @param type
     * @param data
     */
    public RequeteSocket(String type, T data)
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
