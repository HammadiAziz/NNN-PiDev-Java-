package tn.esprit.services;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static final Map<String, Object> sessionData = new HashMap<>();

    public static void setSession(String key, Object value) {
        sessionData.put(key, value);
    }

    public static Object getSession(String key) {
        return sessionData.get(key);
    }

    public static void removeSession(String key) {
        sessionData.remove(key);
    }

    public static void clearSession() {
        sessionData.clear();
    }
}
