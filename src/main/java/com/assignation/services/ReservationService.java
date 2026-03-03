package com.assignation.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.assignation.models.Reservation;
import com.assignation.models.ReservationApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ReservationService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    // Format de date du backend: "Feb 6, 2026, 3:19:00 PM"
    private final DateTimeFormatter backendFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

    @Value("${api.reservations.url}")
    private String apiUrl;

    @Value("${api.auth.token}")
    private String authToken;

    public ReservationService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    private HttpHeaders createAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authToken);
        return headers;
    }

    public List<Reservation> getAllReservations() {
        try {
            HttpEntity<Void> entity = new HttpEntity<>(createAuthHeaders());
            ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                String.class
            );
            String json = response.getBody();
            ReservationApiResponse apiResponse = objectMapper.readValue(json, ReservationApiResponse.class);
            if (apiResponse != null && apiResponse.getData() != null && apiResponse.getData().getData() != null) {
                List<Reservation> reservations = apiResponse.getData().getData();
                reservations.sort((r1, r2) -> Integer.compare(r1.getId(), r2.getId()));
                return reservations;
            }
            return Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'appel API: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Reservation> getReservationsByDateRange(String dateDebut, String dateFin) {
        List<Reservation> allReservations = getAllReservations();
        
        if ((dateDebut == null || dateDebut.isEmpty()) && (dateFin == null || dateFin.isEmpty())) {
            return allReservations;
        }
        
        // Convertir les dates de filtre (format YYYY-MM-DD du formulaire HTML)
        LocalDate startDate = (dateDebut != null && !dateDebut.isEmpty()) ? LocalDate.parse(dateDebut) : null;
        LocalDate endDate = (dateFin != null && !dateFin.isEmpty()) ? LocalDate.parse(dateFin) : null;
        
        return allReservations.stream()
            .filter(r -> {
                try {
                    // Extraire la partie date du backend: "Feb 6, 2026, 3:19:00 PM" -> "Feb 6, 2026"
                    String dateStr = r.getDateheure();
                    // Trouver la position de la deuxième virgule pour extraire "Feb 6, 2026"
                    int firstComma = dateStr.indexOf(',');
                    int secondComma = dateStr.indexOf(',', firstComma + 1);
                    String datePart = dateStr.substring(0, secondComma);
                    
                    LocalDate reservationDate = LocalDate.parse(datePart, backendFormatter);
                    
                    boolean afterStart = (startDate == null) || !reservationDate.isBefore(startDate);
                    boolean beforeEnd = (endDate == null) || !reservationDate.isAfter(endDate);
                    return afterStart && beforeEnd;
                } catch (Exception e) {
                    System.err.println("Erreur de parsing de date: " + r.getDateheure() + " - " + e.getMessage());
                    return true; // Inclure les réservations avec dates non parsables
                }
            })
            .collect(Collectors.toList());
    }
}
