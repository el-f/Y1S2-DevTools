package AirportProject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.Scanner;

public class Flight {

    private int terminal;
    private String flightNum;
    private Plane plane;
    private LocalDateTime dateTime;
    private String country;
    private boolean outgoing;

    public Flight(Scanner input) {
        terminal = input.nextInt();
        flightNum = input.next();
        plane = new Plane(input);
        dateTime = LocalDateTime.of(input.nextInt(), input.nextInt(),
                input.nextInt(), input.nextInt(), input.nextInt());
        country = input.next();
        outgoing = Boolean.parseBoolean(input.next());
    }

    public void save(PrintWriter writer) throws FileNotFoundException {
        writer.println(terminal);
        writer.println(flightNum);
        plane.save(writer);
        writer.println(dateTime.getYear() + " " + dateTime.getMonth().getValue() +
                " " + dateTime.getDayOfMonth() + " " + dateTime.getHour() + " " +
                dateTime.getMinute());
        writer.println(country);
        writer.println(outgoing);
    }

    public Flight(Scanner scan, String user) {
        System.out.println("please enter terminal number:");
        terminal = scan.nextInt();
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

    public Flight(int _terminal, String _flightNum, Plane _plane, LocalDateTime _date, String _country, boolean _outgoing) {
        terminal = _terminal;
        flightNum = _flightNum;
        plane = _plane;
        dateTime = _date;
        country = _country;
        outgoing = _outgoing;

    }

    public boolean isOutgoing() {
        return outgoing;
    }

    public void setTerminal(int terminal) {
        this.terminal = terminal;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setOutgoing(boolean outgoing) {
        this.outgoing = outgoing;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public Plane getPlane() {
        return plane;
    }

    public Flight() {
        this(0, "", null, null, "", false);
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

    public String showDateTime() {
        return "Date: " + dateTime.getDayOfMonth() + "/" + dateTime.getMonthValue() +
                "/" + dateTime.getYear() + " Time: " + dateTime.getHour() + ":" + dateTime.getMinute();
    }


    public String toString() {
        String dir = outgoing ? " To" : " From";
        return "Flight: " + flightNum + ", Terminal: " + terminal +
                " | " + plane.toString() + ", date and time: " + dateTime + dir + " country: " + country;
    }

}
