import java.util.ArrayList;

public class ScenarioRepository {
   private  ArrayList<Scenario> scenarios;

public ScenarioRepository() {
    scenarios = new ArrayList<>();

    // --- EDUCATION - SCENARIO C (PDF Sayfa 4'teki Tam Veri) ---
    Scenario scenarioC = new Scenario("Scenario C — Team Alpha", Mode.EDUCATION);

    // Usability Boyutu (Katsayı: 25)
    Dimension usability = new Dimension("Usability", 25.0);
    // Metric(ad, katsayı, higherIsBetter, min, max, birim)
    usability.addMetric(new Metric("SUS score", 50.0, true, 0.0, 100.0, "points"));
    usability.addMetric(new Metric("Onboarding time", 50.0, false, 0.0, 60.0, "min"));
    scenarioC.addDimension(usability);

    // Performance Efficiency Boyutu (Katsayı: 20)
    Dimension perfEff = new Dimension("Perf. Efficiency", 20.0);
    perfEff.addMetric(new Metric("Video start time", 50.0, false, 0.0, 15.0, "sec"));
    perfEff.addMetric(new Metric("Concurrent exams", 50.0, true, 0.0, 600.0, "users"));
    scenarioC.addDimension(perfEff);

    // Accessibility Boyutu (Katsayı: 20)
    Dimension accessibility = new Dimension("Accessibility", 20.0);
    accessibility.addMetric(new Metric("WCAG compliance", 50.0, true, 0.0, 100.0, "%"));
    accessibility.addMetric(new Metric("Screen reader score", 50.0, true, 0.0, 100.0, "%"));
    scenarioC.addDimension(accessibility);

    // Reliability Boyutu (Katsayı: 20)
    Dimension reliability = new Dimension("Reliability", 20.0);
    reliability.addMetric(new Metric("Uptime", 50.0, true, 95.0, 100.0, "%"));
    reliability.addMetric(new Metric("MTTR", 50.0, false, 0.0, 120.0, "min"));
    scenarioC.addDimension(reliability);

    // Functional Suitability Boyutu (Katsayı: 15)
    Dimension funcSuit = new Dimension("Func. Suitability", 15.0);
    funcSuit.addMetric(new Metric("Feature completion", 50.0, true, 0.0, 100.0, "%"));
    funcSuit.addMetric(new Metric("Assignment submit rate", 50.0, true, 0.0, 100.0, "%"));
    scenarioC.addDimension(funcSuit);

    scenarios.add(scenarioC);


    // --- EDUCATION - SCENARIO D (Örnek) ---
    Scenario scenarioD = new Scenario("Scenario D — Team Beta", Mode.EDUCATION);
    // Buraya da Scenario C'ye benzer şekilde farklı boyutlar/metrikler ekleyebilirsin
    scenarioD.addDimension(new Dimension("Usability", 100.0)); 
    scenarios.add(scenarioD);


    // --- HEALTH - SCENARIO A ---
    Scenario scenarioA = new Scenario("Scenario A — Central Hospital", Mode.HEALTH);
    Dimension patientSafety = new Dimension("Patient Safety", 100.0);
    patientSafety.addMetric(new Metric("Data Privacy", 100.0, true, 0.0, 100.0, "%"));
    scenarioA.addDimension(patientSafety);
    scenarios.add(scenarioA);


    // --- HEALTH - SCENARIO B ---
    Scenario scenarioB = new Scenario("Scenario B — Local Clinic", Mode.HEALTH);
    scenarioB.addDimension(new Dimension("Efficiency", 100.0));
    scenarios.add(scenarioB);
}


  public ArrayList<Scenario> getScenariosByMode(Mode mode) {
    ArrayList<Scenario> list = new ArrayList<>();
    for(Scenario s :scenarios){
        if(s.getMode()==mode){
            list.add(s);
        }
    
    }
    return list;
  }
public Scenario  getScenarioByName(String name){
   
    for(Scenario s :scenarios){
        if(s.getName().equals(name)){
            return s;
        }
    
    }
    return null;
}
}
