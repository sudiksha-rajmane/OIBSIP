package reservation;

import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
    private static final Map<String, String> users = new HashMap<>();

    static {
        users.put("admin", "admin123");
        users.put("user1", "pass1");
        users.put("rahul", "rahul@123");
        users.put("priya", "priya@456");
    }

    public static boolean authenticate(String username, String password) {
        // Koi bhi non-empty username/password se login ho jayega
        if (username != null && !username.trim().isEmpty() 
            && password != null && !password.trim().isEmpty()) {
            return true;
        }
        return false;
    }
}
