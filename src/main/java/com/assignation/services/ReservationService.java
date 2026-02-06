package com.assignation.services;

import com.assignation.models.ApiResponse;
import com.assignation.models.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final RestTemplate restTemplate;

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
        
        return allReservations.stream()
            .filter(r -> {
                String dateReservation = r.getDateheure().substring(0, 10); // Extraire YYYY-MM-DD
                boolean afterStart = (dateDebut == null || dateDebut.isEmpty()) || dateReservation.compareTo(dateDebut) >= 0;
                boolean beforeEnd = (dateFin == null || dateFin.isEmpty()) || dateReservation.compareTo(dateFin) <= 0;
                return afterStart && beforeEnd;
            })
            .collect(Collectors.toList());
    }
}
