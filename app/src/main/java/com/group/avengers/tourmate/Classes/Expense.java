package com.group.avengers.tourmate.Classes;

public class Expense {

    private String expID;
    private String amount;
    private String timeDate;
    private String comment;

    public Expense() {
    }

    public Expense(String amount, String timeDate, String comment) {
        this.amount = amount;
        this.timeDate = timeDate;
        this.comment = comment;
    }

    public Expense(String expID, String amount, String timeDate, String comment) {
        this.expID = expID;
        this.amount = amount;
        this.timeDate = timeDate;
        this.comment = comment;
    }

    public String getExpID() {
        return expID;
    }

    public void setExpID(String expID) {
        this.expID = expID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
