package com.uniovi.sdi2223entrega182.validators;

import com.uniovi.sdi2223entrega182.entities.Log;
import com.uniovi.sdi2223entrega182.entities.User;
import com.uniovi.sdi2223entrega182.services.LogService;
import com.uniovi.sdi2223entrega182.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class LoginHandler  extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private LogService service;

    @Autowired
    private UsersService userServices;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Log log = new Log("LOGIN-EX,",request.getParameter("username"), new Date());
        service.addLog(log);
        logger.info(String.format("LOGIN EXITOSO DEL SISTEMA"));
        String email = request.getParameter("username");
        if(email.equals("admin@email.com")){
            super.setDefaultTargetUrl("/admin/userList");
        }else{
            super.setDefaultTargetUrl("/offer/list");
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
