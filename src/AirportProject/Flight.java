package AirportProject;

import java.time.LocalDate;

public class Flight {

	private String flightNum;
	private Plane plane;
	private LocalDate date;
	private String origin;
	private String destination;

	public Flight(String _flightNum, Plane _plane, LocalDate _date, String _origin, String _destination) {
		flightNum = _flightNum;
		plane = _plane;
		date = _date;
		origin = _origin;
		destination = _destination;
	}

	public Flight() {
		this("", null, null, "", "");
	}

	public int getNumPassengers() {
		return plane.getNumPassengers();
	}

	public void changeDirection() {
		String temp = origin;
		origin = destination;
		destination = temp;
	}

	public String toString() {
		return "Flight: " + flightNum + " | " + plane.toString() + ", date: " + date + ", Origin: " + origin
				+ ", Destination: " + destination;
	}

}
