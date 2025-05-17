import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.HashMap;

public class AdminDashboard {
    // Color scheme
    private static final Color PRIMARY_COLOR = new Color(0, 51, 102);  // Dark blue
    private static final Color SIDEBAR_COLOR = new Color(240, 245, 250); // Light blue-gray
    private static final Color ACTIVE_MENU_COLOR = new Color(200, 230, 255); // Active menu highlight
    private static final Font MENU_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public static void openAdminDashboard(String username) {
        JFrame frame = new JFrame("Admin Dashboard - " + username);
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = createHeaderPanel(username);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Sidebar and Main Content
        JPanel sidebarPanel = createSidebarPanel();
        JPanel mainContentPanel = new JPanel(new CardLayout());
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        frame.add(sidebarPanel, BorderLayout.WEST);
        frame.add(mainContentPanel, BorderLayout.CENTER);

        // Menu Buttons and Content Panels
        String[] menuItems = {"User Management", "Reports", "Profile"};
        for (String item : menuItems) {
            JButton menuButton = createMenuButton(item);
            JPanel contentPanel = createContentPanel(item, frame);
            mainContentPanel.add(contentPanel, item);

            menuButton.addActionListener(e -> {
                setActiveMenuButton(sidebarPanel, menuButton);
                ((CardLayout) mainContentPanel.getLayout()).show(mainContentPanel, item);
            });
            sidebarPanel.add(menuButton);
        }

        // Footer Panel
        JPanel footerPanel = createFooterPanel(frame);
        frame.add(footerPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // Method to set the active menu button appearance
    private static void setActiveMenuButton(JPanel sidebarPanel, JButton activeButton) {
        // Reset all buttons to default appearance
        for (Component comp : sidebarPanel.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.setBackground(SIDEBAR_COLOR);
                button.setForeground(Color.BLACK);
            }
        }
        // Set the active button appearance
        activeButton.setBackground(ACTIVE_MENU_COLOR);
        activeButton.setForeground(Color.BLACK);
    }

    // ========== PANEL CREATION METHODS ========== //
    private static JPanel createHeaderPanel(String username) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel welcomeLabel = new JLabel("Welcome, " + username, JLabel.LEFT);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        headerPanel.add(welcomeLabel, BorderLayout.WEST);

        JLabel roleLabel = new JLabel("Administrator", JLabel.RIGHT);
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roleLabel.setForeground(Color.WHITE);
        headerPanel.add(roleLabel, BorderLayout.EAST);

        return headerPanel;
    }

    private static JPanel createSidebarPanel() {
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setPreferredSize(new Dimension(250, 0));
        sidebarPanel.setBackground(SIDEBAR_COLOR);
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        JLabel menuLabel = new JLabel("ADMIN MENU");
        menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sidebarPanel.add(menuLabel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        return sidebarPanel;
    }

    private static JButton createMenuButton(String item) {
        JButton menuButton = new JButton(item);
        menuButton.setFont(MENU_FONT);
        menuButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        menuButton.setBackground(SIDEBAR_COLOR);
        menuButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 10));
        menuButton.setHorizontalAlignment(SwingConstants.LEFT);
        menuButton.setToolTipText("Go to " + item);

        menuButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                menuButton.setBackground(ACTIVE_MENU_COLOR.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (!menuButton.getBackground().equals(ACTIVE_MENU_COLOR)) {
                    menuButton.setBackground(SIDEBAR_COLOR);
                }
            }
        });

        return menuButton;
    }

    private static JPanel createContentPanel(String menuItem, JFrame parentFrame) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

      if (menuItem.equals("User Management")) {
    panel.add(createUserManagementPanel(parentFrame), BorderLayout.CENTER);
} else if (menuItem.equals("Reports")) {
    panel.add(createReportsPanel(), BorderLayout.CENTER);
} else if (menuItem.equals("Profile")) {
    panel.add(createEditableProfilePanel(parentFrame), BorderLayout.CENTER);
}

        return panel;
    }

 private static JPanel createUserManagementPanel(JFrame parentFrame) {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Title
    JLabel titleLabel = new JLabel("User Management", JLabel.CENTER);
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
    titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    panel.add(titleLabel, BorderLayout.NORTH);

    // Create sample user data
    List<User> users = new ArrayList<>();
    users.add(new User("teacher1", "teacher", "Active", "2023-05-15 14:30"));
    users.add(new User("student1", "student", "Active", "2023-05-16 09:15"));
    users.add(new User("admin", "admin", "Active", "2023-05-16 10:45"));
    
     HashMap<String, EnrollmentFormUI.Student> students = EnrollmentFormUI.getStudentDatabase();
    for (EnrollmentFormUI.Student student : students.values()) {
        users.add(new User(student.getUsername(), "Student", "Active", "Never"));
    }

    // Create table model without the first column
    UserTableModel model = new UserTableModel(users);
    JTable table = new JTable(model) {
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component c = super.prepareRenderer(renderer, row, column);
            if (!isRowSelected(row)) {
                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 240, 240));
            }
            return c;
        }
    };
    
    // Configure table appearance
    table.setRowHeight(40);
    table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
    table.setShowGrid(false);
    
    // Center-align all columns except actions
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    for (int i = 0; i < table.getColumnCount(); i++) {
        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
    
    // Set custom renderer and editor for Actions column
    table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
    table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(table, users, model));

    JScrollPane scrollPane = new JScrollPane(table);
    panel.add(scrollPane, BorderLayout.CENTER);

    // Action buttons panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
    buttonPanel.setBackground(Color.WHITE);
    
    JButton addButton = createStyledButton("Add User", PRIMARY_COLOR);
    addButton.addActionListener(e -> showAddUserDialog(parentFrame, users, model));

    buttonPanel.add(addButton);
    panel.add(buttonPanel, BorderLayout.SOUTH);

    return panel;
}
    private static JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 35));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }

    private static void showAddUserDialog(JFrame parent, List<User> users, UserTableModel model) {
        AddUserDialog dialog = new AddUserDialog(parent);
        dialog.setVisible(true);

        if (dialog.isUserAdded()) {
            User newUser = dialog.getNewUser();
            users.add(newUser);
            model.fireTableDataChanged();
            JOptionPane.showMessageDialog(parent, "User added successfully!");
        }
    }

    private static void editSelectedUser(JTable table, List<User> users, UserTableModel model) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            User user = users.get(selectedRow);
            EditUserDialog dialog = new EditUserDialog((JFrame)table.getTopLevelAncestor(), user);
            dialog.setVisible(true);

            if (dialog.isUserUpdated()) {
                model.fireTableDataChanged();
                JOptionPane.showMessageDialog(table, "User updated successfully!");
            }
        } else {
            JOptionPane.showMessageDialog(table, "Please select a user to edit", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static void deleteSelectedUsers(JTable table, List<User> users, UserTableModel model) {
        int[] selectedRows = table.getSelectedRows();
        if (selectedRows.length > 0) {
            int confirm = JOptionPane.showConfirmDialog(table, 
                    "Delete " + selectedRows.length + " selected user(s)?", 
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Delete from last to first to avoid index shifting
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    users.remove(selectedRows[i]);
                }
                model.fireTableDataChanged();
                JOptionPane.showMessageDialog(table, "User(s) deleted successfully!");
            }
        } else {
            JOptionPane.showMessageDialog(table, "Please select user(s) to delete", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static void showResetPasswordDialog(JFrame parent, User user) {
        JDialog dialog = new JDialog(parent, "Reset Password", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());
        dialog.setResizable(false);

        // Main content panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // User info
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel infoLabel = new JLabel("Reset password for: " + user.getUsername());
        infoLabel.setFont(infoLabel.getFont().deriveFont(Font.BOLD));
        contentPanel.add(infoLabel, gbc);

        // Password field
        gbc.gridy = 1; gbc.gridwidth = 1;
        contentPanel.add(new JLabel("New Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(20);
        contentPanel.add(passwordField, gbc);

        // Confirm password field
        gbc.gridx = 0; gbc.gridy = 2;
        contentPanel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField confirmField = new JPasswordField(20);
        contentPanel.add(confirmField, gbc);

        dialog.add(contentPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            String password = new String(passwordField.getPassword());
            String confirm = new String(confirmField.getPassword());

            if (!password.equals(confirm)) {
                JOptionPane.showMessageDialog(dialog, "Passwords don't match!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (password.length() < 8) {
                JOptionPane.showMessageDialog(dialog, "Password must be at least 8 characters!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(dialog, "Password reset successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    // ========== INNER CLASSES ========== //

    static class User {
        private String username;
        private String role;
        private String status;
        private String lastLogin;
        private String email;
        private String subjects;
        private String gradeLevel;

        public User(String username, String role, String status, String lastLogin) {
            this.username = username;
            this.role = role;
            this.status = status;
            this.lastLogin = lastLogin;
        }

        // Getters and setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getLastLogin() { return lastLogin; }
        public void setLastLogin(String lastLogin) { this.lastLogin = lastLogin; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getSubjects() { return subjects; }
        public void setSubjects(String subjects) { this.subjects = subjects; }
        public String getGradeLevel() { return gradeLevel; }
        public void setGradeLevel(String gradeLevel) { this.gradeLevel = gradeLevel; }
    }

 static class UserTableModel extends AbstractTableModel {
    private final List<User> users;
    private final String[] columnNames = {"Username", "Role", "Status", "Last Login", "Actions"};

    public UserTableModel(List<User> users) {
        this.users = users;
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 4; // Only Actions column is editable
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = users.get(rowIndex);
        switch (columnIndex) {
            case 0: return user.getUsername();
            case 1: return user.getRole();
            case 2: return user.getStatus();
            case 3: return user.getLastLogin();
            case 4: return "Edit | Delete | View";
            default: return null;
        }
    }

   
}



    static class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {

            Component c = super.getTableCellRendererComponent(table, value, 
                    isSelected, hasFocus, row, column);

            if (value instanceof String) {
                String status = (String) value;
                setHorizontalAlignment(SwingConstants.CENTER);

                if ("Active".equals(status)) {
                    c.setBackground(new Color(200, 255, 200));
                    c.setForeground(new Color(0, 100, 0));
                } else {
                    c.setBackground(new Color(255, 200, 200));
                    c.setForeground(new Color(100, 0, 0));
                }

                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.GRAY, 1),
                        BorderFactory.createEmptyBorder(2, 5, 2, 5)
                    ));
            }

            return c;
        }
    }

 static class ButtonRenderer extends JPanel implements TableCellRenderer {
    private final JButton editButton, deleteButton, viewButton;

    public ButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5)); // Increased spacing
        setOpaque(true);
        
        editButton = createActionButton("Edit", new Color(70, 130, 180), 80, 30);
        deleteButton = createActionButton("Delete", new Color(220, 80, 80), 80, 30);
        viewButton = createActionButton("View", new Color(100, 150, 200), 80, 30);
        
        add(editButton);
        add(deleteButton);
        add(viewButton);
    }
    
       private JButton createActionButton(String text, Color color, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12)); // Slightly larger font
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setPreferredSize(new Dimension(width, height));
        return button;
    }
      @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(row % 2 == 0 ? table.getBackground() : new Color(240, 240, 240));
        }
        return this;
    }
}
    static class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel;
    private final JButton editButton, deleteButton, viewButton;
    private int currentRow;

    public ButtonEditor(JTable table, List<User> users, UserTableModel model) {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5)); // Increased spacing
        panel.setOpaque(true);
        
        editButton = createActionButton("Edit", new Color(70, 130, 180), 80, 30);
        deleteButton = createActionButton("Delete", new Color(220, 80, 80), 80, 30);
        viewButton = createActionButton("View", new Color(100, 150, 200), 80, 30);
        
        // Add action listeners (same as before)
        editButton.addActionListener(e -> {
            User user = users.get(currentRow);
            EditUserDialog dialog = new EditUserDialog((JFrame)SwingUtilities.getWindowAncestor(table), user);
            dialog.setVisible(true);
            if (dialog.isUserUpdated()) {
                model.fireTableRowsUpdated(currentRow, currentRow);
            }
            fireEditingStopped();
        });
        
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(table, 
                "Delete user " + users.get(currentRow).getUsername() + "?", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                users.remove(currentRow);
                model.fireTableDataChanged();
            }
            fireEditingStopped();
        });
        
        viewButton.addActionListener(e -> {
            User user = users.get(currentRow);
            JOptionPane.showMessageDialog(table, 
                "Viewing details for: " + user.getUsername() + 
                "\nRole: " + user.getRole() + 
                "\nStatus: " + user.getStatus(),
                "User Details", JOptionPane.INFORMATION_MESSAGE);
            fireEditingStopped();
        });
        
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(viewButton);
    }
    
    
        
     private JButton createActionButton(String text, Color color, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12)); // Slightly larger font
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setPreferredSize(new Dimension(width, height));
        return button;
    }

   @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        currentRow = row;
        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
        } else {
            panel.setBackground(row % 2 == 0 ? table.getBackground() : new Color(240, 240, 240));
        }
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
}

  static class AddUserDialog extends JDialog {
    private User newUser;
    private boolean userAdded = false;
    private JTextField usernameField, emailField;
    private JComboBox<String> roleComboBox;
    private static final Color DARK_BLUE = new Color(0, 51, 102);
    private static final Color LIGHT_TEXT = Color.WHITE;

    public AddUserDialog(JFrame parent) {
        super(parent, "Add New User", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setResizable(false);

        // Main content panel (unchanged)
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username field (unchanged)
        gbc.gridx = 0; gbc.gridy = 0;
        contentPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        contentPanel.add(usernameField, gbc);

        // Email field (unchanged)
        gbc.gridx = 0; gbc.gridy = 1;
        contentPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        contentPanel.add(emailField, gbc);

        // Role selection (combobox remains default style)
        gbc.gridx = 0; gbc.gridy = 2;
        contentPanel.add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        roleComboBox = new JComboBox<>(new String[]{"Student", "Teacher", "Admin"});
        contentPanel.add(roleComboBox, gbc);

        add(contentPanel, BorderLayout.CENTER);

        // Button panel with dark blue button
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        
        // Style ONLY the button
        saveButton.setBackground(DARK_BLUE);
        saveButton.setForeground(LIGHT_TEXT);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        saveButton.setFocusPainted(false);
        
        // Hover effect
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                saveButton.setBackground(new Color(0, 68, 136)); // Slightly lighter blue
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                saveButton.setBackground(DARK_BLUE);
            }
        });
        
        saveButton.addActionListener(e -> saveUser());
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void saveUser() {
        // Existing save logic unchanged
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String role = (String) roleComboBox.getSelectedItem();

        if (username.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        newUser = new User(username, role, "Active", "Never");
        newUser.setEmail(email);
        userAdded = true;
        dispose();
    }

    public boolean isUserAdded() { return userAdded; }
    public User getNewUser() { return newUser; }
}
    static class EditUserDialog extends JDialog {
        private User user;
        private boolean userUpdated = false;
        private JComboBox<String> statusComboBox;

        public EditUserDialog(JFrame parent, User user) {
            super(parent, "Edit User", true);
            this.user = user;
            setSize(350, 200);
            setLocationRelativeTo(parent);
            setLayout(new BorderLayout());
            setResizable(false);

            // Main content panel
            JPanel contentPanel = new JPanel(new GridBagLayout());
            contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Username (read-only)
            gbc.gridx = 0; gbc.gridy = 0;
            contentPanel.add(new JLabel("Username:"), gbc);
            gbc.gridx = 1;
            JTextField usernameField = new JTextField(user.getUsername());
            usernameField.setEditable(false);
            contentPanel.add(usernameField, gbc);

            // Status selection
            gbc.gridx = 0; gbc.gridy = 1;
            contentPanel.add(new JLabel("Status:"), gbc);
            gbc.gridx = 1;
            statusComboBox = new JComboBox<>(new String[]{"Active", "Inactive"});
            statusComboBox.setSelectedItem(user.getStatus());
            contentPanel.add(statusComboBox, gbc);

            add(contentPanel, BorderLayout.CENTER);

            // Button panel
            JPanel buttonPanel = new JPanel();
            JButton saveButton = new JButton("Save Changes");
            saveButton.addActionListener(e -> saveChanges());
            buttonPanel.add(saveButton);
            add(buttonPanel, BorderLayout.SOUTH);
        }

        private void saveChanges() {
            user.setStatus((String) statusComboBox.getSelectedItem());
            userUpdated = true;
            dispose();
        }

        public boolean isUserUpdated() { return userUpdated; }
    }

  
  

   private static JPanel createReportsPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel.setBackground(Color.WHITE);

    // Title
    JLabel titleLabel = new JLabel("Reports", JLabel.CENTER);
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
    titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    panel.add(titleLabel, BorderLayout.NORTH);

    // Report data
    Object[][] reportData = {
        {"User Activity", "2023-06-01 14:30", "Ready"},
        {"System Logs", "2023-06-01 10:15", "Ready"},
        {"Enrollment Summary", "2023-05-31 09:45", "Processing"}
    };

    // Create table model
    DefaultTableModel model = new DefaultTableModel(reportData, new String[]{"Report Type", "Generated On", "Status", "Actions"}) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3; // Only Actions column is editable
        }
    };
    
          JTable table = new JTable(model) {
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component c = super.prepareRenderer(renderer, row, column);
            if (!isRowSelected(row)) {
                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 240, 240));
            }
            return c;
        }
    };

    
          table.setRowHeight(50); // Larger row height
    table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
    table.setShowGrid(false);
    
    
         DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    for (int i = 0; i < table.getColumnCount() - 1; i++) {
        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
    
    
    
    
    
    
        JTable reportTable = new JTable(model);
        reportTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        reportTable.getColumnModel().getColumn(3).setCellEditor(new ReportButtonEditor(new JCheckBox()));

        // Status column styling
         table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            c.setFont(new Font("Segoe UI", Font.BOLD, 12));
            if ("Ready".equals(value)) {
                c.setForeground(new Color(0, 128, 0)); // Green
            } else if ("Processing".equals(value)) {
                c.setForeground(new Color(200, 100, 0)); // Orange
            }
            setHorizontalAlignment(SwingConstants.CENTER);
            return c;
        }
    });   
    
    
    
          table.getColumnModel().getColumn(3).setCellRenderer(new ReportButtonRenderer());
    table.getColumnModel().getColumn(3).setCellEditor(new ReportButtonEditor(new JCheckBox()));

    // Set column widths
    table.getColumnModel().getColumn(0).setPreferredWidth(200); // Report Type
    table.getColumnModel().getColumn(1).setPreferredWidth(150); // Generated On
    table.getColumnModel().getColumn(2).setPreferredWidth(120); // Status
    table.getColumnModel().getColumn(3).setPreferredWidth(250); // Actions (wider)

    panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Controls panel
      JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    controlsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    controlsPanel.setBackground(Color.WHITE);

    String[] reportTypes = {"User Activity", "System Logs", "Enrollment Summary", "Custom Report"};
    JComboBox<String> reportTypeCombo = new JComboBox<>(reportTypes);

    JButton generateButton = createStyledButton("Generate Report", PRIMARY_COLOR);
    generateButton.addActionListener(e -> {
        String selectedReport = (String) reportTypeCombo.getSelectedItem();
        JOptionPane.showMessageDialog(panel, 
            "Generating " + selectedReport + "...", 
            "Report Generation", 
            JOptionPane.INFORMATION_MESSAGE);
    });

    controlsPanel.add(new JLabel("Generate:"));
    controlsPanel.add(reportTypeCombo);
    controlsPanel.add(generateButton);

    panel.add(controlsPanel, BorderLayout.SOUTH);
    return panel;
}


