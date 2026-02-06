package com.assignation.controllers;

import com.assignation.models.Reservation;
import com.assignation.services.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final ReservationService reservationService;

    public HomeController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/")
    public String home(
            @RequestParam(required = false) String dateDebut,
            @RequestParam(required = false) String dateFin,
            Model model) {
        
        List<Reservation> reservations = reservationService.getReservationsByDateRange(dateDebut, dateFin);
        model.addAttribute("reservations", reservations);
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);
        return "reservation";
    }
}
