package com.uniovi.sdi2223entrega182.validators;

import com.uniovi.sdi2223entrega182.entities.Log;
import com.uniovi.sdi2223entrega182.services.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class Logout implements LogoutSuccessHandler {
    @Autowired
    private LogService service;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        service.addLog(new Log("LOGOUT", "LOGOUT DEL SISTEMA", new Date()));
        logger.info(String.format("LOGOUT DEL SISTEMA"));
        response.sendRedirect("/login?logout");
    }
}
