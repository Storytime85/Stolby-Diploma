package diploma.storytime.stolbysassistant.utils.maputils;

public class Pillar {
    private double[] coordinates;
    private String[] names;
    private String[] descriptions;

    public Pillar(double[] coordinates, String[] names, String[] descriptions) {
        this.coordinates = coordinates;
        this.names = names;
        this.descriptions = descriptions;
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

    public String[] getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String[] descriptions) {
        this.descriptions = descriptions;
    }
}
