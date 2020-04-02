package AirportProject;

import java.util.ArrayList;
import java.util.Collections;

public class Airport {
	private ArrayList<Flight> flights;
	public final static int MAX_FLIGHTS = 200;

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
		Collections.sort(flights, (a, b) -> a.getNumPassengers() < b.getNumPassengers() ? 1
				: a.getNumPassengers() == b.getNumPassengers() ? 0 : -1);
	}

	public void sortByDate() {
		Collections.sort(flights, (a, b) -> a.getDate().compareTo(b.getDate()));
	}

	public boolean removeFlight(Flight flight) {
		return flights.remove(flight);
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
