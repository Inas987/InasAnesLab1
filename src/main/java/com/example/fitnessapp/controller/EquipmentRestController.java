package com.example.fitnessapp.controller;
import com.example.fitnessapp.model.Equipment;
import com.example.fitnessapp.repository.EquipmentRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentRestController {
    private final EquipmentRepository repository;
    public EquipmentRestController(EquipmentRepository repository){
        this.repository=repository;

    }
    @GetMapping
    public List<Equipment> getAll(){

        return repository.findAll();

    }
}
