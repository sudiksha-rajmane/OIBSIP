package reservation;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Reservation Form - Allows user to book a train ticket.
 * Collects passenger details and generates a PNR number.
 */
public class ReservationForm extends JFrame {

    private static final Color BG_DARK    = new Color(18, 26, 38);
    private static final Color BG_CARD    = new Color(28, 40, 56);
    private static final Color ACCENT     = new Color(255, 193, 7);
    private static final Color TEXT_WHITE = Color.WHITE;
    private static final Color TEXT_GRAY  = new Color(160, 170, 185);
    private static final Color FIELD_BG   = new Color(38, 55, 74);

    private JTextField nameField, trainNumberField, trainNameField;
    private JTextField dateField, fromField, toField;
    private JComboBox<String> classTypeBox;
    private JLabel statusLabel;

    private final String loggedInUser;
    private final JFrame parentFrame;

    // Train data: number -> name (auto-fill)
    private static final String[][] TRAINS = {
        {"12301", "Howrah Rajdhani Express"},
        {"12951", "Mumbai Rajdhani Express"},
        {"12002", "Bhopal Shatabdi Express"},
        {"12627", "Karnataka Express"},
        {"22222", "Hazrat Nizamuddin Duronto"},
        {"12560", "Shiv Ganga Express"},
        {"12618", "Mangala Lakshadweep Express"},
    };

    public ReservationForm(String loggedInUser, JFrame parentFrame) {
        this.loggedInUser = loggedInUser;
        this.parentFrame  = parentFrame;
        initUI();
    }

