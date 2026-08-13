package reservation;

/**
 * Online Reservation System - Main Entry Point
 * OIBSIP Internship Task
 */
public class Main {
    public static void main(String[] args) {
        // Launch the Login Form
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}
