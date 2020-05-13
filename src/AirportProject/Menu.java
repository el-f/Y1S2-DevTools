package AirportProject;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

public class Menu {
    private static Airport ap;

    public static void showMenu() {
        ap = new Airport();
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
            System.out.println("\n0) To exit");
            choice = s.nextInt();
            switch (choice) {
                case 1:
                    ap.getFlightFromUser(s, true);
                    break;
                case 2:
                    ap.getFlightFromUser(s, false);
                    break;
                case 3:
                    System.out.println("Outgoing flights: ");
                    ap.showOutgoingFlights();
                    break;
                case 4:
                    System.out.println("Incoming flights: ");
                    ap.showIncomingFlights();
                    break;
                case 5:
                    System.out.println("Please enter file name or file path to create");
                    ap.save(s.next());
                    break;
                case 6:
                    System.out.println("Choose: ");
                    System.out.println("1) enter file name/path yourself");
                    System.out.println("2) load default file");
                    switch (s.nextInt()) {
                        case 1:
                            System.out.println("Please enter file name or file path to read from");
                            File f = new File(s.next());
                            if (!f.exists()) {
                                System.out.println("no such file!");
                                break;
                            }
                            ap = new Airport(f);
                            break;
                        case 2:
                            ap = new Airport(new File("airport"));
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
                    ap.show();
                    break;
                case 0:
                    System.out.println("~~~end of program~~~");
                    break;
                default:
                    System.out.println("invalid input");
                    break;
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
            System.out.println("please try again");
        } catch (Exception e) {
            System.out.println("oops! error: " + e.getClass().getSimpleName());
            System.out.println("please try again");
        }
    }



    private static void showCustomRangeFlights(Scanner s) throws MyException {
        List<Flight> result = new ArrayList<>(ap.getFlights());
        var oc = new Object() {
            String str;
            int num;
        };
        System.out.println("if you want to filter by Direction enter 'y'. 'n' for no");
        if (s.next().equals("y")) {
            System.out.println("Please Select direction: ");
            System.out.println("1) Outgoing");
            System.out.println("2) Incoming");
            switch (s.nextInt()) {
                case 1:
                    result.removeAll(ap.getIncomingFlights());
                    break;
                case 2:
                    result.removeAll(ap.getOutgoingFlights());
                    break;
                default:
                    throw new MyException("Unexpected Value!");
            }
        }
        System.out.println("if you want to filter by country enter 'y'. 'n' for no");
        if (s.next().equals("y")) {
            System.out.println("Please Enter country:");
            oc.str = s.next().toLowerCase();
            result.removeIf(f -> !f.getCountry().toLowerCase().equals(oc.str));
        }
        System.out.println("if you want to filter by city enter 'y'. 'n' for no");
        if (s.next().equals("y")) {
            System.out.println("Please Enter city:");
            oc.str = s.next().toLowerCase();
            result.removeIf(f -> !f.getCity().toLowerCase().equals(oc.str));
        }
        System.out.println("if you want to filter by airport enter 'y'. 'n' for no");
        if (s.next().equals("y")) {
            System.out.println("Please Enter airport name:");
            oc.str = s.next().toLowerCase();
            result.removeIf(f -> !f.getAirportName().toLowerCase().equals(oc.str));
        }
        System.out.println("if you want to filter by terminal num enter 'y'. 'n' for no");
        if (s.next().equals("y")) {
            System.out.println("Please Enter terminal number:");
            oc.num = s.nextInt();
            result.removeIf(f -> f.getTerminal() != oc.num);
        }
        System.out.println("if you want to filter by day of the week range enter 'y'. 'n' for no");
        if (s.next().equals("y")) {
            System.out.println("Please enter day of the week by name");
            oc.str = s.next();
            result.removeIf(f -> !f.getDate().getDayOfWeek().name().equals(oc.str.toUpperCase()));
        }
        System.out.println("if you want to filter by date and time range enter 'y'. 'n' for no");
        if (s.next().equals("y")) {
            System.out.println("Please enter start date");
            LocalDateTime start = Flight.getDateTimeFromUser(s);
            System.out.println("Please enter end date");
            LocalDateTime end = Flight.getDateTimeFromUser(s);
            result.removeIf(f -> f.getDate().isBefore(start) || f.getDate().isAfter(end));
        }
        result.forEach(System.out::println);
    }
}
