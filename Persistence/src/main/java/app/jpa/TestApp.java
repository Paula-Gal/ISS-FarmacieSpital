package app.jpa;

import app.model.Doctor;
import app.persistence.repo.DoctorRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import app.persistence.repo.UserRepo;
import app.persistence.repo.PharmacistRepo;
import app.persistence.repo.PatientRepo;

@SpringBootApplication
@ComponentScan("app")
@EnableJpaRepositories("app")
@EntityScan("app")
public class TestApp {
    public static void main(String[] args) {
        SpringApplication.run(TestApp.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            UserRepo userRepo,
            DoctorRepo doctorRepo,
            PharmacistRepo pharmacistRepo,
            PatientRepo patientRepo
    ) {
        return args -> {
            //Doctor doctor = doctorRepo.findByEmailAndPassword("ion@gmail.com", "abcd");
            //System.out.println(doctor.toString());
            userRepo.findAll().forEach(user -> System.out.println(user.toString()));
            pharmacistRepo.findAll().forEach(pharmacist -> System.out.println(pharmacist.toString()));
            patientRepo.findAll().forEach(patient -> System.out.println(patient.toString()));
            doctorRepo.save(new Doctor("Ioana", "Irina", "ioana@gmail.com", "abcd"));
            doctorRepo.findAll().forEach(doctor1 -> System.out.println(doctor1.toString()));
        };
    }
}
