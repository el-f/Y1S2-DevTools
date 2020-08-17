package AirportProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.*;

public class Program {

    public static final String defaultFile = "airport.csv";
    public static final String TEST_IDENTIFIER = "INTERNAL_TEST";

    public static void main(String[] args) throws IOException {
        if (args.length == 0)
            Menu.showMenu(null);
        else if (args.length == 1)
            Menu.showMenu(args[0]); //passing filepath argument to menu
        else {
            //external java calls need full path, internal tests only need filename.
            String path;
            if (args[0].equalsIgnoreCase(TEST_IDENTIFIER)) path = defaultFile;
            else path = Paths.get("").toAbsolutePath().getParent() + "/" + defaultFile;
            Airport ap = new Airport(new File(path));

            List<Flight> results = new ArrayList<>(ap.getFlights());

            if (args[1].equalsIgnoreCase("departures"))
                results.removeAll(ap.getIncomingFlights());
            else if (args[1].equalsIgnoreCase("arrivals"))
                results.removeAll(ap.getOutgoingFlights());

            if (!args[2].isBlank()) Airport.filterByCountry(results, args[2]);
            if (!args[3].isBlank()) Airport.filterByCity(results, args[3]);
            if (!args[4].isBlank()) Airport.filterByAirport(results, args[4]);
            if (!args[5].isBlank()) Airport.filterByCompany(results, args[5]);

            if (!args[6].isBlank() && !args[7].isBlank() && !args[8].isBlank())
                Airport.filterByStartDate(
                        results,
                        Flight.getDateTimeFromUser(
                                parseInt(args[6]),  /*day*/
                                parseInt(args[7]),  /*month*/
                                parseInt(args[8])   /*year*/
                        )
                );

            if (!args[9].isBlank() && !args[10].isBlank() && !args[11].isBlank())
                Airport.filterByEndDate(
                        results,
                        Flight.getDateTimeFromUser(
                                parseInt(args[9]),  /*day*/
                                parseInt(args[10]), /*month*/
                                parseInt(args[11])  /*year*/
                        )
                );

            String weekdays = "";
            if (!args[12].isBlank() && Boolean.parseBoolean(args[12])) weekdays += "sunday ";
            if (!args[13].isBlank() && Boolean.parseBoolean(args[13])) weekdays += "monday ";
            if (!args[14].isBlank() && Boolean.parseBoolean(args[14])) weekdays += "tuesday ";
            if (!args[15].isBlank() && Boolean.parseBoolean(args[15])) weekdays += "wednesday ";
            if (!args[16].isBlank() && Boolean.parseBoolean(args[16])) weekdays += "thursday ";
            if (!args[17].isBlank() && Boolean.parseBoolean(args[17])) weekdays += "friday ";
            if (!args[18].isBlank() && Boolean.parseBoolean(args[18])) weekdays += "saturday ";
            if (weekdays.length() > 0) Airport.filterByWeekDays(results, weekdays);

//            if (!args[19].isBlank()) Airport.filterByTerminal(results, parseInt(args[19]));

            if (results.isEmpty())
                System.out.println("empty flights list! too much or invalid filters!");
            else results.forEach(result -> System.out.println(result + (
                    args[0].equalsIgnoreCase("TEXT") ? "" :
                            args[0].equalsIgnoreCase("HTML") ? "<br>" : ""))
            );
        }

//        initDefault();        //for initiating the default files.
    }

    @SuppressWarnings({"unused","RedundantSuppression"})
    private static void initDefault() throws IOException {
        Airport ap = new Airport();
        ap.getFlights().addAll(Flight.getDefaultFlights());
        System.out.println(ap);
        ap.save(defaultFile);
        Airport ap2 = new Airport(new File(defaultFile));
        System.out.println(ap.toString().equals(ap2.toString()) ? "Saved Successfully!" : "ERROR");
    }
}
