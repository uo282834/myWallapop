package com.uniovi.sdi2223entrega182.services;

import com.uniovi.sdi2223entrega182.entities.Conversation;
import com.uniovi.sdi2223entrega182.entities.Offer;
import com.uniovi.sdi2223entrega182.entities.User;
import com.uniovi.sdi2223entrega182.repositories.ConversationRepository;
import com.uniovi.sdi2223entrega182.repositories.OffersRepository;
import com.uniovi.sdi2223entrega182.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    public static double INITIAL_MONEY = 100d;
    public static int USER = 1;
    private List<String> lista = new ArrayList<>();
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private OffersRepository offersRepository;
    @Autowired
    private ConversationRepository conversationRepository;
    @PostConstruct
    public void init() {
    }



    // ------------------ operaciones --------------------

    /**
     * Método que añade un usuario en el sistema
     *
     * @param user que se quiere añadir
     */
    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    /**
     * Método que actualzia un usuario en la bbdd
     *
     * @param user que se quiere actualizar
     */
    public void updateUser(User user) {
        usersRepository.save(user);
    }

    /**
     * Método que elimina un usuario buscado por un email suministrado
     *
     * @param email del usuario que se quiere eliminar
     */
    public void deleteUser(String email) {
        User user = usersRepository.findByEmail(email);
        usersRepository.deleteById(user.getId());
    }

    // ------------------ consultas --------------------

    /**
     * Método que devuelve la lista de usuarios del sistema salvo el administrador
     *
     * @return la lista mencionada
     */
    public List<User> getNotAdminUsers() {
        List<User> users = new ArrayList<User>();
        usersRepository.findNotAdminUsers().forEach(users::add);
        return users;
    }

    /**
     * Método que busca un usuario por id
     *
     * @param id del usuario que se busca
     * @return el usuario
     */
    public User getUser(Long id) {
        return usersRepository.findById(id).get();
    }

    /**
     * Método que busca un usuario por email en el sistema
     *
     * @param email del usuario que se quiere buscar
     * @return el usuario buscado
     */
    public User getUserByEmail(String email) {
        User user = usersRepository.findByEmail(email);
        if (user != null){
            httpSession.setAttribute("money",user.getMoney());
            httpSession.setAttribute("email", user.getEmail());
        }
        return user;
    }
    /**
     * Método que devuelve todos los usuarios registrados
     * @return La lista de todos los usuarios registrados
     */
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }
    public User getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = getUserByEmail(email);
        return activeUser;
    }





    /**
     * Método que borra todos los usuarios seleccionados
     */
    public void removeUsers(){

        List<User> users = usersRepository.findAll();
        for(User user : users){
            if(lista.contains(user.getEmail())){
                List<Offer> ofertas = offersRepository.searchAllByEmail(user);
                for(Offer offer: ofertas)
                    offersRepository.delete(offer);
                List<Conversation> conversations = conversationRepository.findByUser(user);
                for(Conversation conversation: conversations)
                    conversationRepository.delete(conversation);
                usersRepository.delete(user);
            }
        }
        lista.clear();
    }
    public void addEmailToDelete(String email){
        if(!lista.contains(email))
            lista.add(email);
    }
    public void deleteEmailToDelete(String email){
        if(lista.contains(email))
            lista.remove(email);
    }

    public Object getUsers() {
        return null;
    }
}
