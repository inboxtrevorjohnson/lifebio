package au.com.lifebio.lifebioperson.person.patient.service;

import au.com.lifebio.lifebioperson.person.patient.Patient;

import java.util.Optional;
import java.util.Set;

/**
 * Created by Trevor on 2018/06/21.
 */

public interface PatientService {

    Optional<Patient> addPatient(Patient person);

    Optional<Patient> changePatient(Patient person);

    Optional<Patient> findPatient(Long oID);

    Optional<Set<Patient>> findAll();

    void deletePatient(Patient person);

}
