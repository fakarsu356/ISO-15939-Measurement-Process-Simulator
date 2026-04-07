Furkan AKARSU
202328001










# SOFTWARE REQUIREMENTS SPECIFICATION (SRS)

## ISO 15939 Measurement Process Simulator

---

## 1. Introduction

### 1.1 Purpose

This document describes the requirements and design of a Java Swing application that simulates the ISO/IEC 15939 software measurement process.

### 1.2 Scope

The system allows users to define quality dimensions, view metrics, collect measurement data, and analyze results through a step-based interface.

---

## 2. Overall Description

### 2.1 System Overview

The application is a desktop-based Java Swing program that follows a step-by-step workflow:

1. Profile
2. Define Quality
3. Plan Measurement
4. Collect Data
5. Analyse

---

### 2.2 User Characteristics

* Basic computer users
* No technical expertise required
* Interacts via graphical user interface

---

## 3. Functional Requirements

### 3.1 Profile Step

* User must enter:

  * Username
  * School
  * Session Name
* System must validate input fields
* Empty fields must trigger warning messages

---

### 3.2 Define Quality Step

* User selects:

  * Quality Type (Product / Process)
  * Mode (Education / Health)
  * Scenario
* Only one option must be selectable at a time

---

### 3.3 Plan Measurement Step

* System displays predefined:

  * Dimensions
  * Metrics
* Data shown in table format
* This step is read-only

---

### 3.4 Collect Data Step

* System displays metric values
* Score is automatically calculated

Score formulas:

Higher is better:
score = 1 + (value − min) / (max − min) × 4

Lower is better:
score = 5 − (value − min) / (max − min) × 4

* Scores must be between 1.0 and 5.0

---

### 3.5 Analyse Step

* System calculates dimension scores:

dimensionScore = Σ(metricScore × coefficient) / Σ(coefficient)

* Displays:

  * Dimension scores
  * Lowest scoring dimension
  * Gap value (5 − score)

---

## 4. Non-Functional Requirements

* Application must be implemented in Java SE 17+
* GUI must be developed using Java Swing
* No external libraries allowed
* Application must be runnable via command line
* Code must follow OOP principles
* Interface must be user-friendly

---

## 5. System Design

### 5.1 Architecture

The system follows MVC architecture:

* Model → Data structures (Metric, Dimension, Scenario)
* View → Swing GUI components
* Controller → Handles user interactions

---

### 5.2 Application Structure

* Step1Panel → Profile
* Step2Panel → Define
* Step3Panel → Plan
* Step4Panel → Collect
* Step5Panel → Analyse

Navigation is implemented using CardLayout.

---

### 5.3 Class Design

Metric Class:

* name
* coefficient
* direction
* min, max
* unit
* value
* score

Dimension Class:

* name
* coefficient
* list of metrics

Scenario Class:

* name
* list of dimensions

---

### 5.4 Data Structures

* ArrayList → store metrics and dimensions
* HashMap → manage scenarios

---

## 6. Constraints

* Only standard Java libraries can be used
* No IDE dependency allowed
* Data is hard-coded (no database required)

---

## 7. Conclusion

This project simulates the ISO 15939 measurement process using a structured, object-oriented, and GUI-based approach. It ensures clear separation between data, logic, and interface while providing a complete measurement workflow.

---


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
