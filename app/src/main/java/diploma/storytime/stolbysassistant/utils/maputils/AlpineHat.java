package diploma.storytime.stolbysassistant.utils.maputils;

public class AlpineHat {
    double[] coordinates;
    String[] names;

    public AlpineHat(double[] coordinates, String[] names) {
        this.coordinates = coordinates;
        this.names = names;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }
}
