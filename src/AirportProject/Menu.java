package AirportProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Menu {
    private static Airport ap = new Airport();

    public static void showMenu() throws FileNotFoundException {
        Scanner s = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menu:");
            System.out.println("1) Create outgoing Flight");
            System.out.println("2) Create incoming Flight");
            System.out.println("3) Show all outgoing flights");
            System.out.println("4) Show all incoming flights");
            System.out.println("5) Save airport to file");
            System.out.println("6) Load airport from file");
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
                    System.out.println("Please enter file name or file path to read from");
                    File f = new File(s.next());
                    if (!f.exists()) {
                        System.out.println("no such file!");
                        break;
                    }
                    ap = new Airport(f);
                    break;
                case 0:
                    choice = 0;
                    break;
                default:
                    System.out.println("invalid input");
                    break;
            }
        } while (choice != 0);

    }
}
