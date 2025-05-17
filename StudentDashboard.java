import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class StudentDashboard {
    // Color scheme
    private static final Color PRIMARY_COLOR = new Color(0, 51, 102);
    private static final Color SIDEBAR_COLOR = new Color(240, 245, 250);
    private static final Color ACTIVE_MENU_COLOR = new Color(200, 230, 255);
    private static final Font MENU_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public static void main(String[] args) {
        openStudentDashboard("Demo Student");
    }

    public static void openStudentDashboard(String username) {
        JFrame frame = new JFrame("Student Dashboard - " + username);
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
        String[] menuItems = {"Dashboard", "Notifications", "Enrollment", "Prospectus", "Study Load", "Grades", "Profile"};
        for (String item : menuItems) {
            JButton menuButton = createMenuButton(item);
            JPanel contentPanel = createContentPanel(item, username);
            mainContentPanel.add(contentPanel, item);

            menuButton.addActionListener(e -> {
                setActiveMenuButton(sidebarPanel, menuButton);
                ((CardLayout) mainContentPanel.getLayout()).show(mainContentPanel, item);
            });
            sidebarPanel.add(menuButton);
        }

        // Logout Button
        JPanel footerPanel = createFooterPanel(frame);
        frame.add(footerPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static JPanel createHeaderPanel(String username) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel welcomeLabel = new JLabel("Welcome, " + username, JLabel.LEFT);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        headerPanel.add(welcomeLabel, BorderLayout.WEST);

        JLabel roleLabel = new JLabel("Student", JLabel.RIGHT);
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

        JLabel menuLabel = new JLabel("STUDENT MENU");
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

    private static JPanel createContentPanel(String menuItem, String username) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (menuItem.equals("Profile")) {
            panel.add(createProfilePanel(username), BorderLayout.CENTER);
        }

        return panel;
    }

    private static JPanel createProfilePanel(String username) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Mock student data
        HashMap<String, String> studentData = new HashMap<>();
        studentData.put("Full Name", "Demo Student");
        studentData.put("Email", "demo.student@example.com");
        studentData.put("Year Level", "2nd Year");
        studentData.put("Username", username);

        // Add profile fields
        int row = 0;
        for (String key : studentData.keySet()) {
            addProfileField(panel, gbc, key + ":", studentData.get(key), row++);
        }

        return panel;
    }

    private static void addProfileField(JPanel panel, GridBagConstraints gbc, String label, String value, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label, JLabel.RIGHT), gbc);

        gbc.gridx = 1;
        panel.add(new JLabel(value), gbc);
    }

    private static JPanel createFooterPanel(JFrame frame) {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setBackground(new Color(255, 69, 0));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setPreferredSize(new Dimension(100, 40));

        logoutButton.addActionListener(e -> {
            frame.dispose(); // Close the current dashboard
            JOptionPane.showMessageDialog(null, "Logged out successfully.", "Logout", JOptionPane.INFORMATION_MESSAGE);
        });

        footerPanel.add(logoutButton);
        return footerPanel;
    }

    private static void setActiveMenuButton(JPanel sidebarPanel, JButton activeButton) {
        for (Component comp : sidebarPanel.getComponents()) {
            if (comp instanceof JButton) {
                comp.setBackground(SIDEBAR_COLOR);
            }
        }
        activeButton.setBackground(ACTIVE_MENU_COLOR);
    }
}
