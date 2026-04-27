import java.util.ArrayList;

public class Scenario {
    private String name;
    private Mode mode;
    private ArrayList<Dimension> dimensions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void addDimension(Dimension e) {
        dimensions.add(e);
    }

    public ArrayList<Dimension> getDimensions() {
        return dimensions;
    }

    public Scenario(String name, Mode mode) {
        this.name = name;
        this.mode = mode;
        this.dimensions = new ArrayList<>();
    }
}
