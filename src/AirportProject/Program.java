package AirportProject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Program {

    public static void main(String[] args) {
        if (args.length == 0)
            Menu.showMenu();
        else {
            boolean direction = (args[0].equalsIgnoreCase("departures") ||
                    args[0].equalsIgnoreCase("arrivals"));
//            System.out.println("args:" + "<br>");
//            for (String arg : args)
//                System.out.println(arg + "<br>");
            DateTimeFormatter ldtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss");
            Airport ap = new Airport();
            ap.getFlights().addAll(Utilities.getDefaultFlights());
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
                result.removeIf(f -> !f.getCity().equalsIgnoreCase(args[2]));
            }
            if (!args[3].isBlank()) {
                result.removeIf(f -> !f.getAirportName().equalsIgnoreCase(args[3]));
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
            if (!args[8].isBlank()) {
                result.removeIf(f -> !f.getCompany().equalsIgnoreCase(args[8]));
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
//        Airport ap = new Airport();
//        ap.getFlights().addAll(Utilities.getDefaultFlights());
//        ap.save("airport");
//        Airport ap2 = new Airport(new File("airport"));
//        ap2.show();
//        System.out.println("\nairport 2");
//        ap2.show();


    }
}
