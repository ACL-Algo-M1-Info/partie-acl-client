package tcp;

import org.junit.Test;

public class RequeteAPITest
{

    @Test
    public void requeteAPITest()
    {
        RequeteAPI requeteAPI = new RequeteAPI("Bonsoir", 1, 2, "test");
        System.out.println(requeteAPI);
    }

}