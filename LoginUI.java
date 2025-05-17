 import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

 class LoginUI {
    // In-memory account storage (static for global access)
    private static final HashMap<String, Account> accountStorage = new HashMap<>();
    
      static {
        resetDefaultAccounts();
    }

    public static void resetDefaultAccounts() {
        accountStorage.clear();
        // ALWAYS maintain these default accounts
        accountStorage.put("teacher", new Account("teacher", "5678", "Teacher"));
        accountStorage.put("admin", new Account("admin", "admin123", "Admin"));
    }

    public static void createLoginUI() {
        
          System.out.println("=== Available Accounts ===");
    accountStorage.forEach((username, account) -> {
        System.out.println(username + " - " + account.getRole());
    });
        
        JFrame frame = new JFrame("University of Cebu - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(900, 600));
        frame.setLayout(new BorderLayout());

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);

        // Left panel with university logo and details
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(new Color(0, 102, 204));
        leftPanel.setPreferredSize(new Dimension(400, 600));
        mainPanel.add(leftPanel, BorderLayout.WEST);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel universityLabel = new JLabel("UNIVERSITY OF CEBU", JLabel.CENTER);
        universityLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        universityLabel.setForeground(Color.WHITE);
        gbc.gridy = 0;
        leftPanel.add(universityLabel, gbc);

        JLabel mottoLabel = new JLabel("Excellence • Integrity • Service", JLabel.CENTER);
        mottoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        mottoLabel.setForeground(new Color(200, 220, 255));
        gbc.gridy = 1;
        leftPanel.add(mottoLabel, gbc);

        // Right panel with login form
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.gridwidth = GridBagConstraints.REMAINDER;
        gbcRight.fill = GridBagConstraints.HORIZONTAL;
        gbcRight.insets = new Insets(10, 0, 20, 0);

        JLabel loginTitle = new JLabel("Welcome Back!", JLabel.CENTER);
        loginTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        rightPanel.add(loginTitle, gbcRight);

        JLabel subTitle = new JLabel("Please login to your account", JLabel.CENTER);
        subTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subTitle.setForeground(Color.GRAY);
        rightPanel.add(subTitle, gbcRight);

        JTextField userText = createLabeledTextField("Username", rightPanel, gbcRight);
        JPasswordField passText = createLabeledPasswordField("Password", rightPanel, gbcRight);

        gbcRight.insets = new Insets(30, 0, 10, 0);
        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(0, 45));
        loginButton.setFocusPainted(false);
        rightPanel.add(loginButton, gbcRight);

        JLabel forgotPassLink = new JLabel("Forgot Password?");
        forgotPassLink.setFont(new Font("Segoe UI", Font.BOLD, 12));
        forgotPassLink.setForeground(new Color(0, 102, 204));
        forgotPassLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPassLink.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(frame, "Forgot Password feature coming soon!");
            }
        });
        rightPanel.add(forgotPassLink, gbcRight);

        JPanel signupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        signupPanel.setBackground(Color.WHITE);
        rightPanel.add(signupPanel, gbcRight);

        JLabel signupText = new JLabel("Don't have an account?");
        signupText.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        signupPanel.add(signupText);

        JLabel signupLink = new JLabel("Sign Up");
        signupLink.setFont(new Font("Segoe UI", Font.BOLD, 12));
        signupLink.setForeground(new Color(0, 102, 204));
        signupLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupPanel.add(signupLink);

        signupLink.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent e) {
        EnrollmentFormUI.openEnrollmentForm();
        frame.dispose();
    }
});

        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passText.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (accountStorage.containsKey(username)) {
                Account account = accountStorage.get(username);
                if (account.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(frame, "Login successful! Welcome, " + account.getRole() + "!");
                    frame.dispose();
                    navigateToDashboard(account);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Account does not exist!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JTextField createLabeledTextField(String label, JPanel panel, GridBagConstraints gbc) {
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(fieldLabel, gbc);

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(0, 40));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panel.add(textField, gbc);

        return textField;
    }

    private static JPasswordField createLabeledPasswordField(String label, JPanel panel, GridBagConstraints gbc) {
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(fieldLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(0, 40));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panel.add(passwordField, gbc);

        return passwordField;
    }

    public static void addAccount(String username, String password, String role) {
        accountStorage.put(username, new Account(username, password, role));
    }

    public static void navigateToDashboard(Account account) {
    String role = account.getRole();
    if (role.equalsIgnoreCase("Student")) {
        StudentDashboard.openStudentDashboard(account.getUsername());
    } else if (role.equalsIgnoreCase("Teacher")) {
        TeacherDashboard.openTeacherDashboard(account.getUsername());
    } else if (role.equalsIgnoreCase("Admin")) {
        AdminDashboard.openAdminDashboard(account.getUsername());
    } else {
        JOptionPane.showMessageDialog(null, "Dashboard for this role is not yet implemented!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}
    public static HashMap<String, Account> getAccountStorage() {
        return accountStorage;
    }

   public static void main(String[] args) {
    addAccount("student", "1234", "Student");
    addAccount("teacher", "5678", "Teacher");
    addAccount("admin", "admin123", "Admin");  // Add this line
    createLoginUI();
}
}

// Account class to represent user accounts
class Account {
    private final String username;
    private final String password;
    private final String role;

    public Account(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}