package app.persistence.repo;

import app.model.Medication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepo extends CrudRepository<Medication, Long> {
    Medication getMedicationById(Long id);
}
