package AirportProject;

public class MyException extends Exception {
    private final String msg;

    public MyException(String _msg) {
        msg = _msg;
    }

    public String getMessage() {
        return msg;
    }
}
