package com.haiku.wateroffer.bean;

import java.io.Serializable;

/**
 * Created by hyming on 2016/7/26.
 */
public class GeoPoint implements Serializable {
    private String address;
    private String city;
    private double lat;
    private double lon;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
