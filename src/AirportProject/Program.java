package AirportProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;

public class Program {
    public static void main(String[] args) throws FileNotFoundException {

        /*
        To Do:
        1. test
        3. create Menu() (show, add, remove, exit, etc)
         */

        LocalDateTime D1 = LocalDateTime.of(2020, 11, 23, 2, 3);
        LocalDateTime D2 = LocalDateTime.of(2020, 11, 22, 14, 30);
        LocalDateTime D3 = LocalDateTime.of(2020, 11, 21, 19, 59);
        LocalDateTime D4 = LocalDateTime.of(2020, 11, 20, 11, 16);
        // plane
        Plane P1 = new Plane("Wright Flyer", 4);
        Plane P2 = new Plane("Supermarine Spitfire", 15);
        Plane P3 = new Plane("Boeing 787", 150);
        Plane P4 = new Plane("Learjet 23", 16);
        Plane P5 = new Plane("C-130", 94);
        Plane P6 = new Plane("Boeing B-29", 120);
        Plane P7 = new Plane("F-35", 2);

        // flight
        Flight F1 = new Flight("SAD1978", P1, D1, "Albania", true);
        Flight F2 = new Flight("HASF1238", P2, D1, "Austria", false);
        Flight F3 = new Flight("ASD41IDA", P3, D2, "Guinea", true);
        Flight F4 = new Flight("ASD41IDA", P4, D3, "Ireland", false);
        Flight F5 = new Flight("DFWQ41F", P5, D4, "Italy", true);
        Flight F6 = new Flight("UR24EE", P6, D3, "Laos", false);
        Flight F7 = new Flight("KSD556Q", P7, D1, "Iran", true);

        Airport ap = new Airport();
        System.out.println("All:");
        ap.addFlight(F1);
        ap.addFlight(F2);
        ap.addFlight(F3);
        ap.addFlight(F4);
        ap.addFlight(F5);
        ap.addFlight(F6);
        ap.addFlight(F7);
        ap.show();

        // remove by ref
        System.out.println("\nremoved even numbers:");
        ap.removeFlight(F2);
        ap.removeFlight(F4);
        ap.removeFlight(F6);
        ap.show();

        System.out.println("\nsorted by number of Passengers:");
        ap.sortByNumOfPassengers();
        ap.show();

        System.out.println("\nsorted by date and time:");
        ap.sortByDateTime();
        ap.show();

        System.out.println("\nsorted by direction");
        ap.sortByDirection();
        ap.show();

        System.out.println("\noutgoing:");
        ap.showOutgoingFlights();
        System.out.println("\nincoming:");
        ap.showIncomingFlights();


        ap.save("airport");
        Airport ap2 = new Airport(new File("airport"));

        System.out.println("\nairport 2");
        ap2.show();
    }

}
