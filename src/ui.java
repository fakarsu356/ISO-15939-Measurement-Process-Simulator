import java.awt.*;
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

    public ui() {
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
            if (stepNum < currentStep) sb.append("<font color='green'>✓ ").append(stepNames[i]).append("</font>");
            else if (stepNum == currentStep) sb.append("<b><font color='blue'>").append(stepNames[i]).append("</font></b>");
            else sb.append("<font color='gray'>").append(stepNames[i]).append("</font>");
            if (i < stepNames.length - 1) sb.append("  →  ");
        }
        sb.append("</html>");
        stepIndicatorLabel.setText(sb.toString());
    }

    private void setupSteps() {
        JPanel p1 = new JPanel(new GridLayout(4, 2, 10, 40));
        p1.setBorder(BorderFactory.createEmptyBorder(100, 250, 100, 250));
        txtUser = new JTextField(); txtSchool = new JTextField(); txtSession = new JTextField();
        p1.add(new JLabel("Username:")); p1.add(txtUser);
        p1.add(new JLabel("School:")); p1.add(txtSchool);
        p1.add(new JLabel("Session Name:")); p1.add(txtSession);
        cardPanel.add(p1, "Step1");

        JPanel p2 = new JPanel(new GridLayout(6, 1, 10, 10));
        p2.setBorder(BorderFactory.createEmptyBorder(50, 250, 50, 250));
        rbProduct = new JRadioButton("Product Quality"); rbProcess = new JRadioButton("Process Quality");
        ButtonGroup bg = new ButtonGroup(); bg.add(rbProduct); bg.add(rbProcess);
        cbMode = new JComboBox<>(new String[]{"Education", "Health"});
        cbScenario = new JComboBox<>();
        
        cbMode.addActionListener(e -> updateScenarios());
        updateScenarios();

        p2.add(new JLabel("1. Quality Type (Select One):"));
        JPanel rp = new JPanel(new FlowLayout(FlowLayout.LEFT)); rp.add(rbProduct); rp.add(rbProcess);
        p2.add(rp);
        p2.add(new JLabel("2. Mode:")); p2.add(cbMode);
        p2.add(new JLabel("3. Scenario (At least 2 required):")); p2.add(cbScenario);
        cardPanel.add(p2, "Step2");

        JPanel p3 = new JPanel(new BorderLayout());
        lblDimTitle = new JLabel("Dimension Details", JLabel.CENTER);
        planModel = new DefaultTableModel(new String[]{"Metric", "Coeff", "Direction", "Range", "Unit"}, 0);
        p3.add(lblDimTitle, BorderLayout.NORTH);
        p3.add(new JScrollPane(new JTable(planModel)), BorderLayout.CENTER);
        cardPanel.add(p3, "Step3");

        JPanel p4 = new JPanel(new BorderLayout());
        collectModel = new DefaultTableModel(new String[]{"Metric", "Direction", "Value", "Score (1-5)"}, 0);
        p4.add(new JScrollPane(new JTable(collectModel)), BorderLayout.CENTER);
        cardPanel.add(p4, "Step4");

        JPanel p5 = new JPanel(new GridLayout(5, 1, 10, 20));
        p5.setBorder(BorderFactory.createEmptyBorder(60, 200, 60, 200));
        resultBar = new JProgressBar(0, 100); resultBar.setStringPainted(true);
        lblQualityLevel = new JLabel("", JLabel.CENTER);
        lblGapInfo = new JLabel("", JLabel.CENTER);
        p5.add(new JLabel("Final Weighted Average Result:", JLabel.CENTER));
        p5.add(resultBar); p5.add(lblQualityLevel);
        p5.add(new JSeparator()); p5.add(lblGapInfo);
        cardPanel.add(p5, "Step5");
    }

    private void updateScenarios() {
        cbScenario.removeAllItems();
        if ("Education".equals(cbMode.getSelectedItem())) {
            cbScenario.addItem("Scenario C - High Performance");
            cbScenario.addItem("Scenario D - Low Performance");
        } else {
            cbScenario.addItem("Health Scenario A");
            cbScenario.addItem("Health Scenario B");
        }
    }

    private void handleNext(JButton next, JButton back) {
        if (currentStep == 1) {
            if (txtUser.getText().isEmpty() || txtSchool.getText().isEmpty()) {
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
        String scenario = (String) cbScenario.getSelectedItem();
        lblDimTitle.setText("Plan for: " + scenario);
        
        if (scenario != null && scenario.contains("Scenario C")) {
            planModel.addRow(new Object[]{"SUS Score", "50", "Higher ↑", "0-100", "points"});
            planModel.addRow(new Object[]{"Onboarding", "50", "Lower ↓", "0-60", "min"});
        } else {
            planModel.addRow(new Object[]{"Video Start", "40", "Lower ↓", "0-15", "sec"});
            planModel.addRow(new Object[]{"WCAG Comp.", "60", "Higher ↑", "0-100", "%"});
        }
    }

    private void loadStep4Data() {
        collectModel.setRowCount(0);
        String scenario = (String) cbScenario.getSelectedItem();
        double v1, v2;
        boolean up1, up2;

        if (scenario != null && scenario.contains("Scenario C")) {
            v1 = 89.0; v2 = 5.0; up1 = true; up2 = false;
        } else {
            v1 = 12.0; v2 = 30.0; up1 = false; up2 = true;
        }

        double s1 = calculateScore(v1, 0, (up1?100:15), up1);
        double s2 = calculateScore(v2, 0, (up2?100:60), up2);

        collectModel.addRow(new Object[]{"Metric 1", up1?"Higher ↑":"Lower ↓", v1, s1});
        collectModel.addRow(new Object[]{"Metric 2", up2?"Higher ↑":"Lower ↓", v2, s2});
    }

    private void loadStep5Data() {
        double s1 = (double) collectModel.getValueAt(0, 3);
        double s2 = (double) collectModel.getValueAt(1, 3);
        double avg = (s1 + s2) / 2.0;

        int percent = (int) ((avg / 5.0) * 100);
        resultBar.setValue(percent);
        resultBar.setString("Score: " + avg + " / 5.0");
        
        if (avg >= 4.0) resultBar.setForeground(Color.GREEN);
        else if (avg >= 2.5) resultBar.setForeground(Color.ORANGE);
        else resultBar.setForeground(Color.RED);

        double gap = 5.0 - avg;
        String level = (avg >= 4.5) ? "Excellent" : (avg >= 3.5) ? "Good" : (avg >= 2.5) ? "Needs Improvement" : "Poor";
        lblQualityLevel.setText("Quality Level: " + level);
        lblGapInfo.setText("<html><center>Gap Analysis Result: " + gap + "<br>Status: " + level + "</center></html>");
    }

    private double calculateScore(double val, double min, double max, boolean up) {
        double s = up ? (1 + ((val - min) / (max - min)) * 4) : (5 - ((val - min) / (max - min)) * 4);
        s = Math.max(1.0, Math.min(5.0, s));
        return Math.round(s * 2) / 2.0;
    }

}