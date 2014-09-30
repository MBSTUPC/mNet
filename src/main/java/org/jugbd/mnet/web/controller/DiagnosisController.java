package org.jugbd.mnet.web.controller;

import org.jugbd.mnet.domain.AdmissionInfo;
import org.jugbd.mnet.domain.Diagnosis;
import org.jugbd.mnet.service.AdmissionInfoService;
import org.jugbd.mnet.service.DiagnosisService;
import org.jugbd.mnet.service.PatientService;
import org.jugbd.mnet.service.UserService;
import org.jugbd.mnet.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author Mushfekur Rahman (mushfek0001)
 */
@Controller
@RequestMapping("/diagnosis")
public class DiagnosisController {

    private static final Logger log = LoggerFactory.getLogger(DiagnosisController.class);

    @Autowired
    private DiagnosisService diagnosisService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdmissionInfoService admissionInfoService;

    @Autowired
    private PatientService patientService;

//    @ModelAttribute("diagnosis")
//    private Diagnosis getDiagnosis() {
//        return new Diagnosis();
//    }

    @RequestMapping(value = "/create/{admissionId}", method = RequestMethod.GET)
    public String create(@PathVariable(value = "admissionId") Long admissionId, Model map) {
        AdmissionInfo admissionInfo = admissionInfoService.findOne(admissionId);

        Diagnosis diagnosis = new Diagnosis();
        //diagnosis.setAdmissionInfo(admissionInfo);

        map.addAttribute("diagnosis", diagnosis);

        return "diagnosis/create";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("diagnosis") Diagnosis diagnosis,
                       BindingResult result,
                       Principal principal,
                       Model uiModel,
                       RedirectAttributes redirectAttrs) throws ParseException {

        if (result.hasErrors()) {
            return "diagnosis/create";
        }

        Date currentDate = DateUtils.getCurrentDate();
        diagnosis.setEntryDate(currentDate);

        //TODO revisit
        //String username = principal.getName();
        //User user = userService.findByUserName(username);
        //Utils.updatePersistentProperties(diagnosis, user);
        //AdmissionInfo admissionInfo = admissionInfoService.findOne(diagnosis.getAdmissionInfo().getId());
        //diagnosis.setAdmissionInfo(admissionInfo);
        diagnosisService.saveDiagnosis(diagnosis);

        redirectAttrs.addFlashAttribute("message", "Diagnosis information entry created!");

        return "redirect:/diagnosis/show/" + diagnosis.getId();
    }

    @RequestMapping(value = "/edit/{diagnosisId}", method = RequestMethod.GET)
    public String edit(@PathVariable("diagnosisId") Long diagnosisId, Model map) {
        log.info("diagnosisId={}", diagnosisId);

        Diagnosis diagnosis = diagnosisService.getDiagnosis(diagnosisId);
        map.addAttribute("diagnosis", diagnosis);

        return "diagnosis/edit";
    }

    @RequestMapping(value = "/show/{diagnosisId}", method = RequestMethod.GET)
    public String show(@PathVariable("diagnosisId") Long diagnosisId, Model map) {
        log.info("diagnosisId={}", diagnosisId);

        Diagnosis diagnosis = diagnosisService.getDiagnosis(diagnosisId);
        map.addAttribute("diagnosis", diagnosis);
        //map.addAttribute("patientId", diagnosis.getAdmissionInfo().getPatient().getId());
        //map.addAttribute("admissionId", diagnosis.getAdmissionInfo().getId());

        return "diagnosis/show";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@ModelAttribute Diagnosis diagnosis,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "diagnosis/create";
        }

        //TODO revisit later
        //AdmissionInfo admissionInfo = admissionInfoService.findOne(diagnosis.getAdmissionInfo().getId());
       // diagnosis.setAdmissionInfo(admissionInfo);
        diagnosisService.saveDiagnosis(diagnosis);

        redirectAttributes.addFlashAttribute("message", "Diagnosis information updated!");

        return "redirect:/diagnosis/show/" + diagnosis.getId();
    }

    @RequestMapping(value = "/patients/{patientId}/admissions/{admissionId}/list", method = RequestMethod.GET)
    public String view(@PathVariable("patientId") Long patientId,
                       @PathVariable("admissionId") Long admissionId, Model map) {
        log.info("patientId={} admissionId={}", patientId, admissionId);

        List<Diagnosis> diagnosisList = diagnosisService.getDiagnosisList(patientId, admissionId);
        map.addAttribute("diagnosisList", diagnosisList);

        return "diagnosis/list";
    }
}
