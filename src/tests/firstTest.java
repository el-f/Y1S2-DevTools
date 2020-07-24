package tests;

import AirportProject.*;
import org.junit.Test;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class firstTest {
    // flights
    private final Flight F1 = Utilities.F1;
    private final Flight F2 = Utilities.F2;
    private final Flight F3 = Utilities.F3;
    private final Flight F4 = Utilities.F4;
    private final Flight F5 = Utilities.F5;
    private final Flight F6 = Utilities.F6;
    private final Flight F7 = Utilities.F7;

    @Test
    public void testFileLoadAndSave() throws FileNotFoundException {
        Airport ap = new Airport(new File("airport"));
        ap.save("ap2");
        Airport ap2 = new Airport(new File("ap2"));
        assertEquals(ap.toString(), ap2.toString());
    }

    @Test
    public void testNoDuplicates() {
        Airport ap = new Airport();
        ap.addFlight(F5);
        ap.addFlight(F6);
        ap.addFlight(F6);
        ap.addFlight(F7);
        ap.addFlight(F7);
        ap.addFlight(F7);
        assertEquals(3, ap.getFlights().size());
    }

    @Test
    public void testFilters() {
        Airport ap = new Airport();
        ap.addFlight(F1);
        ap.addFlight(F2);
        ap.addFlight(F3);
        ap.addFlight(F4);
        ap.addFlight(F5);
        ap.addFlight(F6);
        ap.addFlight(F7);
        List<Flight> flightList = new ArrayList<>();
        flightList.add(F1);
        flightList.add(F3);
        flightList.add(F5);
        flightList.add(F7);
        assertEquals(flightList, ap.getOutgoingFlights());
    }

    @Test
    public void testUserFilteredList() {
        InputStream backupIn = System.in;
        PrintStream backupOut = System.out;
        String simulatedInput = "6\n 2\n 7\n y\n 2\n n\n n\n n\n n\n y\n 3\n n\n n\n 0\n";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream outputPrintStream = new PrintStream(outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(outputPrintStream);
        Menu.showMenu();
        String outputString = outputStream.toString();
        System.out.println(outputString);
        assertTrue(outputString.contains(F6.toString()));//only expected result
        assertFalse(outputString.contains(F1.toString()));
        assertFalse(outputString.contains(F2.toString()));
        assertFalse(outputString.contains(F3.toString()));
        assertFalse(outputString.contains(F4.toString()));
        assertFalse(outputString.contains(F5.toString()));
        assertFalse(outputString.contains(F7.toString()));
        System.setOut(backupOut);
        System.setIn(backupIn);
    }

}