static class ReportButtonRenderer extends JPanel implements TableCellRenderer {
    private final JButton viewButton, exportButton;

    public ReportButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        setOpaque(true);
        
        viewButton = createReportActionButton("View", new Color(70, 130, 180), 80, 30);
        exportButton = createReportActionButton("Export", new Color(60, 179, 113), 80, 30);
        
        add(viewButton);
        add(exportButton);
    }
    
      private JButton createReportActionButton(String text, Color color, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setPreferredSize(new Dimension(width, height));
        return button;
    }
    
     @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(row % 2 == 0 ? table.getBackground() : new Color(240, 240, 240));
        }
        return this;
    }
}
   static class ReportButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel;
    private final JButton viewButton, exportButton;
    private int currentRow;

    public ReportButtonEditor(JCheckBox checkBox) {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panel.setOpaque(true);
        
        viewButton = createReportActionButton("View", new Color(70, 130, 180), 80, 30);
        exportButton = createReportActionButton("Export", new Color(60, 179, 113), 80, 30);
        
        viewButton.addActionListener(e -> {
            fireEditingStopped();
            JOptionPane.showMessageDialog(panel, 
                "Viewing report details...", 
                "Report View", 
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        exportButton.addActionListener(e -> {
            fireEditingStopped();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Export Report");
            fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));
            
            if (fileChooser.showSaveDialog(panel) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(panel, 
                    "Report exported to: " + file.getAbsolutePath(), 
                    "Export Successful", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        panel.add(viewButton);
        panel.add(exportButton);
    }
    
    private JButton createReportActionButton(String text, Color color, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setPreferredSize(new Dimension(width, height));
        return button;
    }

    
        @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        currentRow = row;
        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
        } else {
            panel.setBackground(row % 2 == 0 ? table.getBackground() : new Color(240, 240, 240));
        }
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
}
    
    
   

        private String generateSampleReport(String reportName, String date) {
            return String.format(
                "%s Report\nGenerated on: %s\n\n" +
                "=== Report Summary ===\n" +
                "Total Users: 125\n" +
                "Active Users: 98\n" +
                "New Signups: 12\n" +
                "System Uptime: 99.8%%\n\n" +
                "=== Detailed Statistics ===\n" +
                "Most Active Time: 10:00-12:00\n" +
                "Popular Features: Dashboard (45%%), Reports (30%%), Settings (25%%)\n\n" +
                "=== Recommendations ===\n" +
                "1. Consider adding more report customization options\n" +
                "2. Improve system performance during peak hours\n" +
                "3. Add user feedback collection mechanism",
                reportName, date
            );
        }
    

    private static void showPasswordChangeDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Change Password", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setLayout(new BorderLayout());
        dialog.setResizable(false);

        // Main content panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Current password
        gbc.gridx = 0; gbc.gridy = 0;
        contentPanel.add(new JLabel("Current Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField currentPassField = new JPasswordField(20);
        contentPanel.add(currentPassField, gbc);

        // New password
        gbc.gridx = 0; gbc.gridy = 1;
        contentPanel.add(new JLabel("New Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField newPassField = new JPasswordField(20);
        contentPanel.add(newPassField, gbc);

        // Confirm new password
        gbc.gridx = 0; gbc.gridy = 2;
        contentPanel.add(new JLabel("Confirm New Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField confirmPassField = new JPasswordField(20);
        contentPanel.add(confirmPassField, gbc);

        // Password requirements
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JLabel requirementsLabel = new JLabel("<html>Password must be at least 8 characters<br>and include uppercase, lowercase, and numbers</html>");
        requirementsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        requirementsLabel.setForeground(Color.GRAY);
        contentPanel.add(requirementsLabel, gbc);

        dialog.add(contentPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("Change Password");
        okButton.setBackground(PRIMARY_COLOR);
        okButton.setForeground(Color.WHITE);
        okButton.addActionListener(e -> {
                    char[] currentPass = currentPassField.getPassword();
                    char[] newPass = newPassField.getPassword();
                    char[] confirmPass = confirmPassField.getPassword();

                    // Basic validation
                    if (currentPass.length == 0 || newPass.length == 0 || confirmPass.length == 0) {
                        JOptionPane.showMessageDialog(dialog, "Please fill all password fields", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!Arrays.equals(newPass, confirmPass)) {
                        JOptionPane.showMessageDialog(dialog, "New passwords don't match", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (newPass.length < 8) {
                        JOptionPane.showMessageDialog(dialog, "Password must be at least 8 characters", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Check password complexity (optional)
                    if (!isPasswordComplex(newPass)) {
                        JOptionPane.showMessageDialog(dialog, 
                            "Password must include uppercase, lowercase and numbers", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // In a real application, you would verify current password and update it
                    JOptionPane.showMessageDialog(dialog, "Password changed successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
            });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    // Helper method to check password complexity
    private static boolean isPasswordComplex(char[] password) {
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;

        for (char c : password) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isLowerCase(c)) hasLower = true;
            if (Character.isDigit(c)) hasDigit = true;
        }

        return hasUpper && hasLower && hasDigit;
    }

    private static JPanel createEditableProfilePanel(JFrame parentFrame) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // Header
        JLabel header = new JLabel("Profile Management", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(header, BorderLayout.NORTH);

        // Main content
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Profile picture
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridheight = 3;
        JLabel profilePic = new JLabel(new ImageIcon(
                    new ImageIcon("default_profile.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)
                ));
        profilePic.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JButton changePicButton = new JButton("Change Picture");
        changePicButton.addActionListener(e -> {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                            "Image files", "jpg", "jpeg", "png", "gif"));

                    if (fileChooser.showOpenDialog(panel) == JFileChooser.APPROVE_OPTION) {
                        profilePic.setIcon(new ImageIcon(new ImageIcon(
                                    fileChooser.getSelectedFile().getPath()).getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)
                            ));
                    }
            });

        JPanel picPanel = new JPanel(new BorderLayout());
        picPanel.add(profilePic, BorderLayout.CENTER);
        picPanel.add(changePicButton, BorderLayout.SOUTH);
        contentPanel.add(picPanel, gbc);

        // Profile fields
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridheight = 1;
        String[][] fields = {
                {"Username:", "admin", "false"},
                {"Full Name:", "System Administrator", "true"},
                {"Email:", "admin@university.edu", "true"},
                {"Phone:", "+1 (555) 123-4567", "true"},
                {"Department:", "IT Administration", "true"},
                {"Last Login:", "2023-06-01 14:30", "false"}
            };

        for (int i = 0; i < fields.length; i++) {
            gbc.gridy = i;

            JLabel label = new JLabel(fields[i][0]);
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            contentPanel.add(label, gbc);

            gbc.gridx = 2;
            if (fields[i][2].equals("true")) {
                JTextField field = new JTextField(fields[i][1], 20);
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                        BorderFactory.createEmptyBorder(5, 8, 5, 8)
                    ));
                contentPanel.add(field, gbc);
            } else {
                JLabel valueLabel = new JLabel(fields[i][1]);
                valueLabel.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
                contentPanel.add(valueLabel, gbc);
            }
            gbc.gridx = 1;
        }

        // Security section
        gbc.gridy = fields.length; gbc.gridx = 1; gbc.gridwidth = 2;
        JButton changePassBtn = new JButton("Change Password");
        changePassBtn.addActionListener(e -> showPasswordChangeDialog(parentFrame));
        contentPanel.add(changePassBtn, gbc);

        panel.add(contentPanel, BorderLayout.CENTER);

        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton auditBtn = new JButton("View Access Log");
        auditBtn.addActionListener(e -> showAccessLog());

        JButton saveBtn = new JButton("Save Changes");
        saveBtn.setBackground(PRIMARY_COLOR);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.addActionListener(e -> {
                    JOptionPane.showMessageDialog(panel, "Profile changes saved successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            });

        buttonPanel.add(auditBtn);
        buttonPanel.add(saveBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private static void showAccessLog() {
        JDialog logDialog = new JDialog();
        logDialog.setTitle("Access Log");
        logDialog.setSize(700, 500);
        logDialog.setLocationRelativeTo(null);

        JTextArea logContent = new JTextArea();
        logContent.setEditable(false);
        logContent.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Sample log data
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("=== System Access Log ===\n\n");
        logBuilder.append("2023-06-01 14:30:22 - ADMIN LOGIN (IP: 192.168.1.100)\n");
        logBuilder.append("2023-06-01 12:45:10 - USER MANAGEMENT: Added new teacher account\n");
        logBuilder.append("2023-06-01 11:20:33 - SYSTEM SETTINGS: Changed maintenance mode\n");
        logBuilder.append("2023-05-31 16:15:44 - REPORTS: Generated user activity report\n");
        logBuilder.append("2023-05-31 09:30:12 - ADMIN LOGIN (IP: 192.168.1.105)\n");
        logBuilder.append("2023-05-30 14:22:01 - PASSWORD: Reset for user teacher1\n");

        logContent.setText(logBuilder.toString());

        logDialog.add(new JScrollPane(logContent));
        logDialog.setVisible(true);
    }

    private static JPanel createFooterPanel(JFrame frame) {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel versionLabel = new JLabel("v1.0.0");
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerPanel.add(versionLabel);

        footerPanel.add(Box.createHorizontalStrut(20));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setBackground(new Color(255, 69, 0));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setPreferredSize(new Dimension(100, 35));
        logoutButton.addActionListener(e -> {
                    frame.dispose();

            });

        footerPanel.add(logoutButton);
        return footerPanel;
    }


}