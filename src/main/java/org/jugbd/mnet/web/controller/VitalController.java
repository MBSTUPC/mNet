package org.jugbd.mnet.web.controller;

import org.jugbd.mnet.domain.Vital;
import org.jugbd.mnet.domain.enums.RegistrationType;
import org.jugbd.mnet.service.RegisterService;
import org.jugbd.mnet.service.VitalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Bazlur Rahman Rokon
 * @since 10/17/14.
 */
@Controller
@Secured({"ROLE_ADMIN", "ROLE_USER"})
@RequestMapping(value = "vital")
public class VitalController {
    private static final Logger log = LoggerFactory.getLogger(VitalController.class);

    public static final String VITAL_CREATE_PAGE = "vital/create";
    public static final String REDIRECT_VITAL_SHOW_PAGE = "redirect:/vital/show/";
    public static final String VITAL_SHOW_PAGE = "vital/show";
    public static final String VITAL_INDEX_PAGE = "vital/index";
    public static final String REDIRECT_VITAL_INDEX_PAGE = "redirect:/vital/index/";

    @Autowired
    private VitalService vitalService;

    @Autowired
    private RegisterService registerService;

    @RequestMapping(value = "/create/{registerId}", method = RequestMethod.GET)
    public String create(@PathVariable Long registerId,
                         @RequestParam(required = true) RegistrationType registrationType,
                         Vital vital,
                         Model uiModel) {

        uiModel.addAttribute("registerId", registerId);
        uiModel.addAttribute("registrationType", registrationType);

        return VITAL_CREATE_PAGE;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String save(@RequestParam(required = true) RegistrationType registrationType,
                       @Valid Vital vital,
                       BindingResult result,
                       Long registerId,
                       Model uiModel) {

        if (registerId == null) {
            throw new RuntimeException("Unable to find Register Id");
        }

        if (result.hasErrors()) {
            uiModel.addAttribute("registerId", registerId);
            uiModel.addAttribute("registrationType", registrationType);

            return VITAL_CREATE_PAGE;
        }

        Vital savedVital = vitalService.saveByRegisterId(vital, registerId, registrationType);

        return REDIRECT_VITAL_SHOW_PAGE + savedVital.getId() + "?registrationType=" + registrationType;
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable Long id,
                       @RequestParam(required = true) RegistrationType registrationType,
                       Model uiModel) {

        Vital vital = vitalService.findOne(id);
        uiModel.addAttribute("vital", vital);
        uiModel.addAttribute("registrationType", registrationType);
        uiModel.addAttribute("register", registrationType == RegistrationType.OUTDOOR ? vital.getOutdoorRegister() : vital.getRegister());

        return VITAL_SHOW_PAGE;
    }

    @RequestMapping(value = "/index/{registerId}", method = RequestMethod.GET)
    public String index(@PathVariable Long registerId,
                        @RequestParam(required = true) RegistrationType registrationType,
                        Model uiModel) {

        List<Vital> vitals = vitalService.findByRegisterId(registerId, registrationType);
        uiModel.addAttribute("vitals", vitals);
        uiModel.addAttribute("registerId", registerId);
        uiModel.addAttribute("registrationType", registrationType);

        return VITAL_INDEX_PAGE;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Long id,
                         @RequestParam(required = true) RegistrationType registrationType) {
        Long registrationId = vitalService.delete(id, registrationType);

        return REDIRECT_VITAL_INDEX_PAGE + registrationId + "?registrationType=" + registrationType.name();
    }

    @RequestMapping(value = "back", method = RequestMethod.GET)
    public String backToRegistrationPage(@RequestParam(required = true) Long registerId,
                                         @RequestParam(required = true) RegistrationType registrationType) {

        return "redirect:" + registerService.findRegisterEither(registerId, registrationType)
                .fold(register -> "/register/vitals/" + register.getId() + "?registrationType=" + registrationType.name(),
                        outdoorRegister -> "/register/vitals/" + outdoorRegister.getId() + "?registrationType=" + registrationType.name());
    }
}
