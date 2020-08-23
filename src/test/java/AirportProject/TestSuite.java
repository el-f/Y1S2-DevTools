package AirportProject;

import org.junit.Test;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

//import flights
import static AirportProject.Flight.*;
import static org.junit.Assert.assertEquals;

public class TestSuite {

    private static Airport ap;
    private static List<Flight> flightList;
    public static final String TEST_FILE = "ap2";
    private static int testId = 1;

    private void initDefaultAP() {
        initEmptyAP();
        ap.getFlights().addAll(getDefaultFlights());
    }

    private void initEmptyAP() {
        ap = new Airport();
    }

    public void printSuccess() {
        //get name of next item in stack and print
        System.out.printf("Test #%d SUCCESS - %s\n", testId++, new Throwable().getStackTrace()[1].getMethodName());
    }


    /*
        Airport Class Tests
     */
    @Test
    public void testFileLoadAndSave() throws IOException {
        initDefaultAP();
        ap.save(TEST_FILE);
        Airport ap2 = new Airport(new File(TEST_FILE));
        assertEquals(ap.toString(), ap2.toString());
        printSuccess();
    }

    @Test
    public void testNoDuplicates_And_AddFlight() {
        initEmptyAP();
        ap.addFlight(F5);

        ap.addFlight(F6);
        ap.addFlight(F6);

        ap.addFlight(F7);
        ap.addFlight(F7);
        ap.addFlight(F7);
        assertEquals(3, ap.getFlights().size());
        printSuccess();
    }

    @Test
    public void testRemoveFlight() {
        initEmptyAP();
        ap.addFlight(F1);
        ap.removeFlight(0);
        assertTrue(ap.getFlights().isEmpty());
        printSuccess();
    }

    @Test
    public void testArrivalsFilter() {
        initDefaultAP();
        flightList = Arrays.asList(F2, F4, F6);
        assertEquals(flightList, ap.getIncomingFlights());
        assertTrue(ap.getOutgoingFlights().stream().noneMatch(flightList::contains));
        printSuccess();
    }

    @Test
    public void testDeparturesFilter() {
        initDefaultAP();
        flightList = Arrays.asList(F1, F3, F5, F7, F8);
        assertEquals(flightList, ap.getOutgoingFlights());
        assertTrue(ap.getIncomingFlights().stream().noneMatch(flightList::contains));
        printSuccess();
    }

    @Test
    public void testCountryFilter() {
        flightList = new ArrayList<>(getDefaultFlights());
        Airport.filterByCountry(flightList, "albania");
        assertEquals(Collections.singletonList(F1), flightList);
        printSuccess();
    }

    @Test
    public void testWeekDaysFilter() {
        flightList = new ArrayList<>(getDefaultFlights());
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
        printSuccess();
    }

    @Test
    public void testCityFilter() {
        flightList = new ArrayList<>(getDefaultFlights());
        Airport.filterByCity(flightList,F3.getCity());
        assertEquals(Collections.singletonList(F3), flightList);
        printSuccess();
    }

    @Test
    public void testAirportFilter() {
        flightList = new ArrayList<>(getDefaultFlights());
        Airport.filterByAirport(flightList,F3.getAirportName());
        assertEquals(Collections.singletonList(F3), flightList);
        printSuccess();
    }

    @Test
    public void testCompanyFilter() {
        flightList = new ArrayList<>(getDefaultFlights());
        Airport.filterByCompany(flightList,F3.getCompany());
        assertEquals(Collections.singletonList(F3), flightList);
        printSuccess();
    }

    @Test
    public void testDateRangeFilter() {
        flightList = new ArrayList<>(getDefaultFlights());
        Airport.filterByDateRange(flightList,F3.getDate(),F6.getDate());
        assertEquals(Arrays.asList(F3, F4, F6), flightList);
        printSuccess();
    }

    @Test
    public void testStartDateFilter() {
        flightList = new ArrayList<>(getDefaultFlights());
        Airport.filterByStartDate(flightList,F3.getDate());
        assertEquals(Arrays.asList(F3, F4,F5 ,F6,F8), flightList);
        printSuccess();
    }

    @Test
    public void testEndDateFilter() {
        flightList = new ArrayList<>(getDefaultFlights());
        Airport.filterByEndDate(flightList,F3.getDate());
        assertEquals(Arrays.asList(F1,F2,F3,F7), flightList);
        printSuccess();
    }

    @Test
    public void testTerminalFilter() {
        flightList = new ArrayList<>(getDefaultFlights());
        Airport.filterByTerminal(flightList,F3.getTerminal());
        assertEquals(Arrays.asList(F3,F5), flightList);
        printSuccess();
    }

    @Test
    public void testGetFlightFromUser() {
        initEmptyAP();
        Scanner s = new Scanner("1\nKS9782EL\n 2020\n 11\n 23\n 11\n 16\n UK\n London\n london_airport\n Wizz\n");
        ap.getFlightFromUser(s,true);
        assertEquals((F8).toString(),ap.getFlights().get(0).toString());
        printSuccess();
    }

    @Test
    public void testSortByDateTime() {
        initEmptyAP();
        ap.addFlight(F8);
        ap.addFlight(F1);
        ap.sortByDateTime();
        assertEquals(F1,ap.getFlights().get(0));
        printSuccess();
    }

     /*
        Flight Class Tests
     */

    @Test
    public void testGetDateTimeFromUser() {
        Scanner s = new Scanner("2020\n 11\n 23\n 11\n 16\n");
        LocalDateTime D=Flight.getDateTimeFromUser(s);
        assertEquals(F8.getDate(),D);
        printSuccess();
    }


     /*
        Menu Class Tests
     */

    @Test
    public void testInitAirportFromFile() {
        initDefaultAP();
        assertEquals(Arrays.asList(F1,F2,F3,F4,F5,F6,F7,F8), ap.getFlights());
        printSuccess();
    }

    @Test
    public void testScanBoolean() {
        assertEquals(true ,Menu.scanBoolean(new Scanner("y")));
        printSuccess();
    }

    @Test
    public void testInitDefault() {
        //TODO
        printSuccess();
    }

    @Test
    public void programmerInputSimulation() {
        InputStream backupIn = System.in;
        PrintStream backupOut = System.out;
        /* simulated user input: */
        String simulatedInput = "6\n 2\n 7\n y\n 2\n n\n n\n n\n n\n y\n 3\n n\n n\n 0\n";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream outputPrintStream = new PrintStream(outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(outputPrintStream);

        Menu.showMenu(null);

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
        printSuccess();
    }

     /*
        Program Class Tests
     */

    @Test
    public void userInputSimulation() {
        String[] args = {
                Program.TEST_IDENTIFIER,
                "arrivals",
                "",                             /*country*/
                "",                             /*city*/
                "",                             /*airport*/
                "",                             /*airline*/
                "", "", "",                     /*start date (d/m/y)*/
                "", "", "",                     /*end date (d/m/y)*/
                "", "", "", "", "", "", ""      /*weekdays*/
//                ,""                             /*terminal*/
        };
        InputStream backupIn = System.in;
        PrintStream backupOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream outputPrintStream = new PrintStream(outputStream);
        System.setOut(outputPrintStream);

        try {
            Program.main(args);
        } catch (Exception e) {
            e.printStackTrace();
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
        printSuccess();
    }

}
