package AirportProject;

@SuppressWarnings("SameParameterValue")
public class MyException extends Exception {
    private final String msg;

     MyException(String _msg) {
        msg = _msg;
    }

    public String getMessage() {
        return msg;
    }
}
