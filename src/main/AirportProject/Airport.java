package AirportProject;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Airport {
    private ArrayList<Flight> flights;

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public Airport(File file) throws FileNotFoundException {
        Scanner s = new Scanner(file);
        int numOfFlights = s.nextInt();
        flights = new ArrayList<>();
        for (int i = 0; i < numOfFlights; i++) {
            flights.add(new Flight(s));
        }
        s.close();
    }

    public void save(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        PrintWriter writer = new PrintWriter(file);
        writer.println(flights.size());
        flights.forEach(f -> f.save(writer));
        writer.close();
    }

    public Airport() {
        flights = new ArrayList<>();
    }

    public boolean addFlight(Flight flight) {
        if (flights.contains(flight))
            return false;
        flights.add(flight);
        return true;
    }

    public void sortByDateTime() {
        flights.sort(Comparator.comparing(Flight::getDate));
    }

    public boolean removeFlight(int flightIndex) {
        return null != flights.remove(flightIndex); //if null then the item wasn't there in the first place
    }

    public void showOutgoingFlights() {
        sortByDateTime();
        getOutgoingFlights().forEach(System.out::println);
    }

    public void showIncomingFlights() {
        sortByDateTime();
        getIncomingFlights().forEach(System.out::println);
    }


    public List<Flight> getOutgoingFlights() {
        return flights.stream().filter(Flight::isOutgoing).collect(Collectors.toList());

    }

    public List<Flight> getIncomingFlights() {
        return flights.stream().filter(f -> !f.isOutgoing()).collect(Collectors.toList());
    }

    public static void filterByCountry(List<Flight> result, String country) {
        result.removeIf(f -> !f.getCountry().equalsIgnoreCase(country));
    }

    public static void filterByCity(List<Flight> result, String city) {
        result.removeIf(f -> !f.getCity().equalsIgnoreCase(city));
    }

    public static void filterByAirport(List<Flight> result, String airport) {
        result.removeIf(f -> !f.getAirportName().equalsIgnoreCase(airport));
    }

    public static void filterByCompany(List<Flight> result, String company) {
        result.removeIf(f -> !f.getCompany().equalsIgnoreCase(company));
    }

    public static void filterByTerminal(List<Flight> result, int terminal) {
        result.removeIf(f -> f.getTerminal() != terminal);
    }

    public static void filterByWeekDays(List<Flight> result, String weekdays) {
        result.removeIf(f -> !weekdays.contains(f.getDate().getDayOfWeek().name().toLowerCase()));
    }

    public static void filterByDateRange(List<Flight> result, LocalDateTime start, LocalDateTime end) {
        result.removeIf(f -> f.getDate().isBefore(start) || f.getDate().isAfter(end));
    }

    public static void filterByStartDate(List<Flight> result, LocalDateTime start) {
        result.removeIf(f -> f.getDate().isBefore(start));
    }

    public static void filterByEndDate(List<Flight> result, LocalDateTime end) {
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
        System.out.println("please enter company");
        input.setCompany(s.next());
        flights.add(input);
    }


}
