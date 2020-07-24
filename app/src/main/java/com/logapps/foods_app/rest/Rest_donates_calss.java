package com.logapps.foods_app.rest;

public class Rest_donates_calss {
    private String name ;
    private String details ;

    public Rest_donates_calss(String name, String details) {
        this.name = name;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "All_donates_class{" +
                "name='" + name + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
