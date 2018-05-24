package com.group.avengers.tourmate.Models;

public class Event {
    private String id;
    private String eventName;
    private String location;
    private String destination;
    private String createdDate;
    private String deparatureDate;
    private String budget;

    public Event() {
    }

    public Event(String eventName, String location, String destination, String createdDate, String deparatureDate, String budget) {
        this.eventName = eventName;
        this.location = location;
        this.destination = destination;
        this.createdDate = createdDate;
        this.deparatureDate = deparatureDate;
        this.budget = budget;
    }

    public Event(String id, String eventName, String location, String destination, String createdDate, String deparatureDate, String budget) {
        this.id = id;
        this.eventName = eventName;
        this.location = location;
        this.destination = destination;
        this.createdDate = createdDate;
        this.deparatureDate = deparatureDate;
        this.budget = budget;
    }

    public String getEventName() {
        return eventName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDeparatureDate() {
        return deparatureDate;
    }

    public void setDeparatureDate(String deparatureDate) {
        this.deparatureDate = deparatureDate;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", eventName='" + eventName + '\'' +
                ", location='" + location + '\'' +
                ", destination='" + destination + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", deparatureDate='" + deparatureDate + '\'' +
                ", budget='" + budget + '\'' +
                '}';
    }
}
