package AirportProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.*;

public class Program {

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length == 0)
            Menu.showMenu();
        else {
            boolean direction = (args[1].equalsIgnoreCase("departures") ||
                    args[1].equalsIgnoreCase("arrivals"));

            //external java call has to have full path.
            Airport ap = new Airport(new File(
                    Paths.get("").toAbsolutePath().getParent().toString() + "\\airport")
            );
            List<Flight> result = new ArrayList<>(ap.getFlights());

            if (direction) {
                switch (args[1].toLowerCase()) {
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
            if (!args[2].isBlank()) {
                Airport.filterByCountry(result, args[2]);
            }
            if (!args[3].isBlank()) {
                Airport.filterByCity(result, args[3]);
            }
            if (!args[4].isBlank()) {
                Airport.filterByAirport(result, args[4]);
            }
            if (!args[5].isBlank()) {
                Airport.filterByCompany(result, args[5]);
            }
            if (!args[6].isBlank() && !args[7].isBlank() && !args[8].isBlank()) {
                Airport.filterByStartDate(result, Flight.getDateTimeFromUser(parseInt(args[6]),
                        parseInt(args[7]), parseInt(args[8])));
            }
            if (!args[9].isBlank() && !args[10].isBlank() && !args[11].isBlank()) {
                Airport.filterByEndDate(result, Flight.getDateTimeFromUser(parseInt(args[9]),
                        parseInt(args[10]), parseInt(args[11])));
            }
            String weekdays = "";
            if (!args[12].isBlank() && Boolean.parseBoolean(args[12])) weekdays += "sunday ";
            if (!args[13].isBlank() && Boolean.parseBoolean(args[13])) weekdays += "monday ";
            if (!args[14].isBlank() && Boolean.parseBoolean(args[14])) weekdays += "tuesday ";
            if (!args[15].isBlank() && Boolean.parseBoolean(args[15])) weekdays += "wednesday ";
            if (!args[16].isBlank() && Boolean.parseBoolean(args[16])) weekdays += "thursday ";
            if (!args[17].isBlank() && Boolean.parseBoolean(args[17])) weekdays += "friday ";
            if (!args[18].isBlank() && Boolean.parseBoolean(args[18])) weekdays += "saturday ";
            if (weekdays.length() > 0)
                Airport.filterByWeekDays(result, weekdays);

            if (result.isEmpty())
                System.out.println("empty flights list! too much or invalid filters!");
            result.forEach(r -> System.out.println(r + (
                    args[0].equalsIgnoreCase("TEXT") ? "" :
                            args[0].equalsIgnoreCase("HTML") ? "<br>" : "")));
        }

//        initDefault();        //for initiating the default files.
    }


    @SuppressWarnings("unused")
    private static void initDefault() throws FileNotFoundException {
        Airport ap = new Airport();
        ap.getFlights().addAll(Utilities.getDefaultFlights());
        System.out.println(ap);
        ap.save("airport");
        Airport ap2 = new Airport(new File("airport"));
        System.out.println("\nairport 2");
        System.out.println(ap2);
    }
}
