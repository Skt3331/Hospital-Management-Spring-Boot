package Repository;

import Entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    List<Hospital> findByHospitalName(String name); // follow field name in entity
    List<Hospital> findByHospitalNameAndHospitalAddress(String hospitalName, String hospitalAddress);

}
