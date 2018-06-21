package au.com.lifebio.lifebioperson.person.doctor.dao;

import au.com.lifebio.lifebioperson.person.doctor.Doctor;
import au.com.lifebio.lifebioperson.person.doctor.DoctorImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * Created by Trevor on 2018/06/18.
 */

@Repository
public interface DoctorRepository extends JpaRepository<DoctorImpl, Long> {

    Optional<Doctor> findByPractiseNumber(String practiseNumber);

    Set<Doctor> findBySurnameLike(String surnameLike);
}
