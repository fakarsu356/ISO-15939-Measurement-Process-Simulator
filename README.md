# ISO 15939 Measurement Process Simulator

1. Architecture:
The application is designed using a Wizard structure within a single JFrame, managed by a CardLayout. The 5 core steps (Profile, Define, Plan, Collect, Analyse) are implemented as separate JPanel components.
Step Indicator: A dynamic JLabel at the top tracks progress using HTML formatting. It highlights the active step and marks completed steps with a "✓" symbol.
2. Step Logic & Validation:
Step 1 (Profile): Collects user and session data. The handleNext method performs validation to prevent empty fields using JOptionPane alerts.
Step 2 (Define): Scenarios are dynamically loaded into the cbScenario ComboBox based on the selected Mode (Education/Health) via the updateScenarios method. ButtonGroup ensures mutual exclusivity for RadioButton selections.
3. Data Processing & Scoring:
Steps 3 & 4 (Plan & Collect): Data is populated into a DefaultTableModel based on the chosen scenario through loadStep3Data and loadStep4Data methods.
Scoring Algorithm: The calculateScore method implements the "Higher is better" and "Lower is better" formulas defined in the ISO standard. Results are rounded to the nearest 0.5 increment using the Math.round(s * 2) / 2.0 logic as required.
4. Analysis & Results (Step 5):
Weighted Average: The application calculates the weighted average of metric scores. This value (on a 1-5 scale) is converted to a 0-100 percentage for visualization on a JProgressBar.
Gap Analysis: It calculates the "Gap Value" (difference from the 5.0 target) and provides dynamic qualitative feedback (Excellent, Good, Needs Improvement, or Poor) based on score thresholds.
5. Technical Specifications:
Language: Java (SE 17 or higher)
Libraries: Java Swing & AWT
Layout Managers: BorderLayout (Main container), GridLayout, and FlowLayout (Step-specific panels).
