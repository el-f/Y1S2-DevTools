package AirportProject;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.rmi.UnexpectedException;
import java.time.LocalDateTime;
import java.util.*;

import static AirportProject.Airport.*;

@UtilityClass
public class Program {
    public final String CREATE_DEFAULT_FILE_SUCCESS = "~~~Saved Successfully~~~";
    public final String INIT_FROM_FILE_SUCCESS = "\n~~~Successfully Loaded Argument File~~~\n";
    public final String TEST_IDENTIFIER = "INTERNAL_TEST";
    public final String DEFAULT_FILE = "airport.csv";
    public final String DEFAULT_FILE_ABS_PATH = Paths.get("").toAbsolutePath().getParent() + "/" + DEFAULT_FILE;

    private Airport airport;

    public void main(String[] args) throws IOException {
        if (args.length == 0) {
            showMenu(null);
        } else if (args.length == 1) {
            showMenu(args[0]); //passing filepath argument to menu
        } else {
            //external java calls need full path, internal tests only need filename.
            airport = new Airport(
                    new File(
                            args[0].equalsIgnoreCase(TEST_IDENTIFIER)
                                    ? DEFAULT_FILE
                                    : DEFAULT_FILE_ABS_PATH
                    )
            );

            List<Flight> results = new ArrayList<>(airport.getFlights());
            applyFiltersByArgs(args, results);

            String newLine = args[0].equalsIgnoreCase("HTML") ? "<br>" : "\n";

            if (results.isEmpty()) {
                System.out.print("Empty flights list! Too many or invalid filters!" + newLine);
            } else results.forEach(result -> System.out.print(result + newLine));
        }
    }

    void showMenu(String filePathArg) {
        initAirportFromFile(filePathArg);

        Scanner s = new Scanner(System.in);
        int choice = -1;
        while (choice != 0) try {
            System.out.println("> Menu:");
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
                    System.out.println("Choose: ");
                    System.out.println("1) Enter file name/path yourself");
                    System.out.println("2) Save to default file");
                    String path = null;
                    switch (s.nextInt()) {
                        case 1:
                            System.out.println("Please enter file name or file path to create");
                            path = s.next();
                            break;
                        case 2:
                            path = DEFAULT_FILE_ABS_PATH;
                            break;
                        default:
                            System.out.println("Invalid Input For Input Range [1-2]");
                            break;
                    }
                    if (path != null) airport.save(path);
                    break;
                case 6:
                    System.out.println("Choose: ");
                    System.out.println("1) Enter file name/path yourself");
                    System.out.println("2) Load default file");
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
                            System.out.println("Invalid Input For Input Range [1-2]");
                            break;
                    }
                    break;
                case 7:
                    showCustomRangeFlights(s);
                    break;
                case 8:
                    if (airport.getFlights().isEmpty())
                        System.out.println("Empty Flight List!");
                    else
                        System.out.println(airport);
                    break;
                case 9:
                    if (airport.getFlights().isEmpty()) {
                        System.out.println("Empty Flight List!");
                    } else {
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
                    System.out.println("invalid input For Input Range [0-10]");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error!" + e.getClass().getSimpleName());
            s.nextLine(); //clean buffer
        }

    }

    void showCustomRangeFlights(Scanner s) throws UnexpectedException {
        List<Flight> result = new ArrayList<>(airport.getFlights());
        System.out.println("Do you want to filter by Direction?");
        if (scanBoolean(s)) {
            System.out.println("Please Select direction: ");
            System.out.println("1) Departures");
            System.out.println("2) Arrivals");
            String direction;
            switch (s.nextInt()) {
                case 1:
                    direction = "Departures";
                    break;
                case 2:
                    direction = "Arrivals";
                    break;
                default:
                    throw new UnexpectedException("Invalid Value for range [1-2]");
            }
            filterByDirection(result, direction);
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
        System.out.println("Do you want to filter by date and time range?");
        if (scanBoolean(s)) {
            System.out.println("Please enter start date");
            LocalDateTime start = Flight.getDateTimeFromUser(s);
            System.out.println("Please enter end date");
            LocalDateTime end = Flight.getDateTimeFromUser(s);
            filterByDateRange(result, start, end);
        }
        System.out.println("Do you want to filter by days of the week?");
        if (scanBoolean(s)) {
            System.out.println("Please enter days of the week by name [space separated]");
            System.out.println("Example: 'sunday friday monday'");
            filterByWeekDays(result, s.next().toLowerCase());
        }
        System.out.println("Do you want to filter by terminal num?");
        if (scanBoolean(s)) {
            System.out.println("Please Enter terminal number:");
            filterByTerminal(result, s.nextInt());
        }
        result.forEach(System.out::println);
    }

    private void initAirportFromFile(String filePathArg) {
        if (filePathArg == null) {
            airport = new Airport();
        } else {
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

    boolean scanBoolean(Scanner scanner) {
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
                System.out.println("Invalid Input!");
        } while (!gotInput);
        return input;
    }

    void createDefaultFile() throws IOException {
        Airport ap = new Airport();
        ap.getFlights().addAll(Flight.getDefaultFlights());
        ap.sortByDateTime();
        ap.save(Program.DEFAULT_FILE);
        System.out.println(
                ap.toString().equals(new Airport(new File(Program.DEFAULT_FILE)).toString()) ?
                        CREATE_DEFAULT_FILE_SUCCESS :
                        "~~~ERROR OCCURRED WHILE SAVING"
        );
    }
}
