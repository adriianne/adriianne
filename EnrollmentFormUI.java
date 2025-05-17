

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class EnrollmentFormUI {
    private static final Color PRIMARY_COLOR = new Color(0, 102, 204);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static HashMap<String, Student> studentDatabase = new HashMap<>();

    // Field declarations
    private JTextField idNumberField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JComboBox<String> yearLevelCombo;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JFrame frame;

    public static void openEnrollmentForm() {
        new EnrollmentFormUI().initializeForm();
    }

    private void initializeForm() {
        frame = new JFrame("University of Cebu - Enrollment");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(900, 600));
        frame.setLayout(new BorderLayout());

        // Initialize all components
        initializeComponents();
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initializeComponents() {
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);

        // Left panel with university logo and details
        JPanel leftPanel = createLeftPanel();
        mainPanel.add(leftPanel, BorderLayout.WEST);

        // Right panel with enrollment form
        JPanel rightPanel = createRightPanel();
        mainPanel.add(rightPanel, BorderLayout.CENTER);
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(PRIMARY_COLOR);
        leftPanel.setPreferredSize(new Dimension(400, 600));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel universityLabel = new JLabel("UNIVERSITY OF CEBU", JLabel.CENTER);
        universityLabel.setFont(TITLE_FONT);
        universityLabel.setForeground(Color.WHITE);
        gbc.gridy = 0;
        leftPanel.add(universityLabel, gbc);

        JLabel mottoLabel = new JLabel("Excellence • Integrity • Service", JLabel.CENTER);
        mottoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        mottoLabel.setForeground(new Color(200, 220, 255));
        gbc.gridy = 1;
        leftPanel.add(mottoLabel, gbc);

        return leftPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.gridwidth = GridBagConstraints.REMAINDER;
        gbcRight.fill = GridBagConstraints.HORIZONTAL;
        gbcRight.insets = new Insets(10, 0, 20, 0);

        JLabel enrollTitle = new JLabel("Student Enrollment", JLabel.CENTER);
        enrollTitle.setFont(TITLE_FONT);
        rightPanel.add(enrollTitle, gbcRight);

        JLabel subTitle = new JLabel("Please fill in the enrollment form", JLabel.CENTER);
        subTitle.setFont(SUBTITLE_FONT);
        subTitle.setForeground(Color.GRAY);
        rightPanel.add(subTitle, gbcRight);

        // Form fields panel
        JPanel formFieldsPanel = createFormFieldsPanel();
        rightPanel.add(formFieldsPanel, gbcRight);

        // Submit button
        JButton submitButton = createSubmitButton();
        rightPanel.add(submitButton, gbcRight);

        // Login link
        JPanel loginPanel = createLoginPanel();
        rightPanel.add(loginPanel, gbcRight);

        return rightPanel;
    }

    private JPanel createFormFieldsPanel() {
        JPanel formFieldsPanel = new JPanel(new GridBagLayout());
        formFieldsPanel.setBackground(Color.WHITE);
        GridBagConstraints gbcFields = new GridBagConstraints();
        gbcFields.insets = new Insets(5, 0, 5, 10);
        gbcFields.anchor = GridBagConstraints.WEST;
        gbcFields.fill = GridBagConstraints.HORIZONTAL;

        // Initialize all fields
        idNumberField = new JTextField(20);
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        emailField = new JTextField(20);
        yearLevelCombo = new JComboBox<>(new String[]{"1st Year", "2nd Year", "3rd Year", "4th Year"});
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        // Style all fields
        styleTextField(idNumberField);
        styleTextField(firstNameField);
        styleTextField(lastNameField);
        styleTextField(emailField);
        styleTextField(usernameField);
        styleTextField(passwordField);
        styleComboBox(yearLevelCombo);

        // Add fields to form
        addFormField(formFieldsPanel, gbcFields, "ID Number:", idNumberField, 0);
        addFormField(formFieldsPanel, gbcFields, "First Name:", firstNameField, 1);
        addFormField(formFieldsPanel, gbcFields, "Last Name:", lastNameField, 2);
        addFormField(formFieldsPanel, gbcFields, "Email:", emailField, 3);
        addFormField(formFieldsPanel, gbcFields, "Year Level:", yearLevelCombo, 4);
        addFormField(formFieldsPanel, gbcFields, "Username:", usernameField, 5);
        addFormField(formFieldsPanel, gbcFields, "Password:", passwordField, 6);

        return formFieldsPanel;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String label, JComponent field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(LABEL_FONT);
        panel.add(jLabel, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private JButton createSubmitButton() {
        JButton submitButton = new JButton("SUBMIT ENROLLMENT");
        submitButton.setFont(BUTTON_FONT);
        submitButton.setBackground(PRIMARY_COLOR);
        submitButton.setForeground(Color.WHITE);
        submitButton.setPreferredSize(new Dimension(0, 45));
        submitButton.setFocusPainted(false);

        submitButton.addActionListener(e -> submitEnrollment());
        
        return submitButton;
    }

    private void submitEnrollment() {
        String idNumber = idNumberField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String yearLevel = (String) yearLevelCombo.getSelectedItem();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Validate all fields
        if (idNumber.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || 
            email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, 
                "Please fill all required fields!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create and store new student
        Student newStudent = new Student(idNumber, firstName, lastName, 
            email, yearLevel, username, password);
        studentDatabase.put(username, newStudent);
        LoginUI.addAccount(username, password, "Student");

        // Show success message
        JOptionPane.showMessageDialog(frame,
            "Enrollment successful!\n" +
            "ID: " + idNumber + "\n" +
            "Username: " + username,
            "Success",
            JOptionPane.INFORMATION_MESSAGE);

        frame.dispose();
        LoginUI.createLoginUI();
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        loginPanel.setBackground(Color.WHITE);

        JLabel loginText = new JLabel("Already have an account?");
        loginText.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        loginPanel.add(loginText);

        JLabel loginLink = new JLabel("Login");
        loginLink.setFont(new Font("Segoe UI", Font.BOLD, 12));
        loginLink.setForeground(PRIMARY_COLOR);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginPanel.add(loginLink);

        loginLink.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // Ensure default accounts exist
                LoginUI.resetDefaultAccounts();
                frame.dispose();
                LoginUI.createLoginUI();
            }
        });

        return loginPanel;
    }

    private static void styleTextField(JComponent field) {
        field.setFont(SUBTITLE_FONT);
        field.setPreferredSize(new Dimension(200, 30));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }

    private static void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(SUBTITLE_FONT);
        comboBox.setPreferredSize(new Dimension(200, 30));
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }

    public static HashMap<String, Student> getStudentDatabase() {
        return studentDatabase;
    }

    static class Student {
        private final String idNumber;
        private final String firstName;
        private final String lastName;
        private final String email;
        private final String yearLevel;
        private final String username;
        private final String password;

        public Student(String idNumber, String firstName, String lastName, 
                      String email, String yearLevel, String username, String password) {
            this.idNumber = idNumber;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.yearLevel = yearLevel;
            this.username = username;
            this.password = password;
        }

        public String getFullName() { return firstName + " " + lastName; }
        public String getEmail() { return email; }
        public String getYearLevel() { return yearLevel; }
        public String getUsername() { return username; }
        public String getIdNumber() { return idNumber; }
    }
}