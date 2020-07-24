package AirportProject;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Flight {

    private int terminal;
    private String flightNum;
    private LocalDateTime dateTime;
    private String country;
    private String city;
    private String airportName;
    private String company;
    private boolean outgoing;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm (EEEE)");

    /*--------------------------------------------DEFAULT FLIGHTS-----------------------------------------------------*/
    //default dates
    public static final LocalDateTime D1 = LocalDateTime.of(2020, 11, 20, 2, 3);
    public static final LocalDateTime D2 = LocalDateTime.of(2020, 11, 21, 14, 30);
    public static final LocalDateTime D3 = LocalDateTime.of(2020, 11, 22, 19, 59);
    public static final LocalDateTime D4 = LocalDateTime.of(2020, 11, 23, 11, 16);

    // default flights
    public static final Flight F1 = new Flight(3, "SAD1978", D1, "Albania", "albania_city", "albania_airport", "AlbaniaAir", true);
    public static final Flight F2 = new Flight(1, "HASF1238", D1, "Austria", "Austria_city", "Austria_airport", "EasyJet", false);
    public static final Flight F3 = new Flight(2, "ASD41IDA", D2, "Guinea", "Guinea_city", "Guinea_airpot", "Aeroflot", true);
    public static final Flight F4 = new Flight(1, "W594XF2B", D3, "Ireland", "Ireland_city", "Ireland_airport", "Lufthansa", false);
    public static final Flight F5 = new Flight(2, "DFWQ41F", D4, "Italy", "Italy_city", "Italy_airport", "Ryan-Air", true);
    public static final Flight F6 = new Flight(3, "UR24EE", D3, "Laos", "Laos_city", "Laos_airport", "United", false);
    public static final Flight F7 = new Flight(1, "KSD556Q", D1, "Iran", "Iran_city", "Iran_airport", "El-AL", true);
    public static final Flight F8 = new Flight(1, "KS9782E", D4, "UK", "London", "london_airport", "Wizz", true);

    public static List<Flight> getDefaultFlights() {
        return Arrays.asList(F1, F2, F3, F4, F5, F6, F7, F8);
    }
    /*----------------------------------------------------------------------------------------------------------------*/

    public String getCompany() {
        return company;
    }

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
        dateTime = LocalDateTime.of(input.nextInt(), input.nextInt(),
                input.nextInt(), input.nextInt(), input.nextInt());
        country = input.next();
        city = input.next();
        airportName = input.next();
        company = input.next();
        outgoing = Boolean.parseBoolean(input.next());
    }

    public void save(PrintWriter writer) {
        writer.println(terminal);
        writer.println(flightNum);
        writer.println(dateTime.getYear() + " " + dateTime.getMonth().getValue() +
                " " + dateTime.getDayOfMonth() + " " + dateTime.getHour() + " " +
                dateTime.getMinute());
        writer.println(country);
        writer.println(city);
        writer.println(airportName);
        writer.println(company);
        writer.println(outgoing);
    }

    public Flight(Scanner scan, String user) {
        System.out.println("please enter terminal number:");
        terminal = scan.nextInt();
        System.out.println("please enter flight Num (letters + digits)");
        flightNum = scan.next();
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
        System.out.println();
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

    public static LocalDateTime getDateTimeFromUser(int day, int month, int year) {
        return LocalDateTime.of(year, month, day, 0, 0);
    }

    public Flight(int _terminal, String _flightNum, LocalDateTime _date, String _country, String _city, String _airportName, String _company, boolean _outgoing) {
        terminal = _terminal;
        flightNum = _flightNum;
        dateTime = _date;
        country = _country;
        city = _city;
        airportName = _airportName;
        company = _company;
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

    public void setCompany(String company) {
        this.company = company;
    }

    public Flight() {
        this(0, "", null, "", "", "", "", false);
    }

    public LocalDateTime getDate() {
        return dateTime;
    }

    public void changeDirection() {
        outgoing = !outgoing;
    }

    public String toString() {
        String dir = outgoing ? " Departing To" : " Arriving From";
        return flightNum + " | Terminal: " + terminal +
                " | date/time: " + dateTime.format(formatter) + " | " + "company: " + company + " |" + dir + ": (country: " +
                country + ", city: " + city + ", airport: " + airportName + ")";
    }

}
