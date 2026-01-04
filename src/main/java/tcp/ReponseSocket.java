// PACKAGE

package tcp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import types.typesCode.CodeStatutReponse;

// CLASSE

public class ReponseSocket
{

    // CONSTANTES

    /** Segment du code statut de la réponse à la reqûete */
    public static final String SEGMENT_STATUT = "statut";

    /** Segment de la réponse à la reqûete */
    public static final String SEGMENT_REPONSE = "reponse";

    /** Segment de l'id de conversation */
    public static final String SEGMENT_IDCONVERSATION = "idConversation";

    // VARIABLES

    /** Statut de la reqûete reçue */
    public CodeStatutReponse statut;

    /** Réponse reçue du serveur */
    public JsonNode reponse;

    /** Id de la conversation */
    public int idConversation = 0;

    /** Contenu JSON du json message */
    public String contenuInitial;

    /** JSON obtenu */
    JsonNode jsonMessage;

    // CONSTRUCTEUR

    /**
     * Identifie la réponse reçue suite à une reqûete suite à une requête envoyée
     *
     * @param contenuInitial le json de la réponse
     * @throws JsonProcessingException si contenuInitial n'est pas au format JSON
     */
    ReponseSocket(String contenuInitial) throws JsonProcessingException
    {
        this.contenuInitial = contenuInitial;

        ObjectMapper mapper = new ObjectMapper();

        jsonMessage = mapper.readTree(contenuInitial);

        if(!respecteStructure())
            throw new IllegalArgumentException("Le message JSON ne respecte pas le format attendu : \n " +
                    "{\n" +
                    "\"" + SEGMENT_STATUT + "\":0\n" +
                    "\"" + SEGMENT_REPONSE + "\":...\n" +
                    "\"" + SEGMENT_IDCONVERSATION + "\":0\n" +
                    "}\n"
            );

        this.statut = CodeStatutReponse.fromCode(jsonMessage.get(SEGMENT_STATUT).asInt());
        this.idConversation = jsonMessage.get(SEGMENT_IDCONVERSATION).asInt();
        this.reponse = jsonMessage.get(SEGMENT_REPONSE);
    }

    // METHODES

    /**
     * Vérifie que la structure du JSON respecte bien la structure attendue
     * @return true si la structure est respectée, false sinon
     */
    boolean respecteStructure()
    {
        return jsonMessage.has(SEGMENT_STATUT) && jsonMessage.has(SEGMENT_REPONSE) && jsonMessage.has(SEGMENT_IDCONVERSATION);
    }

    @Override
    public String toString() {
        return "statut : " + this.statut + "\n reponse : " + this.reponse + "\n contenu : " + this.contenuInitial;
    }
}
