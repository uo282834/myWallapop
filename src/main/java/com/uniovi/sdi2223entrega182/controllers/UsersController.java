package com.uniovi.sdi2223entrega182.controllers;

import com.uniovi.sdi2223entrega182.entities.Offer;
import com.uniovi.sdi2223entrega182.entities.User;
import com.uniovi.sdi2223entrega182.services.OffersService;
import com.uniovi.sdi2223entrega182.services.RolesService;
import com.uniovi.sdi2223entrega182.services.SecurityService;
import com.uniovi.sdi2223entrega182.services.UsersService;
import com.uniovi.sdi2223entrega182.validators.SignUpFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class UsersController {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private SignUpFormValidator signUpFormValidator;
    @Autowired
    private UsersService usersService;
    @Autowired
    private RolesService rolesService;
    @Autowired
    private OffersService offersService;



    /**
     * Método que permite registrar un usuario en el sistema.
     *
     * @param user   que contiene los datos del nuevo usuario a registrar
     * @param result para comprobar si hay errores de validación en el formulario
     * @return la vista para registrarte si hay errores y la vista del home con
     *         ofertas destacadas si no hay errores en el formulario y hay ofertas
     *         destacada
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result) {
        signUpFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "signup";
        }
        user.setRole(rolesService.getRoles()[UsersService.USER]);
        user.setMoney(UsersService.INITIAL_MONEY);
        usersService.addUser(user);

        securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
        return "redirect:home";
    }
    /**
     * Método que permite cambiar la contraseña
     *
     * @param user   que contiene los datos del nuevo usuario a registrar
     * @return la vista para registrarte si hay errores y la vista del home con
     *         ofertas destacadas si no hay errores en el formulario y hay ofertas
     *         destacada
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String changePasswordPost(@Validated User user, RedirectAttributes atrr) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        // Verificar que la nueva contraseña no sea igual a la anterior
        if (usersService.repeatedPassword(user.getPassword(),activeUser.getPassword())) {
            atrr.addFlashAttribute("repetida", "");
            return "redirect:changePassword";
        }else{
            activeUser.setPassword(user.getPassword());
            usersService.addUser(activeUser);
            return "redirect:home";
        }
    }
    /**
     * Método que devuelve la vista con el formulario para registrarse
     *
     * @param model donde se guardará un nuevo usuario
     * @return la vista para cubrir el formulario de registro
     */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        if (usersService.getUser() != null){
            return "redirect:/home";
        }else{
            model.addAttribute("user", new User());
            return "signup";
        }
    }

    /**
     * Método que devuelve la vista con el formulario para registrarse
     *
     * @param model donde se guardará un nuevo usuario
     * @return la vista para cubrir el formulario de registro
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String changePasswordGet(Model model) {
            model.addAttribute("user", new User());
            return "changePassword";
        }

    /**
     * Método que devuelve la vista con el formulario para hacer log in en el
     * sistema
     *
     * @return la vista para cubrir el formulario de log in
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        if (usersService.getUser() != null){
            return "redirect:/home";
        }else{
            return "login";
        }
    }

    /**
     * Método que devuelve la lista de usuarios
     * @param model El modelo
     * @return La vista de la lista
     */
    @RequestMapping(value ="/admin/userList")
    public String getList(Model model){

        List<User> users = new ArrayList<User>();
        users = usersService.getAllUsers();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        model.addAttribute("activeUser",activeUser);
        model.addAttribute("usersList", users);
        model.addAttribute("usersToDelete", new ArrayList<String>());
        return "users/list";
    }
    /**
     * Método que devuelve la lista de usuarios
     * @param model El modelo
     * @return La vista de la lista
     */
    @RequestMapping(value ="/users/carrito")
    public String getCarrito(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        activeUser.getCarrito();
        model.addAttribute("carrito", activeUser.getCarrito());
        return "users/carrito";
    }
    @RequestMapping(value = {"/users/addCarrito/{id}"})
    public String addCarrito(Model model, @PathVariable Long id) {
        User activeUser = usersService.getUser();
        Offer offer = offersService.getOffer(id);
        usersService.addToCarrito(offer, activeUser);
        return "redirect:/users/carrito";
    }
    @RequestMapping(value = {"/users/deleteCarrito/{id}"})
    public String deleteCarrito(Model model, @PathVariable Long id) {
        User activeUser = usersService.getUser();
        Offer offer = offersService.getOffer(id);
        usersService.deleteToCarrito(offer, activeUser);
        return "redirect:/users/carrito";
    }
    @RequestMapping(value = {"/users/buyCarrito"})
    public String buyCarrito(Model model, RedirectAttributes atrr) {
        User activeUser = usersService.getUser();
        if (usersService.getPrecioCarrito(activeUser)){
            offersService.buyCarrito(activeUser);
            return "redirect:/offer/bought";
        }else{
            atrr.addFlashAttribute("sindinero","");
            return "redirect:/users/carrito";
        }
    }

    /**
     * Método que borre todos los usuarios seleccionados
     * @param model El modelo
     * @return La vista de la lista
     */
    @RequestMapping(value = "/admin/userList/remove")
    public String delete(Model model){

        usersService.removeUsers();
        List<User> users = usersService.getAllUsers();
        model.addAttribute("usersList", usersService.getAllUsers());
        return "redirect:/admin/userList";
    }
    @RequestMapping(value = "/admin/userList/add/{s}", method = RequestMethod.GET)
    public String addSelected(Model model, @PathVariable String s){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        if(!s.equals(email))
            usersService.addEmailToDelete(s);
        else
            return "/error";
        User activeUser = usersService.getUserByEmail(email);
        model.addAttribute("activeUser",activeUser);
        model.addAttribute("usersList", usersService.getAllUsers());
        return "/users/list";
    }
    @RequestMapping(value = "/admin/userList/removeFromList/{s}", method = RequestMethod.GET)
    public String removeSelected(Model model, @PathVariable String s){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        if(!s.equals(email))
            usersService.deleteEmailToDelete(s);
        else
            return "/error";
        User activeUser = usersService.getUserByEmail(email);
        model.addAttribute("activeUser",activeUser);
        model.addAttribute("usersList", usersService.getAllUsers());

        return "/users/list";
    }


}
