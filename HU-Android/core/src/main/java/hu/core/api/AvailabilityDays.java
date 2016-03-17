package hu.core.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JGabrielFreitas on 16/03/16.
 */
public class AvailabilityDays {

    private String id;

    @SerializedName("is_available")
    private boolean isAvailable;

    private String date;

    public String getId() {
        return id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getDate() {
        return date;
    }

    public String toString() {
        return "AvailabilityDays{" +
                "id='" + id + '\'' +
                ", isAvailable=" + isAvailable +
                ", date='" + date + '\'' +
                '}';
    }
}
