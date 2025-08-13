package Driver;

import Entity.Hospital;
import org.springframework.stereotype.Repository;
import Repository.HospitalRepository;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

@Repository
public class HospitalDao {

    private final HospitalRepository repo;
    public HospitalDao(HospitalRepository repo) {
        this.repo = repo;
    }

    public Hospital getHospital(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Hospital ID cannot be null");
        }
        return repo.findById(id).orElse(null);
    }
    
    public List<Hospital> getAllHospitals() {
        return repo.findAll();
    }

    public Hospital insertHospital(Hospital hospital) {
        if (hospital == null) {
            throw new IllegalArgumentException("Hospital object cannot be null");
        }
        
        // Validate required fields
        if (hospital.getHospitalName() == null || hospital.getHospitalName().trim().isEmpty()) {
            throw new IllegalArgumentException("Hospital name is required");
        }
        if (hospital.getHospitalAddress() == null || hospital.getHospitalAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Hospital address is required");
        }
        if (hospital.getHospitalCity() == null || hospital.getHospitalCity().trim().isEmpty()) {
            throw new IllegalArgumentException("Hospital city is required");
        }
        if (hospital.getHospitalState() == null || hospital.getHospitalState().trim().isEmpty()) {
            throw new IllegalArgumentException("Hospital state is required");
        }
        
        try {
            return repo.save(hospital);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Data integrity violation: " + e.getMessage());
        }
    }
    
    public void updateHospital(Hospital hospital) {
        if (hospital == null) {
            throw new IllegalArgumentException("Hospital object cannot be null");
        }
        
        if (hospital.getId() == null) {
            throw new IllegalArgumentException("Hospital ID is required for update");
        }
        
        // Check if hospital exists
        if (!repo.existsById(hospital.getId())) {
            throw new IllegalArgumentException("Hospital with ID " + hospital.getId() + " not found");
        }
        
        // Validate required fields
        if (hospital.getHospitalName() == null || hospital.getHospitalName().trim().isEmpty()) {
            throw new IllegalArgumentException("Hospital name is required");
        }
        if (hospital.getHospitalAddress() == null || hospital.getHospitalAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Hospital address is required");
        }
        if (hospital.getHospitalCity() == null || hospital.getHospitalCity().trim().isEmpty()) {
            throw new IllegalArgumentException("Hospital city is required");
        }
        if (hospital.getHospitalState() == null || hospital.getHospitalState().trim().isEmpty()) {
            throw new IllegalArgumentException("Hospital state is required");
        }
        
        try {
            repo.save(hospital);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Data integrity violation: " + e.getMessage());
        }
    }
    
    public void deleteHospital(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Hospital ID cannot be null");
        }
        
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Hospital with ID " + id + " not found");
        }
        
        repo.deleteById(id);
    }
}
