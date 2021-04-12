package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    // импорт ссылок на интерфейсы
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showAllUsers(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "index";
    }

    @GetMapping(value = "/addNewUser")
    public String addNewUser(Model model) {
        model.addAttribute("listRole", roleService.getListRole());
        model.addAttribute("user", new User());
        return "user_save_info";
    }

    @PostMapping(value = "/saveUser")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam("role") String[] role) {

        user.setRoles(getAddRole(role));
        System.out.println(user.getId());
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/editUser/{id}")
    public String updateUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("listRole", roleService.getListRole());
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "user_edit";
    }

    @PostMapping(value = "/userEdit")
    public String Update(@ModelAttribute("user") User user, @RequestParam("role") String[] role,
                         @RequestParam("password") String password
    )
    {
        if (password.isEmpty()) {
            user.setPassword(user.getPassword());
        } else
            user.setPassword(password);

        user.setRoles(getAddRole(role));
        userService.editUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    private Set<Role> getAddRole(String[] role) {
        Set<Role> roleSet = new HashSet<>();
        for (String s : role) {
            roleSet.add(roleService.getRoleByName(s));
        }
        return roleSet;
    }

}