package AirportProject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Airport {
    private final ArrayList<Flight> flights;


    ArrayList<Flight> getFlights() {
        return flights;
    }

    Airport() {
        flights = new ArrayList<>();
    }

    Airport(File file) throws IOException {
        this();
        Files.lines(Path.of(file.getPath()))
                .map(line -> line.split(","))
                .skip(1)    //skip headers
                .forEach(params -> flights.add(new Flight(params)));
    }

    void save(String filePath) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File(filePath));
        writer.println("Terminal,#,Year,Month,Day,Hour,Minute,Country,City,Airport,Airline,isOutgoing");
        flights.forEach(f -> f.save(writer));
        writer.close();
    }

    void addFlight(Flight flight) {
        if (flights.contains(flight)) return;
        flights.add(flight);
    }

    void sortByDateTime() {
        flights.sort(Comparator.comparing(Flight::getDate));
    }

    boolean removeFlight(int flightIndex) {
        return null != flights.remove(flightIndex); //if null then the item wasn't there in the first place
    }

    void showOutgoingFlights() {
        sortByDateTime();
        getOutgoingFlights().forEach(System.out::println);
    }

    void showIncomingFlights() {
        sortByDateTime();
        getIncomingFlights().forEach(System.out::println);
    }


    List<Flight> getOutgoingFlights() {
        return flights
                .stream()
                .filter(Flight::isOutgoing)
                .collect(Collectors.toList());
    }

    List<Flight> getIncomingFlights() {
        return flights
                .stream()
                .filter(f -> !f.isOutgoing())
                .collect(Collectors.toList());
    }

    static void filterByCountry(List<Flight> result, String country) {
        result.removeIf(f -> !f.getCountry().equalsIgnoreCase(country));
    }

    static void filterByCity(List<Flight> result, String city) {
        result.removeIf(f -> !f.getCity().equalsIgnoreCase(city));
    }

    static void filterByAirport(List<Flight> result, String airport) {
        result.removeIf(f -> !f.getAirportName().equalsIgnoreCase(airport));
    }

    static void filterByCompany(List<Flight> result, String company) {
        result.removeIf(f -> !f.getCompany().equalsIgnoreCase(company));
    }

    static void filterByTerminal(List<Flight> result, int terminal) {
        result.removeIf(f -> f.getTerminal() != terminal);
    }

    static void filterByWeekDays(List<Flight> result, String weekdays) {
        result.removeIf(f -> !weekdays.contains(f.getDate().getDayOfWeek().name().toLowerCase()));
    }

    static void filterByDateRange(List<Flight> result, LocalDateTime start, LocalDateTime end) {
        result.removeIf(f -> f.getDate().isBefore(start) || f.getDate().isAfter(end));
    }

    static void filterByStartDate(List<Flight> result, LocalDateTime start) {
        result.removeIf(f -> f.getDate().isBefore(start));
    }

    static void filterByEndDate(List<Flight> result, LocalDateTime end) {
        result.removeIf(f -> f.getDate().isAfter(end));
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
