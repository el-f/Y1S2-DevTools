package AirportProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Airport {
    private ArrayList<Flight> flights;
    private int currentFlightsNum;
    public final static int MAX_FLIGHTS = 200;


    public void initializeFromUser() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter number of flights");
        currentFlightsNum = scan.nextInt();
        for (int i = 0; i < currentFlightsNum; i++) {
            System.out.println("please enter flight #" + i + " details:");
            flights.add(new Flight(scan, "user"));
        }

        scan.close();
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public Airport(File file) throws FileNotFoundException {
        Scanner s = new Scanner(file);
        currentFlightsNum = s.nextInt();
        flights = new ArrayList<>();
        for (int i = 0; i < currentFlightsNum; i++) {
            flights.add(new Flight(s));
        }
        s.close();
    }

    public void save(String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        PrintWriter writer = new PrintWriter(file);
        writer.println(flights.size());
        for (Flight f : flights) {
            f.save(writer);
        }
        writer.close();
    }

    public Airport() {
        flights = new ArrayList<>();
    }

    public boolean addFlight(Flight flight) {
        if (flights.contains(flight))
            return false;
        if (flights.size() < MAX_FLIGHTS) {
            return flights.add(flight);
        }
        return false;
    }

    public void sortByNumOfPassengers() {
        flights.sort((f1, f2) -> Integer.compare(f2.getNumPassengers(), f1.getNumPassengers()));
    }

    public void sortByDirection() {
        flights.sort((f1, f2) -> Boolean.compare(f2.isOutgoing(), f1.isOutgoing()));
    }

    public void sortByDateTime() {
        flights.sort(Comparator.comparing(Flight::getDate));
    }

    public boolean removeFlight(Flight flight) {
        return flights.remove(flight);
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

    public List<Flight> getFlightsByCountry(String country) {
        return flights.stream().filter(f -> f.getCountry().toLowerCase().equals(country)).collect(Collectors.toList());
    }

    public List<Flight> getFlightsByCity(String city) {
        return flights.stream().filter(f -> f.getCity().toLowerCase().equals(city)).collect(Collectors.toList());
    }

    public List<Flight> getFlightsByAirport(String airport) {
        return flights.stream().filter(f -> f.getAirportName().toLowerCase().equals(airport)).collect(Collectors.toList());
    }

    public List<Flight> getFlightsByDateRange(LocalDateTime start, LocalDateTime end) {
        return flights.stream().filter(f -> (f.getDate().isAfter(start) || f.getDate().isEqual(start)) &&
                (f.getDate().isBefore(end) || f.getDate().isEqual(end))).collect(Collectors.toList());
    }

    public List<Flight> getFlightsByTerminal(int terminalNum) {
        return flights.stream().filter(f -> f.getTerminal() == terminalNum).collect(Collectors.toList());
    }

    public List<Flight> getFlightsByDayOfWeek(DayOfWeek dayOfWeek) {
        return flights.stream().filter(f -> f.getDate().getDayOfWeek().name().equals(dayOfWeek.name())).collect(Collectors.toList());
    }


    public void show() {
        flights.forEach(System.out::println);
    }

    // same as show()
    public String toString() {
        StringBuilder res = new StringBuilder();
        flights.forEach(f -> res.append(f).append("\n"));
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
        System.out.println("Enter model of plane");
        input.setPlane(new Plane());
        input.getPlane().setModel(s.next());
        System.out.println("Enter num passengers (up to: " + Plane.MAX_PASSENGERS + " passengers)");
        input.getPlane().setPassengers(s.nextInt());
        System.out.println("Please enter date and time FORMAT:[year month day hour minutes]");
        input.setDateTime(Flight.getDateTimeFromUser(s));
        System.out.println("Please enter country of" + dir);
        input.setCountry(s.next());
        flights.add(input);
    }


}
