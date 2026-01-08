package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils
{

    public static <T> T jsonVersT(JsonNode message, TypeReference<T> reference) throws JsonProcessingException
    {
        return jsonVersT(message.toString(), reference);
    }

    public static <T> T jsonVersT(String message, TypeReference<T> reference) throws JsonProcessingException
    {

        ObjectMapper mapper = new ObjectMapper();

        T data = mapper.readValue(message, reference);

        return data;

    }

}
