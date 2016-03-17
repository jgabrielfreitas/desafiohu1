package hu.core.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JGabrielFreitas on 16/03/16.
 */
public class Hotel {

    private String id;

    private String location;

    private String name;

    @SerializedName("availability_days")
    private List<AvailabilityDays> availabilityDays;

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public List<AvailabilityDays> getAvailabilityDays() {
        return availabilityDays;
    }

    public String toString() {
        return "Hotel{" +
                "id='" + id + '\'' +
                ", location='" + location + '\'' +
                ", name='" + name + '\'' +
                ", availabilityDays=" + availabilityDays +
                '}';
    }
}
