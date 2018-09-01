package au.com.lifebio.lifebioperson.person.patient.service;

import au.com.lifebio.lifebiocommon.common.exception.ConflictException;
import au.com.lifebio.lifebiocommon.common.exception.TypeNotSupportedException;
import au.com.lifebio.lifebioperson.person.patient.Patient;
import au.com.lifebio.lifebioperson.person.patient.PatientImpl;
import au.com.lifebio.lifebioperson.person.patient.dao.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Trevor on 2018/06/18.
 */

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    @PreAuthorize("hasAuthority('PATIENT_CREATE')")
    public Optional<Patient> addPatient(@NotNull(message = "Cannot add null patient.") Patient patient) {
        patient.setLastModified(LocalDateTime.now());
        return Optional.of(patientRepository.save((PatientImpl)patient));
    }

    @Override
    @PreAuthorize("hasAuthority('PATIENT_UPDATE')")
    public Optional<Patient> changePatient(@NotNull(message = "Cannot change null patient.")Patient patient) {
        if(!patient.getLastModified().equals(patientRepository.findById(patient.getOID()).orElseThrow(
                () -> new ResourceNotFoundException("Cannot change patient, cannot find patient!") )
                .getLastModified())){
            throw new ConflictException("Cannot change patient, it has been modified by someone else!");
        }
        if(patient instanceof PatientImpl) {
            patient.setLastModified(LocalDateTime.now());
            return Optional.of(patientRepository.save((PatientImpl)patient));
        }
        throw new TypeNotSupportedException("Patient is not a supported type!");
    }

    @Override
    @PreAuthorize("hasAuthority('PATIENT_READ')")
    public Optional<Patient> findPatient(@NotNull(message = "Cannot find patient with a null OID.") Long oID) {
        Optional<Patient> optional = Optional.of(patientRepository.findById(oID).orElseThrow(
                () -> new ResourceNotFoundException("Unable to find patient by OID")
        ));
        return optional;
    }

    @Override
    @PreAuthorize("hasAuthority('PATIENT_READ')")
    public Optional<Set<Patient>> findAll() {
        return Optional.of(new HashSet<Patient>(patientRepository.findAll()));
    }

    @Override
    @PreAuthorize("hasAuthority('PATIENT_DELETE')")
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
