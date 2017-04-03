package ru.EXXO.Ratings;

public class FIOratingResult extends ratingResult {

    private String fio;

    public FIOratingResult(String fio, double result, int count, int target_id, int rating_id) {
        super(result, count, target_id, rating_id);
        this.fio = fio;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

}
