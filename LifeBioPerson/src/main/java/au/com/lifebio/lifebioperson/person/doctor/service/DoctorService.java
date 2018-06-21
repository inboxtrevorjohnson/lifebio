package au.com.lifebio.lifebioperson.person.doctor.service;

import au.com.lifebio.lifebioperson.person.doctor.Doctor;

import java.util.Optional;
import java.util.Set;

/**
 * Created by Trevor on 2018/06/21.
 */

public interface DoctorService {

    Optional<Doctor> addDoctor(Doctor person);

    Optional<Doctor> changeDoctor(Doctor person);

    Optional<Doctor> findDoctor(Long oID);

    Optional<Doctor> findDoctorByPractiseNumber(String practiseNumber);

    Optional<Set<Doctor>> findAll();

    Optional<Set<Doctor>> findBySurnameLike(String surnameLike);

    void deleteDoctor(Doctor person);

}
