package au.com.lifebio.lifebioperson.person.doctor.service;

import au.com.lifebio.lifebiocommon.common.exception.ConflictException;
import au.com.lifebio.lifebiocommon.common.exception.TypeNotSupportedException;
import au.com.lifebio.lifebioperson.person.doctor.Doctor;
import au.com.lifebio.lifebioperson.person.doctor.DoctorImpl;
import au.com.lifebio.lifebioperson.person.doctor.dao.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
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
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    @PreAuthorize("hasAuthority('DOCTOR_CREATE')")
    public Optional<Doctor> addDoctor(@NotNull(message = "Cannot add null doctor.") Doctor doctor) {
        doctor.setLastModified(LocalDateTime.now());
        return Optional.of(doctorRepository.save((DoctorImpl)doctor));
    }

    @Override
    @PreAuthorize("hasAuthority('DOCTOR_UPDATE')")
    public Optional<Doctor> changeDoctor(@NotNull(message = "Cannot change null doctor.") Doctor doctor) {
        if(!doctor.getLastModified().equals(doctorRepository.findById(doctor.getOID()).orElseThrow(
                () -> new ResourceNotFoundException("Cannot change doctor, cannot find doctor!") )
                .getLastModified())){
            throw new ConflictException("Cannot change doctor, it has been modified by someone else!");
        }
        if(doctor instanceof DoctorImpl) {
            doctor.setLastModified(LocalDateTime.now());
            return Optional.of(doctorRepository.save((DoctorImpl)doctor));
        }
        throw new TypeNotSupportedException("Doctor is not a supported type!");
    }

    @Override
    @PreAuthorize("hasAuthority('DOCTOR_READ')")
    public Optional<Doctor> findDoctorByPractiseNumber(
            @NotEmpty(message = "Cannot find doctor with an empty practise number.") String practiseNumber) {
        Optional<Doctor> optional = Optional.of(doctorRepository.findByPractiseNumber(practiseNumber)
                .orElseThrow(
                () -> new ResourceNotFoundException("Unable to find doctor by practise number")
        ));
        return optional;
    }

    @Override
    @PreAuthorize("hasAuthority('DOCTOR_READ')")
    public Optional<Doctor> findDoctor(@NotNull(message = "Cannot find doctor with a null OID.") Long oID) {
        Optional<Doctor> optional = Optional.of(doctorRepository.findById(oID).orElseThrow(
                () -> new ResourceNotFoundException("Unable to find doctor by OID")
        ));
        return optional;
    }

    @Override
    @PreAuthorize("hasAuthority('DOCTOR_READ')")
    public Optional<Set<Doctor>> findAll() {
        return Optional.of(new HashSet<Doctor>(doctorRepository.findAll()));
    }

    @Override
    @PreAuthorize("hasAuthority('DOCTOR_READ')")
    public Optional<Set<Doctor>> findBySurnameLike(@NotEmpty(message = "Cannot find doctor with an empty surname.")
            String surnameLike) {
        return Optional.of(doctorRepository.findBySurnameLike(surnameLike));
    }

    @Override
    @PreAuthorize("hasAuthority('DOCTOR_DELETE')")
    public void deleteDoctor(@NotNull(message = "Cannot delete doctor, a valid doctor must be specified.")
                                         Doctor doctor) {
        if(doctor instanceof DoctorImpl) {
            doctorRepository.delete((DoctorImpl) doctor);
        }
        else {
            throw new TypeNotSupportedException("Doctor is not a supported type!");
        }

    }

}
