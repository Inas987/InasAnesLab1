package com.example.fitnessapp.controller;

import com.example.fitnessapp.model.Exercise;
import com.example.fitnessapp.model.Trainer;
import com.example.fitnessapp.repository.ExerciseRepository;
import com.example.fitnessapp.repository.TrainerRepository;
import com.example.fitnessapp.service.WorkoutService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FitnessController {

    private final ExerciseRepository exerciseRepository;
    private final TrainerRepository trainerRepository;
    private final WorkoutService workoutService;

    public FitnessController(ExerciseRepository exerciseRepository,
                             TrainerRepository trainerRepository,
                             WorkoutService workoutService) {
        this.exerciseRepository = exerciseRepository;
        this.trainerRepository = trainerRepository;
        this.workoutService = workoutService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/exercises";
    }

    @GetMapping("/exercises")
    public String exercises(Model model) {
        model.addAttribute("exercises", exerciseRepository.findAll());
        return "exercises";
    }

    @GetMapping("/trainers")
    public String trainers(Model model) {
        model.addAttribute("trainers", trainerRepository.findAll());
        model.addAttribute("exercises", exerciseRepository.findAll());
        return "trainers";
    }

    @PostMapping("/trainers/hire")
    public String hireTrainer(@RequestParam Long exerciseId, @RequestParam Long trainerId) {
        Exercise e = exerciseRepository.findById(exerciseId).orElse(null);
        Trainer t = trainerRepository.findById(trainerId).orElse(null);

        if (e != null && t != null && e.canAddTrainer() && e.getPoints() >= 3) {
            e.setPoints(e.getPoints() - 3);
            e.addTrainer(t);
            exerciseRepository.save(e);
        }
        return "redirect:/trainers";
    }

    @GetMapping("/workout")
    public String workout(Model model, @RequestParam(required = false) Long exerciseId) {
        model.addAttribute("exercises", exerciseRepository.findAll());
        model.addAttribute("selectedId", exerciseId);
        return "workout";
    }

    @PostMapping("/workout/start")
    public String startWorkout(@RequestParam Long exerciseId, Model model) {
        Exercise e = exerciseRepository.findById(exerciseId).orElse(null);
        if (e != null) {
            WorkoutService.Result result = workoutService.executeWorkout(e);
            model.addAttribute("exercise", e);
            model.addAttribute("result", result);
        }
        model.addAttribute("exercises", exerciseRepository.findAll());
        model.addAttribute("selectedId", exerciseId);
        return "workout";
    }
}