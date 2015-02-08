package org.jugbd.mnet.service;

import org.jugbd.mnet.dao.PatientDao;
import org.jugbd.mnet.dao.RegisterDao;
import org.jugbd.mnet.dao.VitalDao;
import org.jugbd.mnet.domain.Register;
import org.jugbd.mnet.domain.Vital;
import org.jugbd.mnet.domain.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author Bazlur Rahman Rokon
 * @since 10/14/14.
 */
@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {

    private static final Logger log = LoggerFactory.getLogger(RegisterService.class);

    @Autowired
    private RegisterDao registerDao;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private VitalDao vitalDao;

    @Override
    public void save(Register register) {
        register.setPatient(patientDao.findOne(register.getPatient().getId()));
        register.setStartDatetime(new Date());
        register.setStatus(Status.ACTIVE);
        registerDao.save(register);
    }

    @Override
    public Register findOne(Long registerId) {

        Register register = registerDao.findOne(registerId);
        initializeRegister(register);

        return register;
    }

    @Override
    public Register findActiveRegisterByPatientId(Long patientId) {

        List<Register> registers = registerDao.findActiveRegisterByPatientId(patientId);
        if (registers != null && registers.size() > 0) {
            initializeRegister(registers.get(0));

            return registers.get(0);
        }

        return null;
    }

    @Override
    public List<Register> findAllRegisterByPatientId(Long patientId) {

        return registerDao.findAllRegisterByPatientId(patientId);
    }

    private void initializeRegister(Register register) {
        // Ref: http://stackoverflow.com/questions/19928568/hibernate-best-practice-to-pull-all-lazy-collections
        register.getVitals().size();
        register.getOperationalDetails().size();
        register.getInvestigation().size();
    }

    @Override
    public void closeRegister(Long registerId) {
        Register register = registerDao.findOne(registerId);
        register.setStatus(Status.CLOSED);
        register.setStopDatetime(new Date());
        registerDao.save(register);
    }

    @Override
    public void update(Register register) {

        registerDao.save(register);
    }

    @Override
    public void addVital(Vital vital, Long registerId) {

    }
}
