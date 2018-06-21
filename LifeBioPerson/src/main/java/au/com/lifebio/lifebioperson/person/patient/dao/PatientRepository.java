package au.com.lifebio.lifebioperson.person.patient.dao;

import au.com.lifebio.lifebioperson.person.patient.PatientImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Trevor on 2018/06/18.
 */

@Repository
public interface PatientRepository extends JpaRepository<PatientImpl, Long> {
}
