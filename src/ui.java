import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ui extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JLabel stepIndicatorLabel;
    private int currentStep = 1;
    private final String[] stepNames = {"Profile", "Define", "Plan", "Collect", "Analyse"};

    private JTextField txtUser, txtSchool, txtSession;
    private JRadioButton rbProduct, rbProcess;
    private JComboBox<String> cbMode, cbScenario;
    private DefaultTableModel planModel, collectModel;
    private JProgressBar resultBar;
    private JLabel lblQualityLevel, lblGapInfo, lblDimTitle;

    private ScenarioRepository repository;
    private Scenario selectedScenario;
    private ArrayList<Double> currentScores;

    public ui() {
        repository = new ScenarioRepository();
        currentScores = new ArrayList<>();

        setTitle("ISO 15939 Measurement Process Simulator");
        setSize(1100, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(new Color(245, 245, 245));
        stepIndicatorLabel = new JLabel();
        stepIndicatorLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        updateStepIndicator();
        topPanel.add(stepIndicatorLabel);
        add(topPanel, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        setupSteps();
        add(cardPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnBack = new JButton("Back");
        JButton btnNext = new JButton("Next");
        btnBack.setEnabled(false);

        btnNext.addActionListener(e -> handleNext(btnNext, btnBack));
        btnBack.addActionListener(e -> handleBack(btnNext, btnBack));

        bottomPanel.add(btnBack);
        bottomPanel.add(btnNext);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void updateStepIndicator() {
        StringBuilder sb = new StringBuilder("<html>");
        for (int i = 0; i < stepNames.length; i++) {
            int stepNum = i + 1;
            if (stepNum < currentStep)
                sb.append("<font color='green'>✓ ").append(stepNames[i]).append("</font>");
            else if (stepNum == currentStep)
                sb.append("<b><font color='blue'>").append(stepNames[i]).append("</font></b>");
            else
                sb.append("<font color='gray'>").append(stepNames[i]).append("</font>");
            if (i < stepNames.length - 1) sb.append("  →  ");
        }
        sb.append("</html>");
        stepIndicatorLabel.setText(sb.toString());
    }

    private void setupSteps() {
       
        JPanel p1 = new JPanel(new GridLayout(4, 2, 10, 40));
        p1.setBorder(BorderFactory.createEmptyBorder(100, 250, 100, 250));
        txtUser = new JTextField();
        txtSchool = new JTextField();
        txtSession = new JTextField();
        p1.add(new JLabel("Username:")); p1.add(txtUser);
        p1.add(new JLabel("School:"));   p1.add(txtSchool);
        p1.add(new JLabel("Session Name:")); p1.add(txtSession);
        cardPanel.add(p1, "Step1");

    
        JPanel p2 = new JPanel(new GridLayout(6, 1, 10, 10));
        p2.setBorder(BorderFactory.createEmptyBorder(50, 250, 50, 250));
        rbProduct = new JRadioButton("Product Quality");
        rbProcess = new JRadioButton("Process Quality");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbProduct);
        bg.add(rbProcess);
        cbMode = new JComboBox<>(new String[]{"Education", "Health"});
        cbScenario = new JComboBox<>();
        cbMode.addActionListener(e -> updateScenarios());
        updateScenarios();

        p2.add(new JLabel("1. Quality Type (Select One):"));
        JPanel rp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rp.add(rbProduct);
        rp.add(rbProcess);
        p2.add(rp);
        p2.add(new JLabel("2. Mode:"));    p2.add(cbMode);
        p2.add(new JLabel("3. Scenario:")); p2.add(cbScenario);
        cardPanel.add(p2, "Step2");

        
        JPanel p3 = new JPanel(new BorderLayout());
        lblDimTitle = new JLabel("Dimension Details", JLabel.CENTER);
        lblDimTitle.setFont(new Font("Arial", Font.BOLD, 14));
        planModel = new DefaultTableModel(new String[]{"Metric", "Coeff", "Direction", "Range", "Unit"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        p3.add(lblDimTitle, BorderLayout.NORTH);
        p3.add(new JScrollPane(new JTable(planModel)), BorderLayout.CENTER);
        cardPanel.add(p3, "Step3");

       
        JPanel p4 = new JPanel(new BorderLayout());
        JLabel lblCollectTitle = new JLabel("Enter or Review Collected Values", JLabel.CENTER);
        lblCollectTitle.setFont(new Font("Arial", Font.BOLD, 14));
        collectModel = new DefaultTableModel(new String[]{"Metric", "Direction", "Collected Value", "Score (1-5)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Sadece "Collected Value" sutunu duzenlenebilir
            }
        };
        p4.add(lblCollectTitle, BorderLayout.NORTH);
        p4.add(new JScrollPane(new JTable(collectModel)), BorderLayout.CENTER);
        cardPanel.add(p4, "Step4");

      
        JPanel p5 = new JPanel(new GridLayout(5, 1, 10, 20));
        p5.setBorder(BorderFactory.createEmptyBorder(60, 200, 60, 200));
        resultBar = new JProgressBar(0, 100);
        resultBar.setStringPainted(true);
        resultBar.setFont(new Font("Arial", Font.BOLD, 14));
        lblQualityLevel = new JLabel("", JLabel.CENTER);
        lblQualityLevel.setFont(new Font("Arial", Font.BOLD, 16));
        lblGapInfo = new JLabel("", JLabel.CENTER);
        lblGapInfo.setFont(new Font("Arial", Font.PLAIN, 13));
        p5.add(new JLabel("Final Weighted Average Result:", JLabel.CENTER));
        p5.add(resultBar);
        p5.add(lblQualityLevel);
        p5.add(new JSeparator());
        p5.add(lblGapInfo);
        cardPanel.add(p5, "Step5");
    }

    private void updateScenarios() {
        if (cbScenario == null || repository == null) return;
        cbScenario.removeAllItems();
        Mode m = cbMode.getSelectedIndex() == 0 ? Mode.EDUCATION : Mode.HEALTH;
        ArrayList<Scenario> list = repository.getScenariosByMode(m);
        for (Scenario s : list) {
            cbScenario.addItem(s.getName());
        }
    }

    private void handleNext(JButton next, JButton back) {
        if (currentStep == 1) {
            if (txtUser.getText().trim().isEmpty() || txtSchool.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill all Profile fields!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } else if (currentStep == 2) {
            if (!rbProduct.isSelected() && !rbProcess.isSelected()) {
                JOptionPane.showMessageDialog(this, "Select a Quality Type!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            loadStep3Data();
        } else if (currentStep == 3) {
            loadStep4Data();
        } else if (currentStep == 4) {
            loadStep5Data();
            next.setText("Finish");
        } else if (currentStep == 5) {
            System.exit(0);
            return;
        }

        currentStep++;
        cardLayout.show(cardPanel, "Step" + currentStep);
        back.setEnabled(true);
        updateStepIndicator();
    }

    private void handleBack(JButton next, JButton back) {
        currentStep--;
        cardLayout.show(cardPanel, "Step" + currentStep);
        next.setText("Next");
        if (currentStep == 1) back.setEnabled(false);
        updateStepIndicator();
    }

   
    private void loadStep3Data() {
        planModel.setRowCount(0);
        String scenarioName = (String) cbScenario.getSelectedItem();
        selectedScenario = repository.getScenarioByName(scenarioName);

        if (selectedScenario != null) {
            lblDimTitle.setText("Plan for: " + selectedScenario.getName());
            for (Dimension dim : selectedScenario.getDimensions()) {
                for (Metric m : dim.getMetrics()) {
                    String direction = m.getDirection() ? "Higher ↑" : "Lower ↓";
                    String range = m.getminRange() + " - " + m.getmaxRange();
                    planModel.addRow(new Object[]{
                        m.getName(), m.getCoefficient(), direction, range, m.getUnit()
                    });
                }
            }
        }
    }

   
    private void loadStep4Data() {
        collectModel.setRowCount(0);
        currentScores.clear();

        if (selectedScenario != null) {
            for (Dimension dim : selectedScenario.getDimensions()) {
                for (Metric m : dim.getMetrics()) {
                    double collectedValue = m.getminRange() + ((m.getmaxRange() - m.getminRange()) * 0.80);
                    double score = calculateScore(collectedValue, m.getminRange(), m.getmaxRange(), m.getDirection());
                    currentScores.add(score);

                    String direction = m.getDirection() ? "Higher ↑" : "Lower ↓";
                    collectModel.addRow(new Object[]{
                        m.getName(), direction,
                        String.format("%.2f", collectedValue),
                        String.format("%.2f", score)
                    });
                }
            }
        }
    }

    private void loadStep5Data() {
        if (selectedScenario == null || currentScores.isEmpty()) {
            resultBar.setValue(0);
            lblQualityLevel.setText("No data available.");
            lblGapInfo.setText("");
            return;
        }

        // Agirlikli ortalama hesapla
        double totalWeightedScore = 0.0;
        double totalWeight = 0.0;
        int scoreIndex = 0;

        for (Dimension dim : selectedScenario.getDimensions()) {
            double dimCoeff = dim.getCoefficient(); 
            double dimWeightedScore = 0.0;
            double metricTotalCoeff = 0.0;

            for (Metric m : dim.getMetrics()) {
                if (scoreIndex < currentScores.size()) {
                    double metricScore = currentScores.get(scoreIndex);
                    dimWeightedScore += metricScore * m.getCoefficient();
                    metricTotalCoeff += m.getCoefficient();
                    scoreIndex++;
                }
            }

            double dimAvg = (metricTotalCoeff > 0) ? (dimWeightedScore / metricTotalCoeff) : 0;
            totalWeightedScore += dimAvg * dimCoeff;
            totalWeight += dimCoeff;
        }

        double finalScore = (totalWeight > 0) ? (totalWeightedScore / totalWeight) : 0;

        
        int percentage = (int) Math.round(((finalScore - 1.0) / 4.0) * 100.0);
        percentage = Math.max(0, Math.min(100, percentage));

        resultBar.setValue(percentage);
        resultBar.setString(percentage + "%");

        if (percentage >= 75) resultBar.setForeground(new Color(34, 139, 34));
        else if (percentage >= 50) resultBar.setForeground(new Color(255, 165, 0));
        else resultBar.setForeground(new Color(200, 0, 0));

        String level;
        if      (percentage >= 90) level = "Excellent ★★★★★";
        else if (percentage >= 75) level = "Good ★★★★";
        else if (percentage >= 60) level = "Acceptable ★★★";
        else if (percentage >= 40) level = "Poor ★★";
        else                       level = "Very Poor ★";

        lblQualityLevel.setText("Quality Level: " + level);
        lblQualityLevel.setForeground(percentage >= 60 ? new Color(0, 100, 0) : Color.RED);

        int gap = 100 - percentage;
        lblGapInfo.setText("Gap to Maximum: " + gap + "% — Raw Score: " + String.format("%.2f", finalScore) + " / 5.00");
    }

    private double calculateScore(double value, double min, double max, boolean higherIsBetter) {
        if (max == min) return 3.0; // Aralik sifirsa orta skor
        double normalized;
        if (higherIsBetter) {
            normalized = (value - min) / (max - min);
        } else {
            normalized = (max - value) / (max - min);
        }
        return 1.0 + normalized * 4.0;
    }
}
