package com.example.fitnessapp.data;

import org.springframework.stereotype.Component;
import com.example.fitnessapp.model.Exercise;
import com.example.fitnessapp.model.Trainer;

import java.util.*;

@Component
public class FitnessData {
    private final Map<Long, Exercise> exercises = new LinkedHashMap<>();
    private final Map<Long, Trainer> trainers = new LinkedHashMap<>();
    private long exerciseSeq = 1;
    private long trainerSeq = 1;

    public FitnessData() {
        saveTrainer(new Trainer(null, "Trener Marko", 30, 0));
        saveTrainer(new Trainer(null, "Trenerica Ana", 10, 1));
        saveTrainer(new Trainer(null, "Trener Denis", 20, 0));
        saveTrainer(new Trainer(null, "Trenerica Ema", 15, 1));

        saveExercise(new Exercise(null, "Cucnjevi", 50, 5, 0));
        saveExercise(new Exercise(null, "Trcanje", 70, 5, 0));
        saveExercise(new Exercise(null, "Sklekovi", 40, 5, 0));
    }

    public List<Exercise> findAllExercises() {
        return new ArrayList<>(exercises.values());
    }

    public Exercise findExercise(Long id) {
        return exercises.get(id);
    }

    public Exercise saveExercise(Exercise e) {
        if (e.getId() == null) e.setId(exerciseSeq++);
        exercises.put(e.getId(), e);
        return e;
    }

    public List<Trainer> findAllTools() {
        return new ArrayList<>(trainers.values());
    }

    public Trainer findTrainer(Long id) {
        return trainers.get(id);
    }

    public Trainer saveTrainer(Trainer t) {
        if (t.getId() == null) t.setId(trainerSeq++);
        trainers.put(t.getId(), t);
        return t;
    }

    public boolean hireTrainer(Long exerciseId, Long trainerId, int pricePoints) {
        Exercise e = findExercise(exerciseId);
        Trainer t = findTrainer(trainerId);
        if (e == null || t == null) return false;
        if (!e.canAddTrainer()) return false;
        if (e.getPoints() < pricePoints) return false;

        e.setPoints(e.getPoints() - pricePoints);
        e.addTrainer(t);
        return true;
    }
}