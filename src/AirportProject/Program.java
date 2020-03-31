package AirportProject;

import java.time.LocalDate;

public class Program {
	public static void main(String[] args) {
		LocalDate D1 = LocalDate.of(2020, 11, 23);
		LocalDate D2 = LocalDate.of(2020, 11, 22);
		LocalDate D3 = LocalDate.of(2020, 11, 21);
		LocalDate D4 = LocalDate.of(2020, 11, 20);
		// plane
		Plane P1 = new Plane("Wright Flyer", 4);
		Plane P2 = new Plane("Supermarine Spitfire", 15);
		Plane P3 = new Plane("Boeing 787", 150);
		Plane P4 = new Plane("Learjet 23", 16);
		Plane P5 = new Plane("C-130", 94);
		Plane P6 = new Plane("Boeing B-29", 120);
		Plane P7 = new Plane("F-35", 2);

		// flight
		Flight F1 = new Flight("SAD1978", P1, D1, "Israel", "Albania");
		Flight F2 = new Flight("HASF1238", P2, D1, "Austria", "Israel");
		Flight F3 = new Flight("ASD41IDA", P3, D2, "Guinea", "Israel");
		Flight F4 = new Flight("ASD41IDA", P4, D3, "Israel", "Ireland");
		Flight F5 = new Flight("DFWQ41F", P5, D4, "Israel", "Italy");
		Flight F6 = new Flight("UR24EE", P6, D3, "Laos", "Israel");
		Flight F7 = new Flight("KSD556Q", P7, D1, "Israel", "Iran");

		Airport ap = new Airport();
		System.out.println("All:");
		ap.addFlight(F1);
		ap.addFlight(F2);
		ap.addFlight(F3);
		ap.addFlight(F4);
		ap.addFlight(F5);
		ap.addFlight(F6);
		ap.addFlight(F7);
		ap.show();

		// remove by ref
		System.out.println("\nremoved even numbers:");
		ap.removeFlight(F2);
		ap.removeFlight(F4);
		ap.removeFlight(F6);
		ap.show();

		System.out.println("\nsorted by number of Passengers:");
		ap.sortByNumOfPassengers();
		ap.show();
		
		System.out.println("\nsorted by date:");
		ap.sortByDate();
		ap.show();

	}

}
