package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils
{

    public static <T> T jsonVersT(JsonNode message) throws JsonProcessingException
    {
        return jsonVersT(message.toString());
    }

    public static <T> T jsonVersT(String message) throws JsonProcessingException
    {

        ObjectMapper mapper = new ObjectMapper();

        T data = mapper.readValue(message, new TypeReference<T>() {});

        return data;

    }

}
