package reservation;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * Cancellation Form - Allows user to cancel a booked ticket using PNR.
 */
public class CancellationForm extends JFrame {

    private static final Color BG_DARK    = new Color(18, 26, 38);
    private static final Color BG_CARD    = new Color(28, 40, 56);
    private static final Color ACCENT     = new Color(255, 100, 100);
    private static final Color YELLOW     = new Color(255, 193, 7);
    private static final Color TEXT_WHITE = Color.WHITE;
    private static final Color TEXT_GRAY  = new Color(160, 170, 185);
    private static final Color FIELD_BG   = new Color(38, 55, 74);

    private JTextField pnrField;
    private JLabel statusLabel;
    private JPanel detailsPanel;
    private JLabel[] detailLabels;
    private JButton cancelConfirmBtn;

    private Reservation foundReservation;
    private final String loggedInUser;
    private final JFrame parentFrame;

    public CancellationForm(String loggedInUser, JFrame parentFrame) {
        this.loggedInUser = loggedInUser;
        this.parentFrame  = parentFrame;
        initUI();
    }

    private void initUI() {
        setTitle("Online Reservation System — Cancel Ticket");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(560, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(BG_DARK);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Header
        JPanel header = buildHeader();

        // Content panel
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(24, 30, 24, 30));

        // PNR search card
        JPanel searchCard = new JPanel();
        searchCard.setLayout(new BoxLayout(searchCard, BoxLayout.Y_AXIS));
        searchCard.setBackground(BG_CARD);
        searchCard.setBorder(new CompoundBorder(
            new LineBorder(new Color(255, 100, 100, 60), 1, true),
            new EmptyBorder(24, 28, 24, 28)
        ));

        JLabel pnrLabel = new JLabel("Enter PNR Number");
        pnrLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        pnrLabel.setForeground(TEXT_GRAY);
        pnrLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        pnrField = new JTextField();
        pnrField.setBackground(FIELD_BG);
        pnrField.setForeground(TEXT_WHITE);
        pnrField.setCaretColor(YELLOW);
        pnrField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        pnrField.setBorder(new CompoundBorder(
            new LineBorder(new Color(255, 100, 100, 80), 1, true),
            new EmptyBorder(10, 12, 10, 12)
        ));
        pnrField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        pnrField.setAlignmentX(Component.LEFT_ALIGNMENT);

        statusLabel = new JLabel(" ", SwingConstants.LEFT);
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton searchBtn = createButton("Search PNR", YELLOW, BG_DARK);
        searchBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        searchBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchBtn.addActionListener(e -> searchPNR());
        pnrField.addActionListener(e -> searchPNR());

        searchCard.add(pnrLabel);
        searchCard.add(Box.createVerticalStrut(8));
        searchCard.add(pnrField);
        searchCard.add(Box.createVerticalStrut(10));
        searchCard.add(statusLabel);
        searchCard.add(Box.createVerticalStrut(10));
        searchCard.add(searchBtn);

        // Details card (initially hidden)
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(BG_CARD);
        detailsPanel.setBorder(new CompoundBorder(
            new LineBorder(new Color(255, 193, 7, 60), 1, true),
            new EmptyBorder(20, 28, 20, 28)
        ));
        detailsPanel.setVisible(false);

        JLabel detailsTitle = new JLabel("Ticket Details");
        detailsTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        detailsTitle.setForeground(YELLOW);
        detailsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 193, 7, 50));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        String[] fieldNames = {"PNR Number", "Passenger Name", "Train No.", "Train Name",
                               "Class", "Date of Journey", "From", "To"};
        detailLabels = new JLabel[fieldNames.length];

        detailsPanel.add(detailsTitle);
        detailsPanel.add(Box.createVerticalStrut(8));
        detailsPanel.add(sep);
        detailsPanel.add(Box.createVerticalStrut(12));

        for (int i = 0; i < fieldNames.length; i++) {
            JPanel row = new JPanel(new BorderLayout());
            row.setOpaque(false);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
            row.setBorder(new EmptyBorder(2, 0, 2, 0));

            JLabel key = new JLabel(fieldNames[i] + ":");
            key.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            key.setForeground(TEXT_GRAY);
            key.setPreferredSize(new Dimension(130, 24));

            detailLabels[i] = new JLabel("—");
            detailLabels[i].setFont(new Font("Segoe UI", Font.BOLD, 12));
            detailLabels[i].setForeground(TEXT_WHITE);

            row.add(key, BorderLayout.WEST);
            row.add(detailLabels[i], BorderLayout.CENTER);
            detailsPanel.add(row);
        }

        detailsPanel.add(Box.createVerticalStrut(16));

        JPanel btnRow = new JPanel(new GridLayout(1, 2, 12, 0));
        btnRow.setOpaque(false);
        btnRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        JButton keepBtn = createButton("Keep Ticket", FIELD_BG, TEXT_GRAY);
        keepBtn.setBorder(new LineBorder(TEXT_GRAY, 1, true));
        keepBtn.addActionListener(e -> resetDetails());

        cancelConfirmBtn = createButton("Confirm Cancellation", ACCENT, TEXT_WHITE);
        cancelConfirmBtn.addActionListener(e -> confirmCancellation());

        btnRow.add(keepBtn);
        btnRow.add(cancelConfirmBtn);
        detailsPanel.add(btnRow);

        // Back button at bottom
        JButton backBtn = createButton("← Back to Dashboard", BG_CARD, TEXT_GRAY);
        backBtn.setBorder(new LineBorder(TEXT_GRAY, 1, true));
        backBtn.setMaximumSize(new Dimension(200, 36));
        backBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        backBtn.addActionListener(e -> goBack());

        content.add(searchCard);
        content.add(Box.createVerticalStrut(20));
        content.add(detailsPanel);
        content.add(Box.createVerticalStrut(20));
        content.add(backBtn);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(content, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(BG_CARD);
        header.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 2, 0, ACCENT),
            new EmptyBorder(16, 24, 16, 24)
        ));
        JLabel t = new JLabel("❌  CANCEL TICKET");
        t.setFont(new Font("Segoe UI", Font.BOLD, 17));
        t.setForeground(ACCENT);
        JLabel s = new JLabel("Enter your PNR number to retrieve and cancel your booking");
        s.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        s.setForeground(TEXT_GRAY);
        header.add(t);
        header.add(Box.createVerticalStrut(4));
        header.add(s);
        return header;
    }

    private JButton createButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? bg.brighter() : bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(fg);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void searchPNR() {
        String pnr = pnrField.getText().trim();
        if (pnr.isEmpty()) {
            statusLabel.setText("⚠ Please enter a PNR number.");
            statusLabel.setForeground(new Color(255, 160, 0));
            detailsPanel.setVisible(false);
            return;
        }

        foundReservation = ReservationDatabase.getReservation(pnr);
        if (foundReservation == null) {
            statusLabel.setText("✗ No reservation found for PNR: " + pnr);
            statusLabel.setForeground(new Color(255, 80, 80));
            detailsPanel.setVisible(false);
        } else {
            statusLabel.setText("✓ Reservation found!");
            statusLabel.setForeground(new Color(80, 200, 120));
            populateDetails(foundReservation);
            detailsPanel.setVisible(true);
            pack();
            setSize(560, 600);
        }
    }

    private void populateDetails(Reservation r) {
        detailLabels[0].setText(r.getPnrNumber());
        detailLabels[1].setText(r.getPassengerName());
        detailLabels[2].setText(r.getTrainNumber());
        detailLabels[3].setText(r.getTrainName());
        detailLabels[4].setText(r.getClassType());
        detailLabels[5].setText(r.getDateOfJourney());
        detailLabels[6].setText(r.getFrom());
        detailLabels[7].setText(r.getTo());
    }

    private void confirmCancellation() {
        if (foundReservation == null) return;

        int choice = JOptionPane.showConfirmDialog(
            this,
            "<html>Are you sure you want to cancel ticket <b>" + foundReservation.getPnrNumber()
            + "</b><br>for <b>" + foundReservation.getPassengerName() + "</b>?<br>"
            + "<small>This action cannot be undone.</small></html>",
            "Confirm Cancellation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            boolean removed = ReservationDatabase.cancelReservation(foundReservation.getPnrNumber());
            if (removed) {
                JOptionPane.showMessageDialog(
                    this,
                    "<html><b style='color:green;'>✓ Ticket Cancelled Successfully!</b><br>"
                    + "PNR " + foundReservation.getPnrNumber() + " has been removed.</html>",
                    "Cancellation Successful",
                    JOptionPane.INFORMATION_MESSAGE
                );
                resetDetails();
                pnrField.setText("");
                statusLabel.setText(" ");
                foundReservation = null;
            }
        }
    }

    private void resetDetails() {
        detailsPanel.setVisible(false);
        foundReservation = null;
        statusLabel.setText(" ");
    }

    private void goBack() {
        parentFrame.setVisible(true);
        dispose();
    }
}
