package web.controller;


import web.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UsersController {

    private static final String REDIRECT = "redirect:/users";
    final UserService userService;
    public UsersController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("")
    public String allUsers (Model model) {
        model.addAttribute("user", userService.getUserList());
        return "index";
    }

    @GetMapping("/{id}")
    public String show (@PathVariable("id") long id, Model model){
        model.addAttribute("user", userService.findUserToID(id));
        return "info";
    }

    @GetMapping("/add")
    public String newUser(@ModelAttribute("user") User user) {
        return "add";
    }

    @PostMapping("")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "add";
        }
        userService.add(user);
        return REDIRECT;
    }
    @GetMapping("/{id}/upgrade")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findUserToID(id));
        return "upgrade";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "upgrade";
        }
        userService.updateUser(user);
        return REDIRECT;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return REDIRECT;
    }
}
