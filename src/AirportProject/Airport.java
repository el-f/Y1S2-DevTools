package AirportProject;

import java.util.ArrayList;

public class Airport {
	private ArrayList<Flight> flights;
	public final static int MAX_FLIGHTS = 200;

	public Airport() {
		flights = new ArrayList<Flight>();
	}
	public boolean addFlight(Flight flight) {
		if (flights.size() < MAX_FLIGHTS) {
			flights.add(flight);
			return true;
		}
		return false;
	}

	public boolean removeFlight(Flight flight) {
		return flights.remove(flight);
	}

	public void show() {
		System.out.println();
		flights.forEach(System.out::println);
	}
}
