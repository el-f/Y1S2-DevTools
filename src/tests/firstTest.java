package tests;

import AirportProject.Airport;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class firstTest {

    @Test
    public void testSomething() throws FileNotFoundException {

        assertTrue(true);
        assertEquals(3, 1 + 2);

        Airport ap = new Airport(new File("airport"));
        ap.save("ap2");
        Airport ap2 = new Airport(new File("ap2"));
        assertEquals(ap.toString(), ap2.toString());

    }

}
