package AirportProject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        boolean direction = args.length > 0 && (args[0].equalsIgnoreCase("departures") ||
                args[0].equalsIgnoreCase("arrivals"));
//        boolean country = args.length > 2;
//        boolean city = args.length > 3;
//        boolean airport = args.length > 4;
        if (args.length == 0)
            Menu.showMenu();
        else {
            LocalDateTime D1 = LocalDateTime.of(2020, 11, 20, 2, 3);
            LocalDateTime D2 = LocalDateTime.of(2020, 11, 21, 14, 30);
            LocalDateTime D3 = LocalDateTime.of(2020, 11, 22, 19, 59);
            LocalDateTime D4 = LocalDateTime.of(2020, 11, 23, 11, 16);
            // plane
            Plane P1 = new Plane("Wright-Flyer", 4);
            Plane P2 = new Plane("Supermarine-Spitfire", 15);
            Plane P3 = new Plane("Boeing-787", 150);
            Plane P4 = new Plane("Learjet-23", 16);
            Plane P5 = new Plane("C-130", 94);
            Plane P6 = new Plane("Boeing_B-29", 120);
            Plane P7 = new Plane("F-35", 2);

            // flight
            Flight F1 = new Flight(3, "SAD1978", P1, D1, "Albania", "albania_city", "albania_airport", true);
            Flight F2 = new Flight(1, "HASF1238", P2, D1, "Austria", "Austria_city", "Austria_airport", false);
            Flight F3 = new Flight(2, "ASD41IDA", P3, D2, "Guinea", "Guinea_city", "Guinea_airpot", true);
            Flight F4 = new Flight(1, "ASD41IDA", P4, D3, "Ireland", "Ireland_city", "Ireland_airport", false);
            Flight F5 = new Flight(2, "DFWQ41F", P5, D4, "Italy", "Italy_city", "Italy_airport", true);
            Flight F6 = new Flight(3, "UR24EE", P6, D3, "Laos", "Laos_city", "Laos_airport", false);
            Flight F7 = new Flight(1, "KSD556Q", P7, D1, "Iran", "Iran_city", "Iran_airport", true);

            Airport ap = new Airport();
            ap.getFlights().addAll(Arrays.asList(F1, F2, F3, F4, F5, F6, F7));
            List<Flight> result = new ArrayList<>(ap.getFlights());
            var oc = new Object() {
                String str;
                int num;
            };
            if (direction) {
                switch (args[0].toLowerCase()) {
                    case "departures":
                        result.removeAll(ap.getIncomingFlights());
                        break;
                    case "arrivals":
                        result.removeAll(ap.getOutgoingFlights());
                        break;
                }
            }
//            if (args[]) {
//                oc.str = s.next().toLowerCase();
//                result.removeIf(f -> !f.getCountry().toLowerCase().equals(oc.str));
//            }
//            System.out.println("Do you want to filter by city?");
//            if (scanBoolean(s)) {
//                System.out.println("Please Enter city:");
//                oc.str = s.next().toLowerCase();
//                result.removeIf(f -> !f.getCity().toLowerCase().equals(oc.str));
//            }
//            System.out.println("Do you want to filter by airport?");
//            if (scanBoolean(s)) {
//                System.out.println("Please Enter airport name:");
//                oc.str = s.next().toLowerCase();
//                result.removeIf(f -> !f.getAirportName().toLowerCase().equals(oc.str));
//            }
//            System.out.println("Do you want to filter by terminal num?");
//            if (scanBoolean(s)) {
//                System.out.println("Please Enter terminal number:");
//                oc.num = s.nextInt();
//                result.removeIf(f -> f.getTerminal() != oc.num);
//            }
//            System.out.println("Do you want to filter by day of the week?");
//            if (scanBoolean(s)) {
//                System.out.println("Please enter day of the week by name");
//                oc.str = s.next();
//                result.removeIf(f -> !f.getDate().getDayOfWeek().name().equals(oc.str.toUpperCase()));
//            }
//            System.out.println("Do you want to filter by date and time range?");
//            if (scanBoolean(s)) {
//                System.out.println("Please enter start date");
//                LocalDateTime start = Flight.getDateTimeFromUser(s);
//                System.out.println("Please enter end date");
//                LocalDateTime end = Flight.getDateTimeFromUser(s);
//                result.removeIf(f -> f.getDate().isBefore(start) || f.getDate().isAfter(end));
//            }
            result.forEach(r->System.out.println(r +"<br>"));
        }
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

//    }

    }
}
