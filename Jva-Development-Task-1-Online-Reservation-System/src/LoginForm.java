package reservation;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Login Form - Entry point UI.
 * Users must authenticate before accessing the reservation system.
 */
public class LoginForm extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;

    // Color palette
    private static final Color BG_DARK    = new Color(18, 26, 38);
    private static final Color BG_CARD    = new Color(28, 40, 56);
    private static final Color ACCENT     = new Color(255, 193, 7);
    private static final Color TEXT_WHITE = Color.WHITE;
    private static final Color TEXT_GRAY  = new Color(160, 170, 185);
    private static final Color FIELD_BG   = new Color(38, 55, 74);
    private static final Color BTN_HOVER  = new Color(218, 165, 0);

    public LoginForm() {
        initUI();
    }

    private void initUI() {
        setTitle("Online Reservation System — Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(460, 520);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main background panel
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                    0, 0, BG_DARK,
                    getWidth(), getHeight(), new Color(10, 18, 30)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Card panel
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setBorder(new CompoundBorder(
            new LineBorder(new Color(255, 193, 7, 60), 1, true),
            new EmptyBorder(40, 45, 40, 45)
        ));
        card.setPreferredSize(new Dimension(380, 420));

        // Icon / title area
        JLabel iconLabel = new JLabel("🚂", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("RESERVATION SYSTEM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(ACCENT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Please login to continue", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(TEXT_GRAY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Username field
        JLabel userLabel = new JLabel("Username");
        userLabel.setForeground(TEXT_GRAY);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        usernameField = createStyledTextField();

        // Password field
        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(TEXT_GRAY);
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        passwordField = new JPasswordField();
        stylePasswordField(passwordField);

        // Status label
        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setForeground(new Color(255, 80, 80));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Login button
        loginButton = createLoginButton();

        // Hint label
        JLabel hintLabel = new JLabel("Hint: admin / admin123", SwingConstants.CENTER);
        hintLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        hintLabel.setForeground(new Color(100, 120, 140));
        hintLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Assemble card
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(4));
        card.add(subtitleLabel);
        card.add(Box.createVerticalStrut(30));
        card.add(userLabel);
        card.add(Box.createVerticalStrut(6));
        card.add(usernameField);
        card.add(Box.createVerticalStrut(16));
        card.add(passLabel);
        card.add(Box.createVerticalStrut(6));
        card.add(passwordField);
        card.add(Box.createVerticalStrut(12));
        card.add(statusLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(loginButton);
        card.add(Box.createVerticalStrut(16));
        card.add(hintLabel);

        mainPanel.add(card);
        setContentPane(mainPanel);

        // Login action
        ActionListener loginAction = e -> performLogin();
        loginButton.addActionListener(loginAction);
        passwordField.addActionListener(loginAction);
        usernameField.addActionListener(loginAction);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setBackground(FIELD_BG);
        field.setForeground(TEXT_WHITE);
        field.setCaretColor(ACCENT);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(new CompoundBorder(
            new LineBorder(new Color(255, 193, 7, 80), 1, true),
            new EmptyBorder(10, 12, 10, 12)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private void stylePasswordField(JPasswordField field) {
        field.setBackground(FIELD_BG);
        field.setForeground(TEXT_WHITE);
        field.setCaretColor(ACCENT);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(new CompoundBorder(
            new LineBorder(new Color(255, 193, 7, 80), 1, true),
            new EmptyBorder(10, 12, 10, 12)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JButton createLoginButton() {
        JButton btn = new JButton("LOGIN") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(BTN_HOVER.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(BTN_HOVER);
                } else {
                    g2.setColor(ACCENT);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(BG_DARK);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        return btn;
    }

    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        // trim spaces from password too
        password = password.trim();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("⚠ Please enter username and password.");
            return;
        }

        if (UserDatabase.authenticate(username, password)) {
            // Open main dashboard
            new MainDashboard(username).setVisible(true);
            dispose();
        } else {
            statusLabel.setText("✗ Invalid credentials. Try again.");
            passwordField.setText("");
            shake(this);
        }
    }

    /** Shake animation on failed login */
    private void shake(JFrame frame) {
        Point orig = frame.getLocation();
        Timer timer = new Timer(30, null);
        int[] count = {0};
        int[] offsets = {-10, 10, -8, 8, -5, 5, -3, 3, 0};
        timer.addActionListener(e -> {
            if (count[0] < offsets.length) {
                frame.setLocation(orig.x + offsets[count[0]], orig.y);
                count[0]++;
            } else {
                timer.stop();
                frame.setLocation(orig);
            }
        });
        timer.start();
    }
}
