package ru.EXXO.Ratings;

public class ratingResult implements java.io.Serializable {

    private double result;

    public ratingResult(double result, int count, int target_id, int rating_id) {
        this.result = result;
        this.count = count;
        this.target_id = target_id;
        this.rating_id = rating_id;
    }

    private int count;
    private int target_id;
    private int rating_id;

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTarget_id() {
        return target_id;
    }

    public void setTarget_id(int target_id) {
        this.target_id = target_id;
    }

    public int getRating_id() {
        return rating_id;
    }

    public void setRating_id(int rating_id) {
        this.rating_id = rating_id;
    }
}
