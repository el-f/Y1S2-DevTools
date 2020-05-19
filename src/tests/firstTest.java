package tests;

import AirportProject.Airport;
import AirportProject.Flight;
import AirportProject.Menu;
import AirportProject.Plane;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class firstTest {
    //dates
    private final LocalDateTime D1 = LocalDateTime.of(2020, 11, 20, 2, 3);
    private final LocalDateTime D2 = LocalDateTime.of(2020, 11, 21, 14, 30);
    private final LocalDateTime D3 = LocalDateTime.of(2020, 11, 22, 19, 59);
    private final LocalDateTime D4 = LocalDateTime.of(2020, 11, 23, 11, 16);

    // planes
    private final Plane P1 = new Plane("Wright-Flyer", 4);
    private final Plane P2 = new Plane("Supermarine-Spitfire", 15);
    private final Plane P3 = new Plane("Boeing-787", 150);
    private final Plane P4 = new Plane("Learjet-23", 16);
    private final Plane P5 = new Plane("C-130", 94);
    private final Plane P6 = new Plane("Boeing_B-29", 120);
    private final Plane P7 = new Plane("F-35", 2);

    // flights
    private final Flight F1 = new Flight(3, "SAD1978", P1, D1, "Albania", "albania_city", "albania_airport", true);
    private final Flight F2 = new Flight(1, "HASF1238", P2, D1, "Austria", "Austria_city", "Austria_airport", false);
    private final Flight F3 = new Flight(2, "ASD41IDA", P3, D2, "Guinea", "Guinea_city", "Guinea_airpot", true);
    private final Flight F4 = new Flight(1, "ASD41IDA", P4, D3, "Ireland", "Ireland_city", "Ireland_airport", false);
    private final Flight F5 = new Flight(2, "DFWQ41F", P5, D4, "Italy", "Italy_city", "Italy_airport", true);
    private final Flight F6 = new Flight(3, "UR24EE", P6, D3, "Laos", "Laos_city", "Laos_airport", false);
    private final Flight F7 = new Flight(1, "KSD556Q", P7, D1, "Iran", "Iran_city", "Iran_airport", true);

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
        flightList.add(F2);
        flightList.add(F7);
        assertEquals(flightList, ap.getFlightsByDayOfWeek(D1.getDayOfWeek()));
        flightList.remove(F2);
        flightList.remove(F7);
        flightList.add(F3);
        flightList.add(F5);
        flightList.add(F7);
        assertEquals(flightList, ap.getOutgoingFlights());
        flightList.remove(F1);
        flightList.remove(F7);
        assertEquals(flightList, ap.getFlightsByTerminal(2));

    }

    @Test
    public void testUserFilteredList() {
        InputStream backupIn = System.in;
        PrintStream backupOut = System.out;
        String simulatedInput = "6\n 2\n 7\n y\n 2\n n\n n\n n\n y\n 3\n n\n n\n 0\n";
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

}
