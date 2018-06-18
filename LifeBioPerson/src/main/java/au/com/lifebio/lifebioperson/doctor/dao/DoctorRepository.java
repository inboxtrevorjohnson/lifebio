package au.com.lifebio.lifebioperson.doctor.dao;

import au.com.lifebio.lifebioperson.person.doctor.Doctor;
import au.com.lifebio.lifebioperson.person.doctor.DoctorImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Trevor on 2018/06/18.
 */

@Repository
public interface DoctorRepository extends JpaRepository<DoctorImpl, Long> {

    Doctor findByPractiseNumber(String practiseNumber);
}
