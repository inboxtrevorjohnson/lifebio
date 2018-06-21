package au.com.lifebio.lifebioperson.person.patient.service;

import au.com.lifebio.lifebioperson.exception.TypeNotSupportedException;
import au.com.lifebio.lifebioperson.person.patient.Patient;
import au.com.lifebio.lifebioperson.person.patient.PatientImpl;
import au.com.lifebio.lifebioperson.person.patient.dao.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Trevor on 2018/06/18.
 */

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Optional<Patient> addPatient(@NotNull(message = "Cannot add null patient.") Patient patient) {
        patient.setLastModified(LocalDateTime.now());
        return Optional.of(patientRepository.save((PatientImpl)patient));
    }

    @Override
    public Optional<Patient> changePatient(@NotNull(message = "Cannot change null patient.")Patient patient) {
        if(patient instanceof PatientImpl) {
            patient.setLastModified(LocalDateTime.now());
            return Optional.of(patientRepository.save((PatientImpl)patient));
        }
        throw new TypeNotSupportedException("Patient is not a supported type!");
    }

    @Override
    public Optional<Patient> findPatient(@NotNull(message = "Cannot find patient with a null OID.") Long oID) {
        Optional<Patient> optional = Optional.of(patientRepository.findById(oID).orElseThrow(
                () -> new ResourceNotFoundException("Unable to find patient by OID")
        ));
        return optional;
    }

    @Override
    public Optional<Set<Patient>> findAll() {
        return Optional.of(new HashSet<Patient>(patientRepository.findAll()));
    }

    @Override
    public void deletePatient(@NotNull(message = "Cannot delete patient, a valid patient must be specified.")
                                         Patient patient) {
        if(patient instanceof PatientImpl) {
            patientRepository.delete((PatientImpl) patient);
        }
        else {
            throw new TypeNotSupportedException("Patient is not a supported type!");
        }

    }

}
