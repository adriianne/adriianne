import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;


public class TeacherDashboard {
    public static void main(String[] args) {
        openTeacherDashboard("Demo Teacher");
    }

    public static void openTeacherDashboard(String username) {
        JFrame frame = new JFrame("Teacher Dashboard - " + username);
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
        String[] menuItems = {"Grades", "Student List", "Profile"};
        for (String item : menuItems) {
            JButton menuButton = createMenuButton(item);
            JPanel contentPanel = createContentPanel(item);
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

    private static JPanel createHeaderPanel(String username) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 51, 102));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel welcomeLabel = new JLabel("Welcome, " + username, JLabel.LEFT);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        headerPanel.add(welcomeLabel, BorderLayout.WEST);

        JLabel roleLabel = new JLabel("Teacher", JLabel.RIGHT);
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roleLabel.setForeground(Color.WHITE);
        headerPanel.add(roleLabel, BorderLayout.EAST);

        return headerPanel;
    }

    private static JPanel createSidebarPanel() {
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setPreferredSize(new Dimension(250, 0));
        sidebarPanel.setBackground(new Color(230, 230, 250));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        JLabel menuLabel = new JLabel("TEACHER MENU");
        menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sidebarPanel.add(menuLabel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        return sidebarPanel;
    }

    private static JButton createMenuButton(String item) {
        JButton menuButton = new JButton(item);
        menuButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        menuButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        menuButton.setBackground(new Color(230, 230, 250));
        menuButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 10));
        menuButton.setHorizontalAlignment(SwingConstants.LEFT);
        menuButton.setToolTipText("Go to " + item);

        menuButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                menuButton.setBackground(new Color(180, 180, 220));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (!menuButton.getBackground().equals(new Color(180, 180, 220))) {
                    menuButton.setBackground(new Color(230, 230, 250));
                }
            }
        });

        return menuButton;
    }

    private static JPanel createContentPanel(String menuItem) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (menuItem.equals("Grades")) {
            panel.add(createGradesTable(), BorderLayout.CENTER);
        } else if (menuItem.equals("Student List")) {
            panel.add(createStudentListTable(), BorderLayout.CENTER);
        } else if (menuItem.equals("Profile")) {
            panel.add(createProfilePanel(), BorderLayout.CENTER);
        }

        return panel;
    }

    private static JScrollPane createGradesTable() {
        String[] columns = {"Student Name", "Subject", "Grade"};
        Object[][] data = {
            {"Alice", "Math", "A"},
            {"Bob", "Science", "B"},
            {"Charlie", "History", "C"},
            {"Diana", "English", "A"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);

        // Add row sorter
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        return new JScrollPane(table);
    }

   private static JScrollPane createStudentListTable() {
    String[] columns = {"ID Number", "Name", "Year Level", "Email"};
    
    // Get all students from enrollment database
    HashMap<String, EnrollmentFormUI.Student> students = EnrollmentFormUI.getStudentDatabase();
    Object[][] data = new Object[students.size()][4];
    
    int i = 0;
    for (EnrollmentFormUI.Student student : students.values()) {
        data[i][0] = "S" + (1000 + i); // Generate student ID
        data[i][0] = student.getIdNumber();  // Display ID number
        data[i][1] = student.getFullName();
        data[i][2] = student.getYearLevel();
        data[i][3] = student.getEmail();
        i++;
    }

    DefaultTableModel model = new DefaultTableModel(data, columns);
    JTable table = new JTable(model);
    table.setFillsViewportHeight(true);

    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    table.setRowSorter(sorter);

    return new JScrollPane(table);
}

    private static JPanel createProfilePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Profile");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel usernameLabel = new JLabel("Username: Demo Teacher");
        JLabel roleLabel = new JLabel("Role: Teacher");
        JLabel emailLabel = new JLabel("Email: teacher@example.com");
        JLabel contactLabel = new JLabel("Contact: +123456789");

        panel.add(usernameLabel);
        panel.add(roleLabel);
        panel.add(emailLabel);
        panel.add(contactLabel);

        return panel;
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
            LoginUI.createLoginUI(); // Redirect to Login UI
        });

        footerPanel.add(logoutButton);
        return footerPanel;
    }

    private static void setActiveMenuButton(JPanel sidebarPanel, JButton activeButton) {
        for (Component comp : sidebarPanel.getComponents()) {
            if (comp instanceof JButton) {
                comp.setBackground(new Color(230, 230, 250));
            }
        }
        activeButton.setBackground(new Color(180, 180, 220));
    }
}