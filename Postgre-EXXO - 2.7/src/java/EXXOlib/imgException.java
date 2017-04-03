package EXXOlib;

public class imgException extends Exception {

    private String msg;

    public imgException() {
        msg = null;
    }

    public imgException(String s) {
        msg = s;
    }

    public String toString() {
        return msg;
    }
}
