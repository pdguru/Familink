package msc.oulu.fi.familink.location;

import java.util.Date;

/**
 * Created by pramodguruprasad on 02/05/16.
 */
public class LocationObject {
    String username;
    Double latitude, longitude;
    Date date;

    public LocationObject(String username, Double lat, double lng, Date date) {
        this.username = username;
        latitude = lat;
        longitude = lng;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Date getDate() {
        return date;
    }
}