    private void initUI() {
        setTitle("Online Reservation System — Book Ticket");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(580, 680);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(BG_DARK);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Header
        JPanel header = buildHeader("🎫  BOOK TICKET", "Fill in the details to reserve your seat");

        // Form panel
        JPanel formCard = new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(BG_CARD);
        formCard.setBorder(new CompoundBorder(
            new LineBorder(new Color(255, 193, 7, 50), 1, true),
            new EmptyBorder(30, 35, 30, 35)
        ));

        // Fields
        nameField        = addField(formCard, "Passenger Name");
        trainNumberField = addField(formCard, "Train Number");

        // Auto-fill train name on train number input
        trainNumberField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                autoFillTrainName(trainNumberField.getText().trim());
            }
        });

        trainNameField   = addField(formCard, "Train Name");
        trainNameField.setEditable(false);
        trainNameField.setBackground(new Color(30, 44, 60));

        // Class type dropdown
        formCard.add(createLabel("Class Type"));
        formCard.add(Box.createVerticalStrut(6));
        classTypeBox = new JComboBox<>(new String[]{
            "Select Class", "Sleeper (SL)", "AC 3 Tier (3A)",
            "AC 2 Tier (2A)", "AC 1st Class (1A)", "General (GN)"
        });
        styleComboBox(classTypeBox);
        formCard.add(classTypeBox);
        formCard.add(Box.createVerticalStrut(14));

        dateField = addField(formCard, "Date of Journey (DD/MM/YYYY)");
        fromField = addField(formCard, "From (Station / City)");
        toField   = addField(formCard, "To (Station / City)");

        // Status
        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 16, 0));
        btnPanel.setOpaque(false);
        btnPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));

        JButton backBtn = createButton("← Back", BG_CARD, TEXT_GRAY);
        backBtn.setBorder(new LineBorder(TEXT_GRAY, 1, true));
        backBtn.addActionListener(e -> goBack());

        JButton insertBtn = createButton("Insert / Book", ACCENT, BG_DARK);
        insertBtn.addActionListener(e -> performReservation());

        btnPanel.add(backBtn);
        btnPanel.add(insertBtn);

        formCard.add(statusLabel);
        formCard.add(Box.createVerticalStrut(14));
        formCard.add(btnPanel);

        // Scroll pane
        JScrollPane scroll = new JScrollPane(formCard);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getViewport().setBackground(BG_DARK);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(20, 30, 20, 30));
        wrapper.add(scroll, BorderLayout.CENTER);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(wrapper, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private JTextField addField(JPanel panel, String label) {
        panel.add(createLabel(label));
        panel.add(Box.createVerticalStrut(6));
        JTextField field = new JTextField();
        field.setBackground(FIELD_BG);
        field.setForeground(TEXT_WHITE);
        field.setCaretColor(ACCENT);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(new CompoundBorder(
            new LineBorder(new Color(255, 193, 7, 70), 1, true),
            new EmptyBorder(9, 12, 9, 12)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(field);
        panel.add(Box.createVerticalStrut(14));
        return field;
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(TEXT_GRAY);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private void styleComboBox(JComboBox<String> box) {
        box.setBackground(FIELD_BG);
        box.setForeground(TEXT_WHITE);
        box.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        box.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
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

    private JPanel buildHeader(String title, String subtitle) {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(BG_CARD);
        header.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 2, 0, ACCENT),
            new EmptyBorder(16, 24, 16, 24)
        ));
        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 17));
        t.setForeground(ACCENT);
        JLabel s = new JLabel(subtitle);
        s.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        s.setForeground(TEXT_GRAY);
        header.add(t);
        header.add(Box.createVerticalStrut(4));
        header.add(s);
        return header;
    }

    private void autoFillTrainName(String number) {
        for (String[] train : TRAINS) {
            if (train[0].equals(number)) {
                trainNameField.setText(train[1]);
                return;
            }
        }
        if (!number.isEmpty()) {
            trainNameField.setText(""); // Unknown train
        }
    }

    private void performReservation() {
        String name       = nameField.getText().trim();
        String trainNum   = trainNumberField.getText().trim();
        String trainName  = trainNameField.getText().trim();
        String classType  = (String) classTypeBox.getSelectedItem();
        String date       = dateField.getText().trim();
        String from       = fromField.getText().trim();
        String to         = toField.getText().trim();

        // Validation
        if (name.isEmpty() || trainNum.isEmpty() || date.isEmpty()
                || from.isEmpty() || to.isEmpty()
                || "Select Class".equals(classType)) {
            showStatus("⚠ Please fill all fields correctly.", new Color(255, 160, 0));
            return;
        }

        if (!date.matches("\\d{2}/\\d{2}/\\d{4}")) {
            showStatus("⚠ Date format must be DD/MM/YYYY.", new Color(255, 160, 0));
            return;
        }

        if (from.equalsIgnoreCase(to)) {
            showStatus("⚠ 'From' and 'To' stations cannot be same.", new Color(255, 160, 0));
            return;
        }

        // If train name not filled, set a generic one
        if (trainName.isEmpty()) trainName = "Express Train";

        String pnr = ReservationDatabase.generatePNR();
        Reservation res = new Reservation(pnr, name, trainNum, trainName,
                                          classType, date, from, to);
        ReservationDatabase.addReservation(res);

        showSuccessDialog(res);
        clearFields();
    }

    private void showSuccessDialog(Reservation res) {
        String msg = "<html><div style='font-family:Segoe UI; width:320px;'>"
            + "<h3 style='color:#28a745;'>✓ Ticket Booked Successfully!</h3>"
            + "<table border='0' cellpadding='4'>"
            + "<tr><td><b>PNR Number:</b></td><td><b>" + res.getPnrNumber() + "</b></td></tr>"
            + "<tr><td><b>Name:</b></td><td>" + res.getPassengerName() + "</td></tr>"
            + "<tr><td><b>Train:</b></td><td>" + res.getTrainNumber() + " - " + res.getTrainName() + "</td></tr>"
            + "<tr><td><b>Class:</b></td><td>" + res.getClassType() + "</td></tr>"
            + "<tr><td><b>Date:</b></td><td>" + res.getDateOfJourney() + "</td></tr>"
            + "<tr><td><b>Route:</b></td><td>" + res.getFrom() + " → " + res.getTo() + "</td></tr>"
            + "</table>"
            + "<p style='color:gray; font-size:11px;'>Please note your PNR number for cancellation.</p>"
            + "</div></html>";

        JOptionPane.showMessageDialog(this, msg, "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showStatus(String msg, Color color) {
        statusLabel.setText(msg);
        statusLabel.setForeground(color);
    }

    private void clearFields() {
        nameField.setText("");
        trainNumberField.setText("");
        trainNameField.setText("");
        classTypeBox.setSelectedIndex(0);
        dateField.setText("");
        fromField.setText("");
        toField.setText("");
        statusLabel.setText(" ");
    }

    private void goBack() {
        parentFrame.setVisible(true);
        dispose();
    }
}
