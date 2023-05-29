package com.uniovi.sdi2223entrega182.controllers;

import com.uniovi.sdi2223entrega182.entities.Log;
import com.uniovi.sdi2223entrega182.entities.Offer;
import com.uniovi.sdi2223entrega182.entities.User;
import com.uniovi.sdi2223entrega182.services.LogService;
import com.uniovi.sdi2223entrega182.services.OffersService;
import com.uniovi.sdi2223entrega182.services.SecurityService;
import com.uniovi.sdi2223entrega182.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Set;

@Controller
public class HomeController {
    /**
     * Método que retorna a la vista principal del sistema.
     * @return la vista mencionada
     */

    @Autowired
    private OffersService offersService;
    @Autowired
    private LogService logService;

    private boolean loggin = false;

    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    @Autowired
    private UsersService usersService;

    /**
     * Metodo que devuelve el index de la pagina
     * @return
     */
    @RequestMapping("/")
    public String index() {
        logger.info(String.format("Acceso a HOME INDEX"));
        Log log = new Log("PET","HOME CONTROLLER INDEX", new Date());
        logService.addLog(log);
        return "index";
    }

    /**
     * Metodo que devuelve el home de la pagina
     * @param model
     * @param pageable
     * @param searchText
     * @return
     */
    @RequestMapping("/home")
    public String home(Model model, Pageable pageable, @RequestParam(value = "", required = false)String searchText) {
        Page<Offer> offers = this.offersService.getPageOffers(pageable, searchText);
        User activeUser = usersService.getUser();

        model.addAttribute("offerList", offers.getContent());
        model.addAttribute("page", offers);
        model.addAttribute("email2", activeUser.getEmail());
        model.addAttribute("money", activeUser.getMoney());

        logger.info(String.format("Acceso a HOME HOME"));
        Log log = new Log("PET","HOME CONTROLLER HOME", new Date());
        logService.addLog(log);

        return "home";
    }

}
