package AirportProject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class Flight {

    private String flightNum;
    private Plane plane;
    private LocalDate date;
    private String origin;
    private String destination;
    private boolean outgoing;

    public Flight(Scanner input) {
        flightNum = input.next();
        plane = new Plane(input);
        date = LocalDate.of(input.nextInt(), input.nextInt(), input.nextInt());
        origin = input.next();
        destination = input.next();
        outgoing = Boolean.parseBoolean(input.next());
    }

    public Flight(Scanner scan, String user) {
        System.out.println("please enter flight Num (letters + digits)");
        flightNum = scan.next();
        System.out.println("please enter plane details: (model and then num of passengers):");
        plane = new Plane(scan.next(), scan.nextInt());
        System.out.println("please enter date of flight: (year, month, day)");
        date = LocalDate.of(scan.nextInt(), scan.nextInt(), scan.nextInt());
        System.out.println("please enter country of origin and destination");
        setDirection(scan.next(), scan.next());
    }

    public void save(PrintWriter writer) throws FileNotFoundException {
        writer.println(flightNum);
        plane.save(writer);
        writer.println(date.getYear() + " " + date.getMonth() + " " + date.getDayOfMonth());
        writer.println(origin);
        writer.println(destination);
        writer.println(outgoing);
    }

    public Flight(String _flightNum, Plane _plane, LocalDate _date, String _origin, String _destination) {
        flightNum = _flightNum;
        plane = _plane;
        date = _date;
        setDirection(_origin, _destination);

    }

    private void setDirection(String _origin, String _destination) {
        if (_destination.toLowerCase().equals("israel") || _origin.toLowerCase().equals("israel")) {
            destination = _destination;
            origin = _origin;
            outgoing = !_destination.toLowerCase().equals("israel");
        }

    }

    public boolean isOutgoing() {
        return outgoing;
    }


    public Flight() {
        this("", null, null, "", "");
    }

    public LocalDate getDate() {
        return date;
    }

    public int getNumPassengers() {
        return plane.getNumPassengers();
    }

    public void changeDirection() {
        String temp = origin;
        origin = destination;
        destination = temp;
        outgoing = !outgoing;
    }

    public String toString() {
        return "Flight: " + flightNum + " | " + plane.toString() + ", date: " + date + ", Origin: " + origin
                + ", Destination: " + destination;
    }

}
