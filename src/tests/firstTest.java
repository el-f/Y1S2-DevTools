package tests;

import AirportProject.Airport;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

public class firstTest {

    @Test
    public void testSomething() throws FileNotFoundException {

        Assert.assertEquals(true, true);
        Assert.assertEquals(3, 1 + 2);

        Airport ap = new Airport(new File("airport"));
        ap.save("ap2");
        Airport ap2 = new Airport(new File("ap2"));
        Assert.assertEquals(ap.toString(), ap2.toString());

    }

}
