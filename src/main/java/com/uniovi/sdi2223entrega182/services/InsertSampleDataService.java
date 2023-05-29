package com.uniovi.sdi2223entrega182.services;

import com.uniovi.sdi2223entrega182.entities.Offer;
import com.uniovi.sdi2223entrega182.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class InsertSampleDataService {
    @Autowired
    private UsersService usersService;
    @Autowired
    private RolesService rolesService;

    /**
     * Inicia la base de datos con 5 usuarios, 3 ofertas para cada uno, 2 compras y
     * 1 conversación por oferta entre su ofertante y un demandante. (2 líneas por
     * interlocutor).
     */
    @PostConstruct
    public void init() {
        // Creo los usuarios

        User user1 = new User("mariobalotelli@uniovi.es", "Mario", "Balotelli");
        user1.setPassword("123456");
        user1.setRole(rolesService.getRoles()[1]);
        user1.setMoney(100d);
        User user2 = new User("leomessi@uniovi.es", "Leo", "Messi");
        user2.setPassword("123456");
        user2.setRole(rolesService.getRoles()[1]);
        user2.setMoney(100d);
        User user3 = new User("cristianoronaldo@uniovi.es", "Cristiano", "Ronaldo");
        user3.setPassword("123456");
        user3.setRole(rolesService.getRoles()[1]);
        user3.setMoney(100d);
        User user4 = new User("upamencano@uniovi.es", "Albert", "Upamencano");
        user4.setPassword("123456");
        user4.setRole(rolesService.getRoles()[1]);
        user4.setMoney(100d);
        User user5 = new User("djuka@uniovi.es", "Uros", "Djurdjevic");
        user5.setPassword("123456");
        user5.setRole(rolesService.getRoles()[1]);
        user5.setMoney(10d);
        User user6 = new User("admin@email.com", "Usuario", "Administrador");
        user6.setPassword("admin");
        user6.setRole(rolesService.getRoles()[0]);
        user6.setMoney(100d);

        Set user1Offers = new HashSet<Offer>() {
            {
                add(new Offer("Oferta A1", "Detalles A1", new Date(), 40, user1));
                add(new Offer("Oferta A2", "Detalles A2", new Date(), 80, user1));
                add(new Offer("Oferta A3", "Detalles A3", new Date(), 10, user1));
                add(new Offer("Oferta A4", "Detalles A4", new Date(), 100, user1));
            }
        };
        user1.setOffers(user1Offers);

        Set user2Offers = new HashSet<Offer>() {
            {
                add(new Offer("Oferta B1", "Detalles B1", new Date(), 50, user2));
                add(new Offer("Oferta B2", "Detalles B2", new Date(), 70, user2));
                add(new Offer("Oferta B3", "Detalles B3", new Date(), 15, user2));
                add(new Offer("Oferta B4", "Detalles B4", new Date(), 300, user2));
            }
        };
        user2.setOffers(user2Offers);
        Set user3Offers = new HashSet<Offer>() {
            {
                add(new Offer("Oferta C1", "Detalles C1", new Date(), 100, user3));
                add(new Offer("Oferta C2", "Detalles C2", new Date(), 200, user3));
                add(new Offer("Oferta C3", "Detalles C3", new Date(), 300, user3));
                add(new Offer("Oferta C4", "Detalles C4", new Date(), 400, user3));
            }
        };
        user3.setOffers(user3Offers);
        Set user4Offers = new HashSet<Offer>() {
            {
                add(new Offer("Oferta D1", "Detalles D1", new Date(), 10, user4));
                add(new Offer("Oferta D2", "Detalles D2", new Date(), 20, user4));
                add(new Offer("Oferta D3", "Detalles D3", new Date(), 30, user4));
                add(new Offer("Oferta D4", "Detalles D4", new Date(), 40, user4));
            }
        };
        user4.setOffers(user4Offers);

        usersService.addUser(user1);
        usersService.addUser(user2);
        usersService.addUser(user3);
        usersService.addUser(user4);
        usersService.addUser(user5);
        usersService.addUser(user6);
    }
}