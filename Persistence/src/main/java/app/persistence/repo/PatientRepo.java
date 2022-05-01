package app.persistence.repo;

import app.model.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepo extends CrudRepository<Patient, Long> {
    Patient findByEmailAndPassword(String email, String password);
}
