package com.example.fitnessapp.model;

public class Trainer {
    private Long id;
    private String name;
    private int calorieBoost;
    private int staminaBoost;
    public Trainer(){}
    public Trainer(Long id, String name, int calorieBoost, int staminaBoost){
        this.id=id;
        this.name=name;
        this.calorieBoost=calorieBoost;
        this.staminaBoost=staminaBoost;
    }
    public Long getId(){return id;}
    public String name(){return name;}
    public int getCalorieBoost(){return calorieBoost;}
    public int getStaminaBoost(){return staminaBoost;}

    public void setId(Long id){this.id=id;}
    public void setName(String name){this.name=name;}
    public void setCalorieBoost(int calorieBoost){this.calorieBoost=calorieBoost;}
    public void setStaminaBoost(int staminaBoost){this.staminaBoost=staminaBoost;}

    }
