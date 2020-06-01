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

    // planes
    public static final Plane P1 = new Plane("Wright-Flyer", 4);
    public static final Plane P2 = new Plane("Supermarine-Spitfire", 15);
    public static final Plane P3 = new Plane("Boeing-787", 24);
    public static final Plane P4 = new Plane("Learjet-23", 16);
    public static final Plane P5 = new Plane("C-130", 94);
    public static final Plane P6 = new Plane("Boeing_B-29", 58);
    public static final Plane P7 = new Plane("F-35", 2);

    // flights
    public static final Flight F1 = new Flight(3, "SAD1978", P1, D1, "Albania", "albania_city", "albania_airport", "AlbaniaAir", true);
    public static final Flight F2 = new Flight(1, "HASF1238", P2, D1, "Austria", "Austria_city", "Austria_airport", "EasyJet", false);
    public static final Flight F3 = new Flight(2, "ASD41IDA", P3, D2, "Guinea", "Guinea_city", "Guinea_airpot", "Aeroflot", true);
    public static final Flight F4 = new Flight(1, "ASD41IDA", P4, D3, "Ireland", "Ireland_city", "Ireland_airport", "Lufthansa", false);
    public static final Flight F5 = new Flight(2, "DFWQ41F", P5, D4, "Italy", "Italy_city", "Italy_airport", "Ryan-Air", true);
    public static final Flight F6 = new Flight(3, "UR24EE", P6, D3, "Laos", "Laos_city", "Laos_airport", "United", false);
    public static final Flight F7 = new Flight(1, "KSD556Q", P7, D1, "Iran", "Iran_city", "Iran_airport", "El-AL", true);
    public static final Flight F8 = new Flight(1, "KS9782E", P3, D4, "UK", "London", "london_airport", "Wizz", true);

    public static List<Flight> getDefaultFlights() {
        return Arrays.asList(F1, F2, F3, F4, F5, F6, F7, F8);
    }
}
