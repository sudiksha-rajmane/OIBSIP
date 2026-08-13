package reservation;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main Dashboard - Shown after successful login.
 * Provides navigation to Reservation and Cancellation forms.
 */
public class MainDashboard extends JFrame {

    private static final Color BG_DARK   = new Color(18, 26, 38);
    private static final Color BG_CARD   = new Color(28, 40, 56);
    private static final Color ACCENT    = new Color(255, 193, 7);
    private static final Color TEXT_WHITE = Color.WHITE;
    private static final Color TEXT_GRAY  = new Color(160, 170, 185);

    private final String loggedInUser;

    public MainDashboard(String username) {
        this.loggedInUser = username;
        initUI();
    }

    private void initUI() {
        setTitle("Online Reservation System — Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        // Background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(BG_DARK);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Header bar
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_CARD);
        header.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 2, 0, ACCENT),
            new EmptyBorder(16, 24, 16, 24)
        ));

        JLabel titleLabel = new JLabel("🚂  ONLINE RESERVATION SYSTEM");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(ACCENT);

        JLabel userLabel = new JLabel("Logged in as: " + loggedInUser + "   ");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        userLabel.setForeground(TEXT_GRAY);

        header.add(titleLabel, BorderLayout.WEST);
        header.add(userLabel, BorderLayout.EAST);

        // Center content
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel welcomeLabel = new JLabel("Welcome, " + loggedInUser + "! Choose an option:");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        welcomeLabel.setForeground(TEXT_GRAY);

        JPanel cardsPanel = new JPanel(new GridLayout(1, 2, 24, 0));
        cardsPanel.setOpaque(false);

        cardsPanel.add(createOptionCard(
            "🎫", "Book Ticket",
            "Reserve a train ticket by filling your journey details.",
            ACCENT,
            e -> openReservationForm()
        ));

        cardsPanel.add(createOptionCard(
            "❌", "Cancel Ticket",
            "Cancel your booked ticket using your PNR number.",
            new Color(255, 100, 100),
            e -> openCancellationForm()
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 20, 0);
        centerPanel.add(welcomeLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        centerPanel.add(cardsPanel, gbc);

        // Footer
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(BG_CARD);
        footer.setBorder(new EmptyBorder(10, 0, 10, 20));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        logoutBtn.setForeground(TEXT_GRAY);
        logoutBtn.setBackground(new Color(40, 55, 74));
        logoutBtn.setBorder(new CompoundBorder(
            new LineBorder(TEXT_GRAY, 1, true),
            new EmptyBorder(6, 16, 6, 16)
        ));
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });
        footer.add(logoutBtn);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel createOptionCard(String icon, String title, String desc,
                                    Color accentColor, ActionListener action) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setBorder(new CompoundBorder(
            new LineBorder(new Color(accentColor.getRed(), accentColor.getGreen(),
                           accentColor.getBlue(), 80), 1, true),
            new EmptyBorder(30, 24, 30, 24)
        ));

        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(accentColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLabel = new JLabel("<html><center>" + desc + "</center></html>", SwingConstants.CENTER);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(TEXT_GRAY);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btn = createCardButton(title, accentColor);
        btn.addActionListener(action);

        card.add(iconLabel);
        card.add(Box.createVerticalStrut(12));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(descLabel);
        card.add(Box.createVerticalStrut(20));
        card.add(btn);

        return card;
    }

    private JButton createCardButton(String text, Color color) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(color.brighter());
                } else {
                    g2.setColor(color);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(BG_DARK);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }

    private void openReservationForm() {
        new ReservationForm(loggedInUser, this).setVisible(true);
        setVisible(false);
    }

    private void openCancellationForm() {
        new CancellationForm(loggedInUser, this).setVisible(true);
        setVisible(false);
    }
}
