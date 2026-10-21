package com.example.fitnessapp.service;


import org.springframework.stereotype.Service;
import com.example.fitnessapp.model.Exercise;

import java.util.Random;

@Service
public class WorkoutService {
    private final Random rnd = new Random();

    public Result executeWorkout(Exercise e) {
        int sessionLevel = e.getCompletedSessions();
        int targetIntensity = Math.max(10, (sessionLevel * 10) + rnd.nextInt(20));
        int totalOutput = e.totalCaloriesBurned();
        int delta = totalOutput - targetIntensity;

        String outcome;
        int gainedPoints = 0;

        if (delta > 0) {
            gainedPoints = 5 + Math.max(0, delta / 5);
            e.setPoints(e.getPoints() + gainedPoints);
            e.setCaloriesBurned(e.getCaloriesBurned() + 2);
            outcome = "USPJEŠAN TRENING";
        } else if (delta == 0) {
            gainedPoints = 2;
            e.setPoints(e.getPoints() + gainedPoints);
            outcome = "PROSJEČAN TRENING";
        } else {
            e.setStamina(Math.max(0, e.getStamina() - 1));
            outcome = "ISCRPLJUJUĆI TRENING";
        }

        e.setCompletedSessions(sessionLevel + 1);
        return new Result(outcome, targetIntensity, gainedPoints, e.getCompletedSessions());
    }

    public static class Result {
        public final String outcome;
        public final int targetIntensity;
        public final int gainedPoints;
        public final int newSessionLevel;

        public Result(String outcome, int targetIntensity, int gainedPoints, int newSessionLevel) {
            this.outcome = outcome;
            this.targetIntensity = targetIntensity;
            this.gainedPoints = gainedPoints;
            this.newSessionLevel = newSessionLevel;
        }
    }
}