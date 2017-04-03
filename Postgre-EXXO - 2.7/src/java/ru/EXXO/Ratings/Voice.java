package ru.EXXO.Ratings;

import ru.EXXO.Status;

public abstract class Voice implements java.io.Serializable, ru.EXXO.changeable {

    private int target_id;
    private int employee_id;
    private int rating_id;
    private ratingTypes type;
    private int result;
    Status s;

    public Voice(int target_id, int rating_id, int employee_id, ratingTypes type, int result, Status s) {
        this.target_id = target_id;
        this.rating_id = rating_id;
        this.employee_id = employee_id;
        this.type = type;
        this.result = result;
        this.s = s;
    }

    public Status getStatus() {
        return s;
    }

    public void setStatus(Status s) {
        this.s = s;
    }

    public int getTarget_id() {
        return target_id;
    }

    public void setTarget_id(int target_id) {
        this.target_id = target_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public ratingTypes getType() {
        return type;
    }

    public void setType(ratingTypes type) {
        this.type = type;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getRating_id() {
        return rating_id;
    }

    public void setRating_id(int rating_id) {
        this.rating_id = rating_id;
    }
}
