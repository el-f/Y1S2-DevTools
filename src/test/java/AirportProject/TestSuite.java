package AirportProject;

import org.junit.Test;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

//import flights
import static AirportProject.Flight.*;

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
        //TODO
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
        //TODO
        printSuccess();
    }

    @Test
    public void testAirportFilter() {
        //TODO
        printSuccess();
    }

    @Test
    public void testCompanyFilter() {
        //TODO
        printSuccess();
    }

    @Test
    public void testDateRangeFilter() {
        //TODO
        printSuccess();
    }

    @Test
    public void testStartDateFilter() {
        //TODO
        printSuccess();
    }

    @Test
    public void testEndDateFilter() {
        //TODO
        printSuccess();
    }

    @Test
    public void testTerminalFilter() {
        //TODO
        printSuccess();
    }

    @Test
    public void testGetFlightFromUser() {
//        initEmptyAP();
//        Scanner s = new Scanner(System.in);
//        ap.getFlightFromUser(s,true);
        //TODO
        printSuccess();
    }

    @Test
    public void testSortByDateTime() {
        //TODO
        printSuccess();
    }

     /*
        Flight Class Tests
     */

    @Test
    public void testGetDateTimeFromUser() {
//        getDateTimeFromUser(new Scanner(System.in));
//        getDateTimeFromUser(0,0,0);
        //TODO
        printSuccess();
    }


     /*
        Menu Class Tests
     */

    @Test
    public void testInitAirportFromFile() {
//        String simulatedInput = "6\n 2\n.......";
        //TODO
        printSuccess();
    }

    @Test
    public void testScanBoolean() {
//        Menu.scanBoolean(s);
        //TODO
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
