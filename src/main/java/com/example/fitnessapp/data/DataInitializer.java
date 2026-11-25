package com.example.fitnessapp.data;

import com.example.fitnessapp.model.Equipment;
import com.example.fitnessapp.model.Exercise;
import com.example.fitnessapp.model.Trainer;
import com.example.fitnessapp.repository.EquipmentRepository;
import com.example.fitnessapp.repository.ExerciseRepository;
import com.example.fitnessapp.repository.TrainerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TrainerRepository trainerRepository;
    private final ExerciseRepository exerciseRepository;
    private final EquipmentRepository equipmentRepository;

    public DataInitializer(TrainerRepository trainerRepository,
                           ExerciseRepository exerciseRepository,
                           EquipmentRepository equipmentRepository) {
        this.trainerRepository = trainerRepository;
        this.exerciseRepository = exerciseRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public void run(String... args) {
        if (trainerRepository.count() == 0) {
            trainerRepository.save(new Trainer(null, "Trener Marko", 30, 0));
            trainerRepository.save(new Trainer(null, "Trenerica Ana", 10, 1));
            trainerRepository.save(new Trainer(null, "Trener Denis", 20, 0));
            trainerRepository.save(new Trainer(null, "Trenerica Ema", 15, 1));
        }

        if (exerciseRepository.count() == 0) {
            Exercise cucnjevi = exerciseRepository.save(new Exercise(null, "Cucnjevi", 50, 5, 0));
            Exercise trcanje = exerciseRepository.save(new Exercise(null, "Trcanje", 70, 5, 0));
            Exercise sklekovi = exerciseRepository.save(new Exercise(null, "Sklekovi", 40, 5, 0));

            if (equipmentRepository.count() == 0) {
                equipmentRepository.save(new Equipment("Olimpijska sipka", "Tegovi", 20.0, cucnjevi));
                equipmentRepository.save(new Equipment("Traka za trcanje", "Kardio", 85.0, trcanje));
                equipmentRepository.save(new Equipment("Strunjaca", "Podloga", 1.5, sklekovi));
            }
        }
    }
}