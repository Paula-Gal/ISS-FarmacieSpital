package app.persistence.repo;

import app.model.Doctor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepo extends CrudRepository<Doctor, Long> {
    Doctor findByEmailAndPassword(String email, String password);
}
