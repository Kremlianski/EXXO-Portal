package EXXOlib;

public class imgFormatException extends Exception {

    private String msg;

    public imgFormatException() {
        msg = null;
    }

    public imgFormatException(String s) {
        msg = s;
    }

    public String toString() {
        return msg;
    }
}
