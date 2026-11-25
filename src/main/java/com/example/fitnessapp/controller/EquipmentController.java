package com.example.fitnessapp.controller;

import com.example.fitnessapp.model.Equipment;
import com.example.fitnessapp.model.Exercise;
import com.example.fitnessapp.repository.EquipmentRepository;
import com.example.fitnessapp.repository.ExerciseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/equipment")
public class EquipmentController {

    private final EquipmentRepository equipmentRepository;
    private final ExerciseRepository exerciseRepository;

    public EquipmentController(EquipmentRepository equipmentRepository,
                               ExerciseRepository exerciseRepository) {
        this.equipmentRepository = equipmentRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @GetMapping
    public String listEquipment(Model model) {
        model.addAttribute("equipmentList", equipmentRepository.findAll());
        model.addAttribute("exercises", exerciseRepository.findAll());
        model.addAttribute("newEquipment", new Equipment());
        return "equipment";
    }

    @PostMapping("/add")
    public String addEquipment(@ModelAttribute Equipment equipment,
                               @RequestParam Long exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId).orElse(null);
        equipment.setExercise(exercise);
        equipmentRepository.save(equipment);
        return "redirect:/equipment";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Neispravan ID opreme: " + id));
        model.addAttribute("equipment", equipment);
        model.addAttribute("exercises", exerciseRepository.findAll());
        return "equipment-edit";
    }

    @PostMapping("/update/{id}")
    public String updateEquipment(@PathVariable Long id,
                                  @ModelAttribute Equipment updatedEquipment,
                                  @RequestParam Long exerciseId) {
        Equipment existing = equipmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Neispravan ID opreme: " + id));
        Exercise exercise = exerciseRepository.findById(exerciseId).orElse(null);

        existing.setName(updatedEquipment.getName());
        existing.setCategory(updatedEquipment.getCategory());
        existing.setWeightKg(updatedEquipment.getWeightKg());
        existing.setExercise(exercise);

        equipmentRepository.save(existing);
        return "redirect:/equipment";
    }

    @PostMapping("/delete/{id}")
    public String deleteEquipment(@PathVariable Long id) {
        equipmentRepository.deleteById(id);
        return "redirect:/equipment";
    }
}