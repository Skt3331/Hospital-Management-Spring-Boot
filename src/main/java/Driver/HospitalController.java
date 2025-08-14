package Driver;

import Entity.Hospital;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import Responce.ResponseStructure;

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
    @GetMapping("/getbyidr")
    public ResponseStructure<Hospital> getHospitalr(@RequestParam("id") Long id) {
        ResponseStructure<Hospital> response = new ResponseStructure<>();
        if (id == null) {
            response.setMessage("id is null");
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setData(null);
            return response;

        }
        Hospital hospital = hospitalDao.getHospital(id);
        if (hospital == null) {
            response.setMessage("hospital is null");
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setData(null);
            return response;

        }
        response.setData(hospital);
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("success");


        return response;
    }



    @GetMapping("/getbyNameAndAddress")
    public ResponseEntity<List<Hospital>> getHospitalsByNameAndAdress(@RequestParam("hospitalName") String name, @RequestParam("hospitalAddress") String adress)
    {
        if (name == null || adress == null) {
            return ResponseEntity.badRequest().build();
        }
        List<Hospital> listHospital = hospitalDao.getHospitalByNameAndAddress(adress, name);
        return ResponseEntity.ok(listHospital);

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
    @GetMapping("/findall/{page}/{size}")
    public ResponseEntity<Page<Hospital>> getHospitals(@PathVariable int page, @PathVariable int size) {
        if (size <= 0) {
            return ResponseEntity.badRequest().build();
        }
        try {
            PageRequest pagable=PageRequest.of(page, size);
           return ResponseEntity.ok(hospitalDao.getallpage(pagable));

        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
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
