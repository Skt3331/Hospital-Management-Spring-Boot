package Repository;

import Entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    List<Hospital> findByHospitalName(String name); // follow field name in entity
    List<Hospital> findByHospitalNameAndHospitalAddress(String hospitalName, String hospitalAddress);
     @Query("SELECT h from Hospital h where h.hospitalName=?1 and h.hospitalAddress=?2")
    List<Hospital> findByNL(String hospitalName, String hospitalAddress);

}
