package reservation;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * In-memory store for all reservations, keyed by PNR number.
 */
public class ReservationDatabase {
    private static final Map<String, Reservation> reservations = new HashMap<>();

    /**
     * Generate a unique 8-digit PNR number.
     */
    public static String generatePNR() {
        Random rnd = new Random();
        String pnr;
        do {
            pnr = String.format("%08d", rnd.nextInt(100000000));
        } while (reservations.containsKey(pnr));
        return pnr;
    }

    /**
     * Save a reservation.
     */
    public static void addReservation(Reservation r) {
        reservations.put(r.getPnrNumber(), r);
    }

    /**
     * Retrieve a reservation by PNR.
     */
    public static Reservation getReservation(String pnr) {
        return reservations.get(pnr);
    }

    /**
     * Cancel (remove) a reservation by PNR.
     * @return true if found and removed, false otherwise.
     */
    public static boolean cancelReservation(String pnr) {
        return reservations.remove(pnr) != null;
    }
}
