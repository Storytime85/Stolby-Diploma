package diploma.storytime.stolbysassistant.utils;

public class Friend {
    private String name;
    private boolean status;
    private double latitude;
    private double longitude;

    public Friend(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    public Friend(String name, boolean status, double latitude, double longitude) {
        this.name = name;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //region getters/setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    //endregion
}