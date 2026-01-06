package tcp;

import java.io.IOException;

/**
 * Interface représentant un objet qu'on peut envoyer sur le réseau
 */
public interface SendableObject {
    String serializeToJSON() throws IOException;
    void setIdConversation(int idConversation);
}
