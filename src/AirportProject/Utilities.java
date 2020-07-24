package AirportProject;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Utilities {
    //dates
    public static final LocalDateTime D1 = LocalDateTime.of(2020, 11, 20, 2, 3);
    public static final LocalDateTime D2 = LocalDateTime.of(2020, 11, 21, 14, 30);
    public static final LocalDateTime D3 = LocalDateTime.of(2020, 11, 22, 19, 59);
    public static final LocalDateTime D4 = LocalDateTime.of(2020, 11, 23, 11, 16);

    // flights
    public static final Flight F1 = new Flight(3, "SAD1978", D1, "Albania", "albania_city", "albania_airport", "AlbaniaAir", true);
    public static final Flight F2 = new Flight(1, "HASF1238", D1, "Austria", "Austria_city", "Austria_airport", "EasyJet", false);
    public static final Flight F3 = new Flight(2, "ASD41IDA", D2, "Guinea", "Guinea_city", "Guinea_airpot", "Aeroflot", true);
    public static final Flight F4 = new Flight(1, "ASD41IDA", D3, "Ireland", "Ireland_city", "Ireland_airport", "Lufthansa", false);
    public static final Flight F5 = new Flight(2, "DFWQ41F", D4, "Italy", "Italy_city", "Italy_airport", "Ryan-Air", true);
    public static final Flight F6 = new Flight(3, "UR24EE", D3, "Laos", "Laos_city", "Laos_airport", "United", false);
    public static final Flight F7 = new Flight(1, "KSD556Q", D1, "Iran", "Iran_city", "Iran_airport", "El-AL", true);
    public static final Flight F8 = new Flight(1, "KS9782E", D4, "UK", "London", "london_airport", "Wizz", true);

    public static List<Flight> getDefaultFlights() {
        return Arrays.asList(F1, F2, F3, F4, F5, F6, F7, F8);
    }
}
