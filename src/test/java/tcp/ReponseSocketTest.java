package tcp;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

public class ReponseSocketTest
{

    @Test
    public void testJSON() throws JsonProcessingException {
        String json = "{\"statut\":200, \"reponse\":\"RAYMI\"}";

        ReponseSocket message = new ReponseSocket(json);
        System.out.println(message.toString());
    }
}