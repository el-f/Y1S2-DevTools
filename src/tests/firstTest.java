package tests;

import AirportProject.*;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        ap.getFlights().addAll(Utilities.getDefaultFlights());
        ap.removeFlight(7);
        assertEquals(7, ap.getFlights().size());

        List<Flight> flightList = Arrays.asList(F1, F3, F5, F7);
        assertEquals(flightList, ap.getOutgoingFlights());
        assertNotEquals(flightList, ap.getIncomingFlights());

        flightList = Arrays.asList(F2, F4, F6);
        assertEquals(flightList, ap.getIncomingFlights());
        assertNotEquals(flightList, ap.getOutgoingFlights());

        flightList = new ArrayList<>(Arrays.asList(F1, F2, F3, F4, F5, F6, F7));
        Airport.filterByCountry(flightList, "albania");
        assertEquals(Collections.singletonList(F1), flightList);
        assertNotEquals(Collections.singletonList(F2), flightList);

        flightList = new ArrayList<>(Arrays.asList(F1, F2, F3, F4, F5, F6, F7));
        Airport.filterByWeekDays(
                flightList,
                String.format(
                        "%s %s %s",
                        F3.getDate().getDayOfWeek().name(),
                        F6.getDate().getDayOfWeek().name(),
                        F4.getDate().getDayOfWeek().name()
                ).toLowerCase()
        );
        assertEquals(Arrays.asList(F3, F4, F6), flightList);
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

    @Test
    public void testMain() {
        String[] args = {
                "TEXT",
                "arrivals",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
        };
        InputStream backupIn = System.in;
        PrintStream backupOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream outputPrintStream = new PrintStream(outputStream);
        System.setOut(outputPrintStream);

        try {
            Program.main(args);
        } catch (FileNotFoundException e) {
            fail();
        }

        String outputString = outputStream.toString();

        assertTrue(outputString.contains(F2.toString()));   //
        assertTrue(outputString.contains(F4.toString()));   // expected results
        assertTrue(outputString.contains(F6.toString()));   //
        assertFalse(outputString.contains(F1.toString()));
        assertFalse(outputString.contains(F3.toString()));
        assertFalse(outputString.contains(F5.toString()));
        assertFalse(outputString.contains(F7.toString()));

        System.setOut(backupOut);
        System.setIn(backupIn);
    }

}
