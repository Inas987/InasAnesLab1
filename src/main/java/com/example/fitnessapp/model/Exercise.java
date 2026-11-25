package com.example.fitnessapp.model;

import java.util.ArrayList;
import java.util.List;

public class Exercise {
    private Long id;
    private String name;
    private int caloriesBurned;
    private int stamina;
    private int points;
    private int completedSessions;
    private List<Trainer> trainers = new ArrayList<>();

    public Exercise() {}

    public Exercise(Long id, String name, int caloriesBurned, int stamina, int points) {
        this.id = id;
        this.name = name;
        this.caloriesBurned = caloriesBurned;
        this.stamina = stamina;
        this.points = points;
        this.completedSessions = 0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCaloriesBurned() { return caloriesBurned; }
    public void setCaloriesBurned(int caloriesBurned) { this.caloriesBurned = caloriesBurned; }

    public int getStamina() { return stamina; }
    public void setStamina(int stamina) { this.stamina = stamina; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public int getCompletedSessions() { return completedSessions; }
    public void setCompletedSessions(int completedSessions) { this.completedSessions = completedSessions; }

    public List<Trainer> getTrainers() { return trainers; }
    public void setTrainers(List<Trainer> trainers) { this.trainers = trainers; }

    public int totalCaloriesBurned() {
        int boost = trainers.stream().mapToInt(Trainer::getCalorieBoost).sum();
        return caloriesBurned + boost;
    }

    public int totalStamina() {
        int boost = trainers.stream().mapToInt(Trainer::getStaminaBoost).sum();
        return Math.min(stamina + boost, 5);
    }

    public boolean canAddTrainer() {
        return trainers.size() < 3;
    }

    public void addTrainer(Trainer t) {
        if (canAddTrainer()) {
            trainers.add(t);
        }
    }
}