package com.uniovi.sdi2223entrega182.controllers;

import com.uniovi.sdi2223entrega182.entities.Log;
import com.uniovi.sdi2223entrega182.services.LogService;
import com.uniovi.sdi2223entrega182.services.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class LogController {
    @Autowired
    private LogService logService;
    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    private boolean loggin = false;

    /**
     * Metodo que devuelve los logs del sistema
     * @param model
     * @return
     */
    @RequestMapping("/logs")
    public String log(Model model){
        model.addAttribute("logslist", logService.getLogs());
        if (!loggin){
            logger.info(String.format("Acceso a LOG LIST"));
            Log log = new Log("PET","LOG CONTROLLER LIST", new Date());
            logService.addLog(log);
            loggin = true;
        }
        return "logs";
    }

    /**
     * Metodo que elimina los logs
     * @return
     */
    @RequestMapping("/logs/delete/")
    public String deleteOffer(){
        logService.deleteAll();
        return "redirect:/logs";
    }
}
