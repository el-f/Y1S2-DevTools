package AirportProject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Plane {

    private String model;
    public static final int MAX_PASSENGERS = 100;
    private int passengers;

    public Plane(String _model, int _passengers) {
        setModel(_model);
        setPassengers(_passengers);
    }

    private void setPassengers(int _passengers) {
        passengers = _passengers > 0 && _passengers < MAX_PASSENGERS ? _passengers : 0;
    }

    private void setModel(String _model) {
        model = _model;
    }

    public int getNumPassengers() {
        return passengers;
    }

    public Plane() {
        this("", 0);
    }

    public boolean addPassengers(int add) {
        if (passengers + add <= MAX_PASSENGERS) {
            passengers += add;
            return true;
        }
        return false;
    }

    public boolean removePassengers(int remove) {
        if (passengers - remove >= 0) {
            passengers -= remove;
            return true;
        }
        return false;
    }

    public String toString() {
        return "Model: " + model + ", Passengers: " + passengers + "/" + MAX_PASSENGERS;
    }

    public void save(PrintWriter writer) throws FileNotFoundException {
        writer.println(model);
        writer.println(passengers);
    }

    public Plane(Scanner input) {
        setModel(input.nextLine());
        input.nextLine();
        setPassengers(input.nextInt());
    }

}
