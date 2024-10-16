package com.example.tolist_de_aprendizaje;

public class SessionManager {
    private static int userId; // Almacena el ID del usuario

    public static void setUserId(int id) {
        userId = id;
    }

    public static int getUserId() {
        return userId;
    }
}
