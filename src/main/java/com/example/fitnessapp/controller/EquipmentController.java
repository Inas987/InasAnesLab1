package com.example.fitnessapp.controller;

import com.example.fitnessapp.model.Equipment;
import com.example.fitnessapp.repository.EquipmentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/equipment")
public class EquipmentController {

    private final EquipmentRepository repository;

    public EquipmentController(EquipmentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listEquipment(Model model) {
        List<Equipment> list = repository.findAll();
        if (list == null) {
            list = new ArrayList<>();
        }
        model.addAttribute("equipments", list);
        return "equipment";
    }

    @PostMapping("/add")
    public String addEquipment(@ModelAttribute Equipment equipment) {
        repository.save(equipment);
        return "redirect:/equipment";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Equipment eq = repository.findById(id).orElseThrow();
        model.addAttribute("equipment", eq);
        return "edit-equipment";
    }

    @PostMapping("/update/{id}")
    public String updateEquipment(@PathVariable Long id, @ModelAttribute Equipment updatedEquipment) {
        Equipment eq = repository.findById(id).orElseThrow();
        eq.setName(updatedEquipment.getName());
        eq.setCategory(updatedEquipment.getCategory());
        repository.save(eq);
        return "redirect:/equipment";
    }
}