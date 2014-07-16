package org.jugbd.mnet.service;

import org.jugbd.mnet.dao.PatientDao;
import org.jugbd.mnet.domain.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author ronygomes
 */

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

    @Autowired
    private PatientDao patientDao;

    @Override
    public Patient create(Patient patient) {
        return patientDao.save(patient);
    }

    @Override
    public Patient findOne(Long id) {
        return patientDao.findOne(id);
    }

    @Override
    public List<Patient> findAll() {
        return patientDao.findAll();
    }
}
