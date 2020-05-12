package AirportProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;

public class Program {
    public static void main(String[] args) {

        Menu.showMenu();

//        LocalDateTime D1 = LocalDateTime.of(2020, 11, 20, 2, 3);
//        LocalDateTime D2 = LocalDateTime.of(2020, 11, 21, 14, 30);
//        LocalDateTime D3 = LocalDateTime.of(2020, 11, 22, 19, 59);
//        LocalDateTime D4 = LocalDateTime.of(2020, 11, 23, 11, 16);
//        // plane
//        Plane P1 = new Plane("Wright-Flyer", 4);
//        Plane P2 = new Plane("Supermarine-Spitfire", 15);
//        Plane P3 = new Plane("Boeing-787", 150);
//        Plane P4 = new Plane("Learjet-23", 16);
//        Plane P5 = new Plane("C-130", 94);
//        Plane P6 = new Plane("Boeing_B-29", 120);
//        Plane P7 = new Plane("F-35", 2);
//
//        // flight
//        Flight F1 = new Flight(3, "SAD1978", P1, D1, "Albania", "albania_city", "albania_airport", true);
//        Flight F2 = new Flight(1, "HASF1238", P2, D1, "Austria", "Austria_city", "Austria_airport", false);
//        Flight F3 = new Flight(2, "ASD41IDA", P3, D2, "Guinea", "Guinea_city", "Guinea_airpot", true);
//        Flight F4 = new Flight(1, "ASD41IDA", P4, D3, "Ireland", "Ireland_city", "Ireland_airport", false);
//        Flight F5 = new Flight(2, "DFWQ41F", P5, D4, "Italy", "Italy_city", "Italy_airport", true);
//        Flight F6 = new Flight(3, "UR24EE", P6, D3, "Laos", "Laos_city", "Laos_airport", false);
//        Flight F7 = new Flight(1, "KSD556Q", P7, D1, "Iran", "Iran_city", "Iran_airport", true);
//
//        Airport ap = new Airport();
//        System.out.println("All:");
//        ap.addFlight(F1);
//        ap.addFlight(F2);
//        ap.addFlight(F3);
//        ap.addFlight(F4);
//        ap.addFlight(F5);
//        ap.addFlight(F6);
//        ap.addFlight(F7);
//        ap.show();
//
//
////        // remove by ref
////        System.out.println("\nremoved even numbers:");
////        ap.removeFlight(F2);
////        ap.removeFlight(F4);
////        ap.removeFlight(F6);
////        ap.show();
//
//        System.out.println("\nsorted by number of Passengers:");
//        ap.sortByNumOfPassengers();
//        ap.show();
//
//        System.out.println("\nsorted by date and time:");
//        ap.sortByDateTime();
//        ap.show();
//
//        System.out.println("\nsorted by direction");
//        ap.sortByDirection();
//        ap.show();
//
//        System.out.println("\noutgoing:");
//        ap.showOutgoingFlights();
//        System.out.println("\nincoming:");
//        ap.showIncomingFlights();
//
//
//        ap.save("airport");
//        Airport ap2 = new Airport(new File("airport"));
//
//        System.out.println("\nairport 2");
//        ap2.show();

    }

}
