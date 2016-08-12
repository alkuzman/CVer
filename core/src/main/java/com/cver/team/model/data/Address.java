package com.cver.team.model.data;

import com.cver.team.model.SourceHolder;

/**
 * Created by Dimitar on 7/7/2016.
 */
public class Address extends Location {

    private String city;

    private String country;

    private String street;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}