package com.uniovi.sdi2223entrega182.controllers;

import com.uniovi.sdi2223entrega182.entities.Conversation;
import com.uniovi.sdi2223entrega182.entities.Log;
import com.uniovi.sdi2223entrega182.entities.Offer;
import com.uniovi.sdi2223entrega182.entities.User;
import com.uniovi.sdi2223entrega182.services.*;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Date;

@Controller
public class ConversationController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private OffersService offersService;
    @Autowired
    private ConversationServices conversationService;

    /**
     * Metodo que devuelve un chat
     * @param offerId
     * @param model
     * @param user
     * @return
     */
    @RequestMapping("/conversation/chat/{offerId}")
    public String getChat(@PathVariable Long offerId, Model model, Principal user){
       String mail = user.getName();
       User connected = usersService.getUserByEmail(mail);
       Offer o = offersService.getOffer(offerId);
 Conversation c =conversationService.getConversation(o,connected);
        if(c!=null){
        model.addAttribute("user", connected);
        model.addAttribute("messages", conversationService.getMessages(c));
          model.addAttribute("offer", o);
            model.addAttribute("conversation", c);
        return  "conversation/chat";}
       return "/home";
    }

    /**
     * Metodo que devuelve un chat en concreto
     * @param offerId
     * @param converId
     * @param model
     * @param user
     * @return
     */
    @RequestMapping("/conversation/chat/{offerId}/{converId}")
    public String getChat(@PathVariable Long offerId,@PathVariable Long converId, Model model, Principal user){
        String mail = user.getName();
        User connected = usersService.getUserByEmail(mail);
        Offer o = offersService.getOffer(offerId);
        Conversation c =conversationService.getConversation(converId,connected);
        if(c!=null){
        model.addAttribute("user", connected);
        model.addAttribute("messages", conversationService.getMessages(c));
            model.addAttribute("offer", o);
            model.addAttribute("conversation", c);
        return  "conversation/chat";}
        return "/home";

    }

    /**
     * Metodo que envia un mensaje
     * @param idChat
     * @param text
     * @param model
     * @param principal
     * @return
     */
    @RequestMapping(value = "/conversation/send/{idChat}", method = RequestMethod.POST)
    public String send(@PathVariable Long idChat, @RequestParam(name = "mensaje", required = true) String text,
                       Model model, Principal principal) {
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        Conversation c = conversationService.getConversation(idChat);

      User u = c.getOffer().getUser();
      if(!text.isEmpty()){
       conversationService.sendMessage(c, user, u, text);}
         model.addAttribute("user", user);
         model.addAttribute("messages", conversationService.getMessages(c));

        return "redirect:/conversation/chat/" +c.getOffer().getId()+"/" +c.getId();
    }

    /**
     * Metodo que muestra la lista de chats
     * @param model
     * @param p
     * @return
     */
    @RequestMapping("/conversation/list")
    public String getList(Model model,Principal p){
        String email = p.getName();
        User user = usersService.getUserByEmail(email);
        model.addAttribute("conversations", conversationService.getConversations(user));
       return "/conversation/list";
    }

    /**
     * Metodo que borra un chat
     * @param id
     * @param p
     * @return
     */
    @RequestMapping("/conversation/delete/{id}")
    public String delete(@PathVariable Long id,Principal p) {
        String email = p.getName();
        User user = usersService.getUserByEmail(email);
      Conversation a = conversationService.deleteConversation(id,user.getId());
       if(a==null){
           return "/convn/list";
       }
        return "redirect:/conversation/list";
    }

}
