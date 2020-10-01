package AirportProject;

import lombok.*;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {

    private int terminal;
    private String flightNum;
    private LocalDateTime dateTime;
    private String country;
    private String city;
    private String airportName;
    private String company;
    private boolean outgoing;
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm (EEEE)", Locale.ENGLISH);

    /*--------------------------------------------DEFAULT FLIGHTS-----------------------------------------------------*/
    //default dates
    public static final LocalDateTime D1 = LocalDateTime.of(2020, 11, 20, 2, 3);
    public static final LocalDateTime D2 = LocalDateTime.of(2020, 11, 21, 14, 30);
    public static final LocalDateTime D3 = LocalDateTime.of(2020, 11, 22, 19, 59);
    public static final LocalDateTime D4 = LocalDateTime.of(2020, 11, 23, 11, 16);

    // default flights
    public static final Flight F1 = new Flight(3, "SAD1978G", D1, "Albania", "albania_city", "albania_airport", "AlbaniaAir", true);
    public static final Flight F2 = new Flight(1, "HASF1238", D1, "Austria", "Austria_city", "Austria_airport", "EasyJet", false);
    public static final Flight F3 = new Flight(2, "ASD41IDA", D2, "Guinea", "Guinea_city", "Guinea_airpot", "Aeroflot", true);
    public static final Flight F4 = new Flight(1, "W594XF2B", D3, "Ireland", "Ireland_city", "Ireland_airport", "Lufthansa", false);
    public static final Flight F5 = new Flight(2, "DFWQ41FC", D4, "Italy", "Italy_city", "Italy_airport", "Ryan-Air", true);
    public static final Flight F6 = new Flight(3, "UR24EE5J", D3, "Laos", "Laos_city", "Laos_airport", "United", false);
    public static final Flight F7 = new Flight(1, "KSD556Q2", D1, "Iran", "Iran_city", "Iran_airport", "El-AL", true);
    public static final Flight F8 = new Flight(1, "KS9782EL", D4, "UK", "London", "london_airport", "Wizz", true);

    public static List<Flight> getDefaultFlights() {
        return Arrays.asList(F1, F2, F3, F4, F5, F6, F7, F8);
    }
    /*----------------------------------------------------------------------------------------------------------------*/

    Flight(String[] params) {
        this(
                Integer.parseInt(params[0]),        /*terminal*/
                params[1],                          /*flightNum*/
                LocalDateTime.of(                   /*dateTime*/
                        Integer.parseInt(params[2]),/*year*/
                        Integer.parseInt(params[3]),/*month*/
                        Integer.parseInt(params[4]),/*day*/
                        Integer.parseInt(params[5]),/*hour*/
                        Integer.parseInt(params[6]) /*minute*/
                ),
                params[7],                          /*country*/
                params[8],                          /*city*/
                params[9],                          /*airport*/
                params[10],                         /*company*/
                Boolean.parseBoolean(params[11])    /*outgoing?*/
        );
    }

    void save(PrintWriter writer) {
        Arrays.asList(
                terminal,
                flightNum,
                dateTime.getYear(),
                dateTime.getMonth().getValue(),
                dateTime.getDayOfMonth(),
                dateTime.getHour(),
                dateTime.getMinute(),
                country,
                city,
                airportName,
                company
        ).forEach(field -> writer.print(field + ","));
        writer.println(outgoing); //adding separately to avoid last comma
    }

    static LocalDateTime getDateTimeFromUser(Scanner s) {
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

    static LocalDateTime getDateTimeFromUser(int day, int month, int year) {
        return LocalDateTime.of(year, month, day, 0, 0);
    }

    public String toString() {
        String dir = outgoing ? " Departing To" : " Arriving From";
        return flightNum + " | Terminal: " + terminal +
                " | " + dateTime.format(formatter) + " | " + company + " |" + dir + ": (country: " +
                country + ", city: " + city + ", airport: " + airportName + ")";
    }

}
