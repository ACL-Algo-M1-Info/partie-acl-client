package tcp;

import java.io.IOException;

public interface SendableObject {
    String serializeToJSON() throws IOException;
    void setIdConversation(int idConversation);
}
