package com.assignation.services;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    // Identifiants par défaut pour le test
    private static final String DEFAULT_EMAIL = "admin@example.com";
    private static final String DEFAULT_PASSWORD = "password123";

    public boolean authenticate(String email, String password) {
        // Vérification simple des identifiants
        // À remplacer par une vraie authentification (base de données, API, etc.)
        return DEFAULT_EMAIL.equals(email) && DEFAULT_PASSWORD.equals(password);
    }
}
