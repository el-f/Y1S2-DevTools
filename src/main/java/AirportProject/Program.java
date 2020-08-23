package AirportProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static AirportProject.Airport.*;
import static java.lang.Boolean.*;
import static java.lang.Integer.*;

public class Program {

    public static final String DEFAULT_FILE = "airport.csv";
    public static final String TEST_IDENTIFIER = "INTERNAL_TEST";

    public static void main(String[] args) throws IOException {
        if (args.length == 0)
            Menu.showMenu(null);
        else if (args.length == 1)
            Menu.showMenu(args[0]); //passing filepath argument to menu
        else {
            //external java calls need full path, internal tests only need filename.
            String path;
            if (args[0].equalsIgnoreCase(TEST_IDENTIFIER)) path = DEFAULT_FILE;
            else path = Paths.get("").toAbsolutePath().getParent() + "/" + DEFAULT_FILE;
            Airport ap = new Airport(new File(path));
            ap.sortByDateTime();

            List<Flight> results = new ArrayList<>(ap.getFlights());

            if (args[1].equalsIgnoreCase("departures"))
                results.removeAll(ap.getIncomingFlights());
            else if (args[1].equalsIgnoreCase("arrivals"))
                results.removeAll(ap.getOutgoingFlights());

            if (args.length > 2 && !args[2].isEmpty()) filterByCountry(results, args[2]);
            if (args.length > 3 && !args[3].isEmpty()) filterByCity(results, args[3]);
            if (args.length > 4 && !args[4].isEmpty()) filterByAirport(results, args[4]);
            if (args.length > 5 && !args[5].isEmpty()) filterByCompany(results, args[5]);

            if (args.length > 8 && !args[6].isEmpty() && !args[7].isEmpty() && !args[8].isEmpty())
                filterByStartDate(
                        results,
                        Flight.getDateTimeFromUser(
                                parseInt(args[6]),  /*day*/
                                parseInt(args[7]),  /*month*/
                                parseInt(args[8])   /*year*/
                        )
                );

            if (args.length > 11 && !args[9].isEmpty() && !args[10].isEmpty() && !args[11].isEmpty())
                filterByEndDate(
                        results,
                        Flight.getDateTimeFromUser(
                                parseInt(args[9]),  /*day*/
                                parseInt(args[10]), /*month*/
                                parseInt(args[11])  /*year*/
                        )
                );

            String weekdays = "";
            if (args.length > 12 && !args[12].isEmpty() && parseBoolean(args[12])) weekdays += "sunday ";
            if (args.length > 13 && !args[13].isEmpty() && parseBoolean(args[13])) weekdays += "monday ";
            if (args.length > 14 && !args[14].isEmpty() && parseBoolean(args[14])) weekdays += "tuesday ";
            if (args.length > 15 && !args[15].isEmpty() && parseBoolean(args[15])) weekdays += "wednesday ";
            if (args.length > 16 && !args[16].isEmpty() && parseBoolean(args[16])) weekdays += "thursday ";
            if (args.length > 17 && !args[17].isEmpty() && parseBoolean(args[17])) weekdays += "friday ";
            if (args.length > 18 && !args[18].isEmpty() && parseBoolean(args[18])) weekdays += "saturday ";
            if (weekdays.length() > 0) filterByWeekDays(results, weekdays);

//            if (!args[19].isBlank()) Airport.filterByTerminal(results, parseInt(args[19]));

            String newLine = args[0].equalsIgnoreCase("HTML") ? "<br>" : "\n";

            if (results.isEmpty())
                System.out.print("empty flights list! too much or invalid filters!" + newLine);
            else results.forEach(result ->
                    System.out.print(result + newLine)
            );
        }
    }
}
