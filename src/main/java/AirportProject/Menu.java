package AirportProject;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static AirportProject.Airport.*;

public abstract class Menu {
    private static Airport airport;
    public static final String CREATE_DEFAULT_FILE_SUCCESS = "~~~Saved Successfully!";
    public static final String INIT_FROM_FILE_SUCCESS = "\n~~~Successfully Loaded Argument File~~~\n";

    static void showMenu(String filePathArg) {
        initAirportFromFile(filePathArg);
        airport.sortByDateTime();

        Scanner s = new Scanner(System.in);
        int choice = -1;
        while (choice != 0) try {
            System.out.println("Menu:");
            System.out.println("1) Create outgoing Flight");
            System.out.println("2) Create incoming Flight");
            System.out.println("3) Show all outgoing flights");
            System.out.println("4) Show all incoming flights");
            System.out.println("5) Save airport to file");
            System.out.println("6) Load airport from file");
            System.out.println("7) Show Custom Selected flights");
            System.out.println("8) Show all flights");
            System.out.println("9) Remove Flight");
            System.out.println("10) Create Default File From Scratch");
            System.out.println("\n0) To exit");
            choice = s.nextInt();
            switch (choice) {
                case 1:
                    airport.getFlightFromUser(s, true);
                    break;
                case 2:
                    airport.getFlightFromUser(s, false);
                    break;
                case 3:
                    System.out.println("Outgoing flights: ");
                    airport.showOutgoingFlights();
                    break;
                case 4:
                    System.out.println("Incoming flights: ");
                    airport.showIncomingFlights();
                    break;
                case 5:
                    System.out.println("Please enter file name or file path to create");
                    airport.save(s.next());
                    break;
                case 6:
                    System.out.println("Choose: ");
                    System.out.println("1) enter file name/path yourself");
                    System.out.println("2) load default file");
                    switch (s.nextInt()) {
                        case 1:
                            System.out.println("Please enter file name or file path to read from");
                            initAirportFromFile(s.next());
                            break;
                        case 2:
                            airport = new Airport(new File(Program.DEFAULT_FILE));
                            System.out.println("Loaded default file!");
                            break;
                        default:
                            System.out.println("Invalid Input");
                            break;
                    }
                    break;
                case 7:
                    showCustomRangeFlights(s);
                    break;
                case 8:
                    System.out.println(airport);
                    break;
                case 9:
                    if (airport.getFlights().isEmpty())
                        System.out.println("Empty Flight List!");
                    else {
                        System.out.println("Choose num of flight to remove");
                        System.out.println(airport);
                        airport.removeFlight(s.nextInt());
                        System.out.println("Removed");
                    }
                    break;
                case 0:
                    System.out.println("~~~end of program~~~");
                    break;
                case 10:
                    createDefaultFile();
                    break;
                default:
                    System.out.println("invalid input");
                    break;
            }
        } catch (Exception e) {
            String message = "";
            if (!(e instanceof MyException))
                message = "Error! " + e.getClass().getSimpleName() + " for ";
            message += e.getMessage();
            System.out.println(message);
            s.nextLine(); //clean buffer
        }

    }


    static void showCustomRangeFlights(Scanner s) throws MyException {
        List<Flight> result = new ArrayList<>(airport.getFlights());
        System.out.println("Do you want to filter by Direction?");
        if (scanBoolean(s)) {
            System.out.println("Please Select direction: ");
            System.out.println("1) Outgoing");
            System.out.println("2) Incoming");
            switch (s.nextInt()) {
                case 1:
                    result.removeAll(airport.getIncomingFlights());
                    break;
                case 2:
                    result.removeAll(airport.getOutgoingFlights());
                    break;
                default:
                    throw new MyException("Unexpected Value!");
            }
        }
        System.out.println("Do you want to filter by country?");
        if (scanBoolean(s)) {
            System.out.println("Please Enter country:");
            filterByCountry(result, s.next());
        }
        System.out.println("Do you want to filter by city?");
        if (scanBoolean(s)) {
            System.out.println("Please Enter city:");
            filterByCity(result, s.next());
        }
        System.out.println("Do you want to filter by airport?");
        if (scanBoolean(s)) {
            System.out.println("Please Enter airport name:");
            filterByAirport(result, s.next());
        }
        System.out.println("Do you want to filter by company?");
        if (scanBoolean(s)) {
            System.out.println("Please Enter company name:");
            filterByCompany(result, s.next());
        }
        System.out.println("Do you want to filter by terminal num?");
        if (scanBoolean(s)) {
            System.out.println("Please Enter terminal number:");
            filterByTerminal(result, s.nextInt());
        }
        System.out.println("Do you want to filter by days of the week?");
        if (scanBoolean(s)) {
            System.out.println("Please enter days of the week by name [space separated]");
            System.out.println("Example: 'sunday friday monday'");
            filterByWeekDays(result, s.next().toLowerCase());
        }
        System.out.println("Do you want to filter by date and time range?");
        if (scanBoolean(s)) {
            System.out.println("Please enter start date");
            LocalDateTime start = Flight.getDateTimeFromUser(s);
            System.out.println("Please enter end date");
            LocalDateTime end = Flight.getDateTimeFromUser(s);
            filterByDateRange(result, start, end);
        }
        result.forEach(System.out::println);
    }

    private static void initAirportFromFile(String filePathArg) {
        if (filePathArg == null)
            airport = new Airport();
        else {
            File f = new File(filePathArg.trim());
            if (!f.exists() || f.isDirectory()) {
                System.out.println("Error locating the specified file!");
                return;
            }
            try {
                airport = new Airport(f);
                System.out.println(INIT_FROM_FILE_SUCCESS);
            } catch (Exception fileNotFoundException) {
                System.out.println("File Not Found!");
            }
        }
    }

    static boolean scanBoolean(Scanner scanner) {
        boolean input = false, gotInput = false;
        String scanned;
        do {
            System.out.println("Enter 'y' for yes, 'n' for no");
            scanned = scanner.next().toLowerCase();
            if (scanned.equals("y")) {
                input = true;
                gotInput = true;
            } else if (scanned.equals("n")) {
                gotInput = true;
            } else
                System.out.println("invalid input!");
        } while (!gotInput);
        return input;
    }

    static void createDefaultFile() throws IOException {
        Airport ap = new Airport();
        ap.getFlights().addAll(Flight.getDefaultFlights());
        ap.save(Program.DEFAULT_FILE);
        System.out.println(
                ap.toString().equals(new Airport(new File(Program.DEFAULT_FILE)).toString()) ?
                        CREATE_DEFAULT_FILE_SUCCESS :
                        "~~~ERROR OCCURRED WHILE SAVING"
        );
    }
}
