package org.jugbd.mnet.service;

import org.jugbd.mnet.dao.ExaminationDao;
import org.jugbd.mnet.domain.Examination;
import org.jugbd.mnet.domain.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Bazlur Rahman Rokon
 * @date 11/29/2014.
 */
@Service
@Transactional
public class ExaminationServiceImpl implements ExaminationService {

    @Autowired
    private ExaminationDao examinationDao;

    @Autowired
    private RegisterService registerService;

    @Override
    public Examination save(Examination examination) {
        if (examination.getId() == null) {
            Register register = registerService.findOne(examination.getRegister().getId());
            register.setExamination(examination);

            registerService.save(register);
            examination.setRegister(register);

            return examinationDao.save(examination);
        } else {
            Examination examinationFromDb = examinationDao.findOne(examination.getId());

            examinationFromDb.setJaundice(examination.getJaundice());
            examinationFromDb.setAnaemia(examination.getAnaemia());
            examinationFromDb.setAccessibleLymphNode(examination.getAccessibleLymphNode());
            examinationFromDb.setDehydration(examination.getDehydration());
            examinationFromDb.setOelema(examination.getOelema());
            examinationFromDb.setNeckVein(examination.getNeckVein());

            examinationFromDb.setListeningExamination(examination.getListeningExamination());
            examinationFromDb.setRespiratorySystem(examination.getRespiratorySystem());
            examinationFromDb.setgISystem(examination.getgISystem());
            examinationFromDb.setCardiovascularSystem(examination.getCardiovascularSystem());
            examinationFromDb.setUrogenitalSystem(examination.getUrogenitalSystem());
            examinationFromDb.setNervousSystem(examination.getNervousSystem());

            return examinationDao.save(examinationFromDb);
        }
    }

    @Override
    public Examination findOne(Long id) {

        return examinationDao.findOne(id);
    }
}
