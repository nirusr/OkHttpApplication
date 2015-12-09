package com.walmart.okhttpapplication.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by sgovind on 12/4/15.
 */
@Table(name = "geoposition")
public class LocLatLng extends Model {

    @Column (name = "lat")
    private double lat;
    @Column (name = "lng")
    private double lng;
    @Column (name ="postcode", index = true, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String postcode;

    public LocLatLng() {
        super();

    }

    public LocLatLng( double lat, double lng, String postcode) {

        this.lat = lat;
        this.lng = lng;
        this.postcode = postcode;
    }

    public static List<LocLatLng> getLocLatLng ( String columnName, String columnValue) {
        String predicate = columnName + " = ?" + columnValue;
        List<LocLatLng> results ;
        results = new Select().from(LocLatLng.class).where(predicate).execute();
        return results;


    }
    public static LocLatLng getByPostCode(String postcode) {
        LocLatLng result = new Select().from(LocLatLng.class).where("postcode = ?", postcode).executeSingle();
        return result;
    }


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
