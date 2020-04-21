package AirportProject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Flight {

    private String flightNum;
    private Plane plane;
    private LocalDateTime dateTime;
    private String country;
    private boolean outgoing;

    public Flight(Scanner input) {
        flightNum = input.next();
        plane = new Plane(input);
        dateTime = LocalDateTime.of(input.nextInt(), input.nextInt(),
                input.nextInt(), input.nextInt(), input.nextInt());
        country = input.next();
        outgoing = Boolean.parseBoolean(input.next());
    }

    public Flight(Scanner scan, String user) {
        System.out.println("please enter flight Num (letters + digits)");
        flightNum = scan.next();
        System.out.println("please enter plane details: (model and then num of passengers):");
        plane = new Plane(scan.next(), scan.nextInt());
        System.out.println("please enter date of flight: (year, month, day)");
        dateTime = LocalDateTime.of(scan.nextInt(), scan.nextInt(),
                scan.nextInt(), scan.nextInt(), scan.nextInt());
        System.out.println("Please enter and if outgoing (true/false)");
        outgoing = Boolean.parseBoolean(scan.next());
        System.out.println("please enter country");
        country = scan.next();
    }

    public void save(PrintWriter writer) throws FileNotFoundException {
        writer.println(flightNum);
        plane.save(writer);
        writer.println(dateTime.getYear() + " " + dateTime.getMonth().getValue() +
                " " + dateTime.getDayOfMonth() + " " + dateTime.getHour() + " " +
                dateTime.getMinute());
        writer.println(country);
        writer.println(outgoing);
    }

    public Flight(String _flightNum, Plane _plane, LocalDateTime _date, String _country, boolean _outgoing) {
        flightNum = _flightNum;
        plane = _plane;
        dateTime = _date;
        outgoing = _outgoing;

    }

    public boolean isOutgoing() {
        return outgoing;
    }


    public Flight() {
        this("", null, null, "", false);
    }

    public LocalDateTime getDate() {
        return dateTime;
    }

    public int getNumPassengers() {
        return plane.getNumPassengers();
    }

    public void changeDirection() {
        outgoing = !outgoing;
    }

    public String toString() {
        String dir = outgoing ? "to" : "from";
        return "Flight: " + flightNum + " | " + plane.toString() + ", date and time: " + dateTime + "Direction: " + dir + ", country: " + country;
    }

}
