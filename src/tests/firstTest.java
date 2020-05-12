package tests;

import AirportProject.Airport;
import AirportProject.Flight;
import AirportProject.Plane;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class firstTest {
    private LocalDateTime D1 = LocalDateTime.of(2020, 11, 20, 2, 3);
    private LocalDateTime D2 = LocalDateTime.of(2020, 11, 21, 14, 30);
    private LocalDateTime D3 = LocalDateTime.of(2020, 11, 22, 19, 59);
    private LocalDateTime D4 = LocalDateTime.of(2020, 11, 23, 11, 16);
    // plane
    private Plane P1 = new Plane("Wright-Flyer", 4);
    private Plane P2 = new Plane("Supermarine-Spitfire", 15);
    private Plane P3 = new Plane("Boeing-787", 150);
    private Plane P4 = new Plane("Learjet-23", 16);
    private Plane P5 = new Plane("C-130", 94);
    private Plane P6 = new Plane("Boeing_B-29", 120);
    private Plane P7 = new Plane("F-35", 2);

    // flight
    private Flight F1 = new Flight(3, "SAD1978", P1, D1, "Albania", "albania_city", "albania_airport", true);
    private Flight F2 = new Flight(1, "HASF1238", P2, D1, "Austria", "Austria_city", "Austria_airport", false);
    private Flight F3 = new Flight(2, "ASD41IDA", P3, D2, "Guinea", "Guinea_city", "Guinea_airpot", true);
    private Flight F4 = new Flight(1, "ASD41IDA", P4, D3, "Ireland", "Ireland_city", "Ireland_airport", false);
    private Flight F5 = new Flight(2, "DFWQ41F", P5, D4, "Italy", "Italy_city", "Italy_airport", true);
    private Flight F6 = new Flight(3, "UR24EE", P6, D3, "Laos", "Laos_city", "Laos_airport", false);
    private Flight F7 = new Flight(1, "KSD556Q", P7, D1, "Iran", "Iran_city", "Iran_airport", true);

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
        ap.addFlight(F7);
        ap.addFlight(F7);
        ap.addFlight(F7);
        ap.addFlight(F7);
        assertEquals( 3,ap.getFlights().size());
    }

    @Test
    public void testFilters(){
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
        assertEquals(flightList,ap.getFlightsByDayOfWeek(D1.getDayOfWeek()));
        flightList.remove(F2);
        flightList.remove(F7);
        flightList.add(F3);
        flightList.add(F5);
        flightList.add(F7);
        assertEquals(flightList,ap.getOutgoingFlights());
        flightList.remove(F1);
        flightList.remove(F7);
        assertEquals(flightList,ap.getFlightsByTerminal(2));

    }

}
