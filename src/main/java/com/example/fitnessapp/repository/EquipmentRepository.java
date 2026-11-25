package com.example.fitnessapp.repository;

import com.example.fitnessapp.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
