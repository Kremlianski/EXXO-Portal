package ru.exxo;

public class imgFormatException extends Exception {

    private String msg;

    public imgFormatException() {
        msg = null;
    }

    public imgFormatException(String s) {
        msg = s;
    }

    @Override
    public String toString() {
        return msg;
    }
}
