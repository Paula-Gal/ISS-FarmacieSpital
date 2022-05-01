package app.persistence.repo;

import app.model.Pharmacist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacistRepo extends CrudRepository<Pharmacist, Long> {
    Pharmacist findByEmailAndPassword(String email, String password);
}
