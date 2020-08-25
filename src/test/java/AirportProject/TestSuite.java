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
    }

    private void initEmptyAP() {
        ap = new Airport();
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
        try {
            ap.save(TEST_FILE);
            ap2 = new Airport(new File(TEST_FILE));
        } catch (IOException e) {
            fail(e.toString());
        }
        assertEquals(ap, ap2);
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
        Airport.filterByDateRange(flightList, F3.getDate(), F6.getDate());
        assertEquals(Arrays.asList(F3, F4, F6), flightList);
        printSuccess();
    }

    @Test
    public void testStartDateFilter() {
        flightList = new ArrayList<>(getDefaultFlights());
        Airport.filterByStartDate(flightList, F3.getDate());
        assertEquals(Arrays.asList(F3, F4, F5, F6, F8), flightList);
        printSuccess();
    }

    @Test
    public void testEndDateFilter() {
        flightList = new ArrayList<>(getDefaultFlights());
        Airport.filterByEndDate(flightList, F3.getDate());
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
        System.setOut(outputPrintStream);   //to silence output

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
        System.setOut(outputPrintStream);   //to silence output

        assertEquals(F8.getDate(), Flight.getDateTimeFromUser(new Scanner("2020\n 11\n 23\n 11\n 16\n")));

        resetOutput();
        printSuccess();
    }


     /*
        Menu Class Tests
     */

    @Test
    public void testInitAirportFromFile() {
        System.setOut(outputPrintStream);   //to silence output
        //using Java Reflection to test a private method
        try {
            Method initAirportFromFile = Menu.class.getDeclaredMethod("initAirportFromFile", String.class);
            initAirportFromFile.setAccessible(true);
            initAirportFromFile.invoke(null, Program.DEFAULT_FILE);
        } catch (Exception e) {
            fail(e.toString());
        }
        assertTrue(outputStream.toString().contains(Menu.INIT_FROM_FILE_SUCCESS));

        resetOutput();
        printSuccess();
    }

    @Test
    public void testScanBoolean() {
        System.setOut(outputPrintStream);   //to silence output

        assertTrue(Menu.scanBoolean(new Scanner("y")));
        assertFalse(Menu.scanBoolean(new Scanner("n")));

        resetOutput();
        printSuccess();
    }

    @Test
    public void testInitDefault() {
        System.setOut(outputPrintStream);   //to silence output
        try {
            Menu.createDefaultFile();
        } catch (IOException e) {
            fail(e.toString());
        }
        assertTrue(outputStream.toString().contains(Menu.CREATE_DEFAULT_FILE_SUCCESS));

        resetOutput();
        printSuccess();
    }

    @Test
    public void programmerInputSimulation() {
        InputStream backupIn = System.in;

        /* simulated user input: */
        String simulatedInput = "6\n 2\n 7\n y\n 2\n n\n n\n n\n n\n y\n 3\n n\n n\n 0\n";
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
                "", "", "", "", "", "", ""      /*weekdays*/
//                ,""                             /*terminal*/
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
