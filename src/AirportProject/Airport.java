package AirportProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import static java.util.Collections.*;

public class Airport {
    private ArrayList<Flight> flights;
    private int currentFlightsNum;
    public final static int MAX_FLIGHTS = 200;


    public void initializeFromUser() {
        Scanner scan = new Scanner(System.in);


        scan.close();
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

    public void save() throws FileNotFoundException {
        File file = new File("airport");
        PrintWriter writer = new PrintWriter(file);
        flights = new ArrayList<>();
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

    public void sortByDate() {
        flights.sort(Comparator.comparing(Flight::getDate));
    }

    public boolean removeFlight(Flight flight) {
        return flights.remove(flight);
    }

    public void showOutgoingFlights() {
        flights.forEach(f -> {
            if (f.isOutgoing())
                System.out.println(f);
        });
    }

    public void showIncomingFlights() {
        flights.forEach(f -> {
            if (!f.isOutgoing())
                System.out.println(f);
        });
    }


    public void show() {
        flights.forEach(System.out::println);
    }

    // same as show()
//	public String toString() {
//		StringBuilder res = new StringBuilder();
//		flights.forEach(f -> res.append(f + "\n"));
//		return res.toString();
//	}

}
