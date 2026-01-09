package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;

import java.util.ArrayList;


public class UtilsTest
{

    @Test
    public void testEnJSON()
    {

        String json = "[[0, 1], [4, 2], [5, 1, 0]]";

        try
        {
            ArrayList<ArrayList<Integer>> test = Utils.jsonVersT(json, new TypeReference<ArrayList<ArrayList<Integer>>>() {});

            System.out.println("[");
            for(ArrayList<Integer> i: test)
            {
                System.out.print("[ ");
                for(Integer j: i)
                {
                    System.out.print(j + " ");
                }
                System.out.println("]");
            }
            System.out.println("]");
        }
        catch (JsonProcessingException j)
        {
            //
        }



    }
}