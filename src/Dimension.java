
import java.util.ArrayList;

public class Dimension {
     private String  name;
   private Double coefficient;
   private ArrayList<Metric> metrics;

   public Dimension(String name, Double coefficient) {
    this.name = name;
    this.coefficient = coefficient;
    metrics=new ArrayList<>();
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
  public void addMetric(Metric m){
    metrics.add(m);
  }
}
