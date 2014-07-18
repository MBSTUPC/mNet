package org.jugbd.mnet.web.controller;

import org.jugbd.mnet.domain.User;
import org.jugbd.mnet.domain.enums.Role;
import org.jugbd.mnet.service.UserService;
import org.jugbd.mnet.utils.Utils;
import org.jugbd.mnet.web.editor.AuthorityEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * Created by Bazlur Rahman Rokon on 7/15/14.
 */

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    private User getUser() {
        log.debug("getUser()");

        return new User();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {

        binder.registerCustomEditor(Role.class, new AuthorityEditor());
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create() {
        log.debug("create()");

        return "user/create";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("user") User user,
                       BindingResult result,
                       RedirectAttributes redirectAttrs,
                       Principal principal) {
        log.debug("save() user ={}", user);

        if (result.hasErrors()) {
            return "user/create";
        }

        User userFound = userService.findByUserName(user.getUsername());
        if (userFound != null) {
            result.rejectValue("username", "error.user.username.already.available", "Its look like someone already has that username. Try another");
            return "user/create";
        }

        //TODO revisit
        //User currentUser = userService.findByUserName(principal.getName());
        //Utils.updatePersistentProperties(user, currentUser);

        userService.save(user);
        redirectAttrs.addFlashAttribute("message", "Successfully user created");

        return "redirect:/user/show/" + user.getId().toString();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model uiModel) {
        log.debug("index()");

        List<User> users = userService.findAll();
        uiModel.addAttribute("users", users);

        return "user/index";
    }

    @RequestMapping(value = "show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        log.debug("show() id ={}", id);

        User user = userService.findById(id);
        uiModel.addAttribute("user", user);

        return "user/show";
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model uiModel) {
        log.debug("edit() id ={}", id);

        User user = userService.findById(id);
        user.setPassword(null);
        uiModel.addAttribute("user", user);

        return "user/edit";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("user") User user,
                         BindingResult result,
                         RedirectAttributes redirectAttrs,
                         Principal principal) {
        log.debug("update() user ={}", user);

        if (result.hasErrors()) {
            return "user/edit";
        }

        //TODO revisit
        //User currentUser = userService.findByUserName(principal.getName());
        //Utils.updatePersistentProperties(user, currentUser);

        userService.save(user);
        redirectAttrs.addFlashAttribute("message", "Successfully user updated");

        return "redirect:/user/show/" + user.getId().toString();
    }

    @RequestMapping(value = "cancel", method = RequestMethod.GET)
    public String cancel() {
        log.debug("cancel()");

        return "redirect:/user";
    }
}
