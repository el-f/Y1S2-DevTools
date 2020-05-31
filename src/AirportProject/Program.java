package AirportProject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Program {


    public static void main(String[] args) {
        boolean direction = args.length > 0 && (args[0].equalsIgnoreCase("departures") ||
                args[0].equalsIgnoreCase("arrivals"));
        if (args.length == 0)
            Menu.showMenu();
        else {
//            System.out.println("args:" + "<br>");
//            for (String arg : args)
//                System.out.println(arg + "<br>");
            DateTimeFormatter ldtFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss");
            LocalDateTime D1 = LocalDateTime.of(2020, 11, 20, 2, 3);
            LocalDateTime D2 = LocalDateTime.of(2020, 11, 21, 14, 30);
            LocalDateTime D3 = LocalDateTime.of(2020, 11, 22, 19, 59);
            LocalDateTime D4 = LocalDateTime.of(2020, 11, 23, 11, 16);
            // planes
            Plane P1 = new Plane("Wright-Flyer", 4);
            Plane P2 = new Plane("Supermarine-Spitfire", 15);
            Plane P3 = new Plane("Boeing-787", 150);
            Plane P4 = new Plane("Learjet-23", 16);
            Plane P5 = new Plane("C-130", 94);
            Plane P6 = new Plane("Boeing_B-29", 120);
            Plane P7 = new Plane("F-35", 2);

            // flights
            Flight F1 = new Flight(3, "SAD1978", P1, D1, "Albania", "albania_city", "albania_airport", true);
            Flight F2 = new Flight(1, "HASF1238", P2, D1, "Austria", "Austria_city", "Austria_airport", false);
            Flight F3 = new Flight(2, "ASD41IDA", P3, D2, "Guinea", "Guinea_city", "Guinea_airpot", true);
            Flight F4 = new Flight(1, "ASD41IDA", P4, D3, "Ireland", "Ireland_city", "Ireland_airport", false);
            Flight F5 = new Flight(2, "DFWQ41F", P5, D4, "Italy", "Italy_city", "Italy_airport", true);
            Flight F6 = new Flight(3, "UR24EE", P6, D3, "Laos", "Laos_city", "Laos_airport", false);
            Flight F7 = new Flight(1, "KSD556Q", P7, D1, "Iran", "Iran_city", "Iran_airport", true);
            Flight F8 = new Flight(1, "KS9782E", P3, D4, "UK", "London", "london_airport", true);

            Airport ap = new Airport();
            ap.getFlights().addAll(Arrays.asList(F1, F2, F3, F4, F5, F6, F7, F8));
            List<Flight> result = new ArrayList<>(ap.getFlights());
            if (direction) {
                switch (args[0].toLowerCase()) {
                    case "departures":
                        result.removeAll(ap.getIncomingFlights());
                        break;
                    case "arrivals":
                        result.removeAll(ap.getOutgoingFlights());
                        break;
                    default:
                        break;
                }
            }
            if (!args[1].isBlank()) {
                result.removeIf(f -> !f.getCountry().equalsIgnoreCase(args[1]));
            }
            if (!args[2].isBlank()) {
                result.removeIf(f -> !f.getCity().toLowerCase().equals(args[2]));
            }
            if (!args[3].isBlank()) {
                result.removeIf(f -> !f.getAirportName().toLowerCase().equals(args[3]));
            }
            if (!args[4].isBlank()) {
                result.removeIf(f -> f.getTerminal() != Integer.parseInt(args[4]));
            }
            if (!args[5].isBlank()) {
                result.removeIf(f -> !f.getDate().getDayOfWeek().name().equalsIgnoreCase(args[5]));
            }
            if (!args[6].isBlank()) {
                result.removeIf(f -> f.getDate().isBefore(LocalDateTime.parse(args[6], ldtFormatter)));
            }
            if (!args[7].isBlank()) {
                result.removeIf(f -> f.getDate().isAfter(LocalDateTime.parse(args[7], ldtFormatter)));
            }
            if (result.isEmpty())
                System.out.println("empty flights list! too much or invalid filters!");
            result.forEach(r -> System.out.println(r + "<br>"));
        }



/*
          method examples:
 */
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
