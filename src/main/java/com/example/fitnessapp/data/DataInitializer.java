package com.example.fitnessapp.data;
import com.example.fitnessapp.model.Equipment;
import com.example.fitnessapp.repository.EquipmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataInitializer implements CommandLineRunner{
    private final EquipmentRepository equipmentRepository;
    public DataInitializer(EquipmentRepository equipmentRepository){
        this.equipmentRepository=equipmentRepository;
    }
    @Override
    public void run(String... args ) throws Exception{
        equipmentRepository.save(new Equipment(null, "Bucice", "slobodni tegovi"));
        equipmentRepository.save(new Equipment(null, "Traka za trcanje", "kardio"));
        equipmentRepository.save(new Equipment(null, "Vratilo", "sipka"));
    }
}
