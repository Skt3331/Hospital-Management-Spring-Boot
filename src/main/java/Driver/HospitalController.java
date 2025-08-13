package Driver;

import Entity.Hospital;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class HospitalController {

    private HospitalDao hospitalDao;

    HospitalController(HospitalDao hospitalDao) {
        this.hospitalDao = hospitalDao;
    }

   @GetMapping("/getbyid")
    public ResponseEntity<Hospital> getHospital(@RequestParam("id") Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        Hospital hospital = hospitalDao.getHospital(id);
        if (hospital == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hospital);
    }

    @GetMapping("/findall")
    public List<Hospital> getAllHospitals() {
        return hospitalDao.getAllHospitals();
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insertHospital(@Valid @RequestBody Hospital hospital) {
        if (hospital == null) {
            return ResponseEntity.badRequest().body("Hospital object cannot be null");
        }
        
        try {
            Hospital savedHospital = hospitalDao.insertHospital(hospital);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedHospital);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error saving hospital: " + e.getMessage());
        }
    }
    
    @PutMapping("/update")
    public ResponseEntity<?> updateHospital(@Valid @RequestBody Hospital hospital) {
        if (hospital == null) {
            return ResponseEntity.badRequest().body("Hospital object cannot be null");
        }
        
        if (hospital.getId() == null) {
            return ResponseEntity.badRequest().body("Hospital ID is required for update");
        }
        
        try {
            hospitalDao.updateHospital(hospital);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating hospital: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletebyid")
    public ResponseEntity<?> deleteHospital(@RequestParam("id") Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body("ID cannot be null");
        }
        
        try {
            hospitalDao.deleteHospital(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting hospital: " + e.getMessage());
        }
    }

    // Exception handler for validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

}
