package org.jugbd.mnet.dao;

import org.jugbd.mnet.domain.Diagnosis;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Mushfekur Rahman (mushfek0001)
 */
@Component
public interface DiagnosisDao extends GenericDao<Diagnosis, Long> {
    void saveDiagnosis(Diagnosis diagnosis);

    Diagnosis getDiagnosis(Long diagnosisId);

    List<Diagnosis> getDiagnosisList(Long patientId, Long admissionId);
}
