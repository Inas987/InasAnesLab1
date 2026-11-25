package com.example.fitnessapp.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.fitnessapp.data.FitnessData;
import com.example.fitnessapp.model.Exercise;
import com.example.fitnessapp.service.WorkoutService;

@Controller
public class FitnessController {

    private final FitnessData data;
    private final WorkoutService workoutService;

    public FitnessController(FitnessData data, WorkoutService workoutService) {
        this.data = data;
        this.workoutService = workoutService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/exercises";
    }

    @GetMapping("/exercises")
    public String exercises(Model model) {
        model.addAttribute("exercises", data.findAllExercises());
        return "exercises";
    }

    @GetMapping("/trainers")
    public String trainers(Model model) {
        model.addAttribute("trainers", data.findAllTools());
        model.addAttribute("exercises", data.findAllExercises());
        return "trainers";
    }

    @PostMapping("/trainers/hire")
    public String hireTrainer(@RequestParam Long exerciseId, @RequestParam Long trainerId) {
        data.hireTrainer(exerciseId, trainerId, 3);
        return "redirect:/trainers";
    }

    @GetMapping("/workout")
    public String workout(Model model, @RequestParam(required = false) Long exerciseId) {
        model.addAttribute("exercises", data.findAllExercises());
        model.addAttribute("selectedId", exerciseId);
        return "workout";
    }

    @PostMapping("/workout/start")
    public String startWorkout(@RequestParam Long exerciseId, Model model) {
        Exercise e = data.findExercise(exerciseId);
        WorkoutService.Result result = workoutService.executeWorkout(e);

        model.addAttribute("exercises", data.findAllExercises());
        model.addAttribute("selectedId", exerciseId);
        model.addAttribute("exercise", e);
        model.addAttribute("result", result);
        return "workout";
    }
}