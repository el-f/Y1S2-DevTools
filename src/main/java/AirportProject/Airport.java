package AirportProject;

import lombok.Cleanup;
import lombok.Data;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

@Data
public class Airport {
    private final ArrayList<Flight> flights;

    Airport() {
        flights = new ArrayList<>();
    }

    Airport(File file) throws IOException {
        this();
        Files.lines(file.toPath())
                .map(line -> line.split(","))
                .skip(1)    //skip headers
                .forEach(params -> flights.add(new Flight(params)));
        sortByDateTime();
    }

    void save(String filePath) throws FileNotFoundException {
        @Cleanup PrintWriter writer = new PrintWriter(new File(filePath));
        writer.println("Terminal,#,Year,Month,Day,Hour,Minute,Country,City,Airport,Airline,isOutgoing");
        flights.forEach(f -> f.save(writer));
    }

    void addFlight(Flight flight) {
        if (flights.contains(flight)) return;
        flights.add(flight);
    }

    void sortByDateTime() {
        flights.sort(Comparator.comparing(Flight::getDateTime));
    }

    void removeFlight(int flightIndex) {
        flights.remove(flightIndex);
    }

    void showOutgoingFlights() {
        sortByDateTime();
        getOutgoingFlights(flights).forEach(System.out::println);
    }

    void showIncomingFlights() {
        sortByDateTime();
        getIncomingFlights(flights).forEach(System.out::println);
    }


    static List<Flight> getOutgoingFlights(List<Flight> _flights) {
        return _flights
                .stream()
                .filter(Flight::isOutgoing)
                .collect(Collectors.toList());
    }

    static List<Flight> getIncomingFlights(List<Flight> _flights) {
        return _flights
                .stream()
                .filter(f -> !f.isOutgoing())
                .collect(Collectors.toList());
    }

    static void filterByDirection(List<Flight> results, String direction) {
        if (direction.equalsIgnoreCase("departures")) {
            results.removeAll(getIncomingFlights(results));
        } else if (direction.equalsIgnoreCase("arrivals"))
            results.removeAll(getOutgoingFlights(results));
    }

    static void filterByCountry(List<Flight> results, String country) {
        results.removeIf(f -> !f.getCountry().equalsIgnoreCase(country));
    }

    static void filterByCity(List<Flight> results, String city) {
        results.removeIf(f -> !f.getCity().equalsIgnoreCase(city));
    }

    static void filterByAirport(List<Flight> results, String airport) {
        results.removeIf(f -> !f.getAirportName().equalsIgnoreCase(airport));
    }

    static void filterByCompany(List<Flight> results, String company) {
        results.removeIf(f -> !f.getCompany().equalsIgnoreCase(company));
    }

    static void filterByTerminal(List<Flight> results, int terminal) {
        results.removeIf(f -> f.getTerminal() != terminal);
    }

    static void filterByWeekDays(List<Flight> results, String weekdays) {
        results.removeIf(f -> !weekdays.contains(f.getDateTime().getDayOfWeek().name().toLowerCase()));
    }

    static void filterByDateRange(List<Flight> results, LocalDateTime start, LocalDateTime end) {
        results.removeIf(f -> f.getDateTime().isBefore(start) || f.getDateTime().isAfter(end));
    }

    static void filterByStartDate(List<Flight> results, LocalDateTime start) {
        results.removeIf(f -> f.getDateTime().isBefore(start));
    }

    static void filterByEndDate(List<Flight> results, LocalDateTime end) {
        results.removeIf(f -> f.getDateTime().isAfter(end));
    }

    static void applyFiltersByArgs(String[] args, List<Flight> results) {
        if (!args[1].isEmpty()) filterByDirection(results, args[1]);
        if (!args[2].isEmpty()) filterByCountry(results, args[2]);
        if (!args[3].isEmpty()) filterByCity(results, args[3]);
        if (!args[4].isEmpty()) filterByAirport(results, args[4]);
        if (!args[5].isEmpty()) filterByCompany(results, args[5]);

        if (!args[6].isEmpty() && !args[7].isEmpty() && !args[8].isEmpty())
            filterByStartDate(
                    results,
                    Flight.getDateTimeFromUser(
                            parseInt(args[6]),  /*day*/
                            parseInt(args[7]),  /*month*/
                            parseInt(args[8])   /*year*/
                    )
            );

        if (!args[9].isEmpty() && !args[10].isEmpty() && !args[11].isEmpty())
            filterByEndDate(
                    results,
                    Flight.getDateTimeFromUser(
                            parseInt(args[9]),  /*day*/
                            parseInt(args[10]), /*month*/
                            parseInt(args[11])  /*year*/
                    )
            );

        String weekdays = "";
        if (!args[12].isEmpty() && parseBoolean(args[12])) weekdays += "sunday ";
        if (!args[13].isEmpty() && parseBoolean(args[13])) weekdays += "monday ";
        if (!args[14].isEmpty() && parseBoolean(args[14])) weekdays += "tuesday ";
        if (!args[15].isEmpty() && parseBoolean(args[15])) weekdays += "wednesday ";
        if (!args[16].isEmpty() && parseBoolean(args[16])) weekdays += "thursday ";
        if (!args[17].isEmpty() && parseBoolean(args[17])) weekdays += "friday ";
        if (!args[18].isEmpty() && parseBoolean(args[18])) weekdays += "saturday ";
        if (weekdays.length() > 0) filterByWeekDays(results, weekdays);

        if (!args[19].isEmpty()) filterByTerminal(results, parseInt(args[19]));
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < flights.size(); i++)
            res.append("(").append(i).append(") ").append(flights.get(i)).append("\n");
        return res.toString();
    }

    void getFlightFromUser(Scanner s, boolean _outgoing) {
        String dir = _outgoing ? " destination" : " origin";
        Flight input = new Flight();
        input.setOutgoing(_outgoing);
        System.out.println("Enter terminal num");
        input.setTerminal(s.nextInt());
        System.out.println("Enter flight num");
        input.setFlightNum(s.next());
        System.out.println("Please enter date and time FORMAT:[year month day hour minutes]");
        input.setDateTime(Flight.getDateTimeFromUser(s));
        System.out.println("Please enter country of" + dir);
        input.setCountry(s.next());
        System.out.println("Please enter city of");
        input.setCity(s.next());
        System.out.println("Please enter airport of");
        input.setAirportName(s.next());
        System.out.println("please enter company");
        input.setCompany(s.next());
        flights.add(input);
    }

}
