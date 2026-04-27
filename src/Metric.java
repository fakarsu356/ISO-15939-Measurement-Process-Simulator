public class Metric {
    private String name;
    private Double coefficient;
    private boolean direction;
    private double minRange;
    private double maxRange;
    private String unit;

    public Metric(String name, Double coefficient, boolean direction, double minRange, double maxRange, String unit) {
        this.name = name;
        this.coefficient = coefficient;
        this.direction = direction;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public boolean getDirection() {
        return direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public double getminRange() {
        return minRange;
    }

    public void setminRange(double minRange) {
        this.minRange = minRange;
    }

    public double getmaxRange() {
        return maxRange;
    }

    public void setmaxRange(double maxRange) {
        this.maxRange = maxRange;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}

