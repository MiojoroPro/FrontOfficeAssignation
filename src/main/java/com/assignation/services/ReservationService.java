package com.assignation.services;

import com.assignation.models.ApiResponse;
import com.assignation.models.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final RestTemplate restTemplate;
    
    // Format de date du backend: "Feb 6, 2026, 3:19:00 PM"
    private final DateTimeFormatter backendFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

    @Value("${api.reservations.url}")
    private String apiUrl;

    public ReservationService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Reservation> getAllReservations() {
        try {
            ResponseEntity<ApiResponse<Reservation>> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ApiResponse<Reservation>>() {}
            );
            ApiResponse<Reservation> apiResponse = response.getBody();
            if (apiResponse != null && apiResponse.getData() != null) {
                return apiResponse.getData();
            }
            return Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'appel API: " + e.getMessage());
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
