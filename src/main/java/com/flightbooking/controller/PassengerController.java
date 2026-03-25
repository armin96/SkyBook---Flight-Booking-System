package com.flightbooking.controller;

import com.flightbooking.model.Passenger;
import com.flightbooking.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    public String listPassengers(@RequestParam(required = false) String search, Model model) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("passengers", passengerService.searchPassengers(search));
            model.addAttribute("searchTerm", search);
        } else {
            model.addAttribute("passengers", passengerService.getAllPassengers());
        }
        return "passengers/list";
    }

    @GetMapping("/{id}")
    public String passengerDetail(@PathVariable String id, Model model) {
        model.addAttribute("passenger", passengerService.getPassengerById(id));
        return "passengers/detail";
    }

    @GetMapping("/new")
    public String newPassengerForm(Model model) {
        model.addAttribute("passenger", new Passenger());
        return "passengers/form";
    }

    @GetMapping("/{id}/edit")
    public String editPassengerForm(@PathVariable String id, Model model) {
        model.addAttribute("passenger", passengerService.getPassengerById(id));
        return "passengers/form";
    }

    @PostMapping("/save")
    public String savePassenger(@ModelAttribute Passenger passenger,
                                RedirectAttributes redirectAttributes) {
        try {
            if (passenger.getId() != null && !passenger.getId().isEmpty()) {
                passengerService.updatePassenger(passenger.getId(), passenger);
                redirectAttributes.addFlashAttribute("success", "Passenger updated successfully!");
            } else {
                passenger.setId(null);
                passengerService.createPassenger(passenger);
                redirectAttributes.addFlashAttribute("success", "Passenger created successfully!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error saving passenger: " + e.getMessage());
        }
        return "redirect:/passengers";
    }

    @GetMapping("/{id}/delete")
    public String deletePassenger(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            passengerService.deletePassenger(id);
            redirectAttributes.addFlashAttribute("success", "Passenger deleted.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/passengers";
    }
}