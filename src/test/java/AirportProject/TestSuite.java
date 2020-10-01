package AirportProject;

import org.junit.Test;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.*;

//import flights
import static AirportProject.Flight.*;

public class TestSuite {

    private Airport ap;
    private List<Flight> flightList;
    public static final String TEST_FILE = "ap2";
    private static int testId = 1;

    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private PrintStream outputPrintStream = new PrintStream(outputStream);
    private final PrintStream backupOut = System.out;

    private void initDefaultAP() {
        initEmptyAP();
        ap.getFlights().addAll(getDefaultFlights());
        ap.sortByDateTime();
    }

    private void initEmptyAP() {
        ap = new Airport();
    }

    private void silenceOutput() {
        System.setOut(outputPrintStream);
    }

    private void resetOutput() {
        System.setOut(backupOut);
        outputStream = new ByteArrayOutputStream();
        outputPrintStream = new PrintStream(outputStream);
    }

    public void printSuccess() {
        //get name of next item in stack and print
        System.out.printf("Test #%d SUCCESS - %s\n", testId++, new Throwable().getStackTrace()[1].getMethodName());
    }


    /*
        Airport Class Tests
     */

    @Test
    public void testFileLoadAndSave() {
        initDefaultAP();
        Airport ap2 = null;
        File testFile = null;
        try {
            ap.save(TEST_FILE);
            testFile = new File(TEST_FILE);
            ap2 = new Airport(testFile);
        } catch (IOException e) {
            fail(e.toString());
        }
        assertEquals(ap, ap2);
        assertTrue(testFile.delete());
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
        assertEquals(Arrays.asList(F5, F6, F7), ap.getFlights());
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
    public void testDirectionFilters() {
        initDefaultAP();
        flightList = new ArrayList<>(ap.getFlights());
        Airport.filterByDirection(flightList, "Departures");
        assertTrue(flightList.containsAll(Airport.getOutgoingFlights(ap.getFlights())));
        assertTrue(Airport.getIncomingFlights(ap.getFlights()).stream().noneMatch(flightList::contains));

        flightList = new ArrayList<>(ap.getFlights());
        Airport.filterByDirection(flightList, "Arrivals");
        assertTrue(flightList.containsAll(Airport.getIncomingFlights(ap.getFlights())));
        assertTrue(Airport.getOutgoingFlights(ap.getFlights()).stream().noneMatch(flightList::contains));
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
                        F3.getDateTime().getDayOfWeek().name(),
                        F6.getDateTime().getDayOfWeek().name(),
                        F4.getDateTime().getDayOfWeek().name()
                ).toLowerCase()
        );
        assertEquals(Arrays.asList(F3, F4, F6), flightList);
        printSuccess();
    }

    @Test
    public void testCityFilter() {
        flightList = new ArrayList<>(getDefaultFlights());
        Airport.filterByCity(flightList, F3.getCity());
        assertEquals(Collections.singletonList(F3), flightList);
        printSuccess();
    }

    @Test
    public void testAirportFilter() {
        flightList = new ArrayList<>(getDefaultFlights());
        Airport.filterByAirport(flightList, F3.getAirportName());
        assertEquals(Collections.singletonList(F3), flightList);
        printSuccess();
    }

    @Test
    public void testCompanyFilter() {
        flightList = new ArrayList<>(getDefaultFlights());
        Airport.filterByCompany(flightList, F3.getCompany());
        assertEquals(Collections.singletonList(F3), flightList);
        printSuccess();
    }

    @Test
    public void testDateRangeFilter() {
        flightList = new ArrayList<>(getDefaultFlights());
        Airport.filterByDateRange(flightList, F3.getDateTime(), F6.getDateTime());
        assertEquals(Arrays.asList(F3, F4, F6), flightList);
        printSuccess();
    }

    @Test
    public void testStartDateFilter() {
        flightList = new ArrayList<>(getDefaultFlights());
        Airport.filterByStartDate(flightList, F3.getDateTime());
        assertEquals(Arrays.asList(F3, F4, F5, F6, F8), flightList);
        printSuccess();
    }

    @Test
    public void testEndDateFilter() {
        flightList = new ArrayList<>(getDefaultFlights());
        Airport.filterByEndDate(flightList, F3.getDateTime());
        assertEquals(Arrays.asList(F1, F2, F3, F7), flightList);
        printSuccess();
    }

    @Test
    public void testTerminalFilter() {
        flightList = new ArrayList<>(getDefaultFlights());
        Airport.filterByTerminal(flightList, F3.getTerminal());
        assertEquals(Arrays.asList(F3, F5), flightList);
        printSuccess();
    }

    @Test
    public void testGetFlightFromUser() {
        silenceOutput();

        initEmptyAP();
        Scanner s = new Scanner("1\nKS9782EL\n 2020\n 11\n 23\n 11\n 16\n UK\n London\n london_airport\n Wizz\n");
        ap.getFlightFromUser(s, true);
        assertEquals(F8, ap.getFlights().get(0));

        resetOutput();
        printSuccess();
    }

    @Test
    public void testSortByDateTime() {
        initEmptyAP();
        ap.addFlight(F8);
        ap.addFlight(F1);
        ap.sortByDateTime();
        assertEquals(F1, ap.getFlights().get(0));
        printSuccess();
    }

     /*
        Flight Class Tests
     */

    @Test
    public void testGetDateTimeFromUser() {
        silenceOutput();

        assertEquals(F8.getDateTime(), Flight.getDateTimeFromUser(new Scanner("2020\n 11\n 23\n 11\n 16\n")));

        resetOutput();
        printSuccess();
    }


     /*
        Menu Class Tests
     */

    @Test
    public void testInitAirportFromFile() {
        silenceOutput();
        //using Java Reflection to test a private method
        try {
            Method initAirportFromFile = Program.class.getDeclaredMethod("initAirportFromFile", String.class);
            initAirportFromFile.setAccessible(true);
            initAirportFromFile.invoke(null, Program.DEFAULT_FILE);
        } catch (Exception e) {
            fail(e.toString());
        }
        assertTrue(outputStream.toString().contains(Program.INIT_FROM_FILE_SUCCESS));

        resetOutput();
        printSuccess();
    }

    @Test
    public void testScanBoolean() {
        silenceOutput();

        assertTrue(Program.scanBoolean(new Scanner("y")));
        assertFalse(Program.scanBoolean(new Scanner("n")));

        resetOutput();
        printSuccess();
    }

    @Test
    public void testInitDefault() {
        silenceOutput();
        try {
            Program.createDefaultFile();
        } catch (IOException e) {
            fail(e.toString());
        }
        assertTrue(outputStream.toString().contains(Program.CREATE_DEFAULT_FILE_SUCCESS));

        resetOutput();
        printSuccess();
    }

    @Test
    public void programmerInputSimulation() {
        InputStream backupIn = System.in;

        /* simulated user input: */
        String simulatedInput = "6\n 2\n 7\n y\n 2\n n\n n\n n\n n\n n\n n\n y\n 3\n 0\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());

        System.setIn(inputStream);
        silenceOutput();

        Program.showMenu(null);

        String outputString = outputStream.toString();

        assertTrue(outputString.contains(F6.toString()));//only expected result
        assertFalse(outputString.contains(F1.toString()));
        assertFalse(outputString.contains(F2.toString()));
        assertFalse(outputString.contains(F3.toString()));
        assertFalse(outputString.contains(F4.toString()));
        assertFalse(outputString.contains(F5.toString()));
        assertFalse(outputString.contains(F7.toString()));

        System.setIn(backupIn);
        resetOutput();
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
                "", "", "", "", "", "", "",     /*weekdays*/
                ""                              /*terminal*/
        };
        System.setOut(outputPrintStream);
        try {
            Program.main(args);
        } catch (Exception e) {
            fail(e.toString());
        }

        String outputString = outputStream.toString();

        assertTrue(outputString.contains(F2.toString()));   //
        assertTrue(outputString.contains(F4.toString()));   // expected results
        assertTrue(outputString.contains(F6.toString()));   //
        assertFalse(outputString.contains(F1.toString()));
        assertFalse(outputString.contains(F3.toString()));
        assertFalse(outputString.contains(F5.toString()));
        assertFalse(outputString.contains(F7.toString()));

        resetOutput();
        printSuccess();
    }

}
