package AirportProject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Flight {

    private int terminal;
    private String flightNum;
    private Plane plane;
    private LocalDateTime dateTime;
    private String country;
    private String city;
    private String airportName;
    private boolean outgoing;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm (EEEE)");

    public String getCountry() {
        return country;
    }

    public int getTerminal() {
        return terminal;
    }

    public String getCity() {
        return city;
    }

    public String getAirportName() {
        return airportName;
    }

    public Flight(Scanner input) {
        terminal = input.nextInt();
        flightNum = input.next();
        plane = new Plane(input);
        dateTime = LocalDateTime.of(input.nextInt(), input.nextInt(),
                input.nextInt(), input.nextInt(), input.nextInt());
        country = input.next();
        city = input.next();
        airportName = input.next();
        outgoing = Boolean.parseBoolean(input.next());
    }

    public void save(PrintWriter writer) {
        writer.println(terminal);
        writer.println(flightNum);
        plane.save(writer);
        writer.println(dateTime.getYear() + " " + dateTime.getMonth().getValue() +
                " " + dateTime.getDayOfMonth() + " " + dateTime.getHour() + " " +
                dateTime.getMinute());
        writer.println(country);
        writer.println(city);
        writer.println(airportName);
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
        dateTime = getDateTimeFromUser(scan);
        System.out.println("Please enter and if outgoing (true/false)");
        outgoing = Boolean.parseBoolean(scan.next());
        System.out.println("please enter country");
        country = scan.next();
        System.out.println("please enter city name");
        city = scan.next();
        String dir = outgoing ? "destination" : "origin";
        System.out.println("please enter " + dir + " airport name ");
        airportName = scan.next();
    }

    public static LocalDateTime getDateTimeFromUser(Scanner s) {
        int year, month, day, hour, minutes;
        System.out.println("enter year");
        year = s.nextInt();
        System.out.println("enter month");
        month = s.nextInt();
        System.out.println("enter day");
        day = s.nextInt();
        System.out.println("enter hour");
        hour = s.nextInt();
        System.out.println("enter minutes");
        minutes = s.nextInt();
        return LocalDateTime.of(year, month, day, hour, minutes);
    }

    public Flight(int _terminal, String _flightNum, Plane _plane, LocalDateTime _date, String _country, String _city, String _airportName, boolean _outgoing) {
        terminal = _terminal;
        flightNum = _flightNum;
        plane = _plane;
        dateTime = _date;
        country = _country;
        city = _city;
        airportName = _airportName;
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
        this(0, "", null, null, "", "", "", false);
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
        return "Flight: " + flightNum + " | Terminal: " + terminal +
                " | " + plane.toString() + " | date and time: " + dateTime.format(formatter) + " | " + dir + ": (country: " +
                country + ", city: " + city + ", airport: " + airportName + ")";
    }


}
