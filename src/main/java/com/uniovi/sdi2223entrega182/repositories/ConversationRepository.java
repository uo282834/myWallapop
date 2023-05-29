package com.uniovi.sdi2223entrega182.repositories;

import com.uniovi.sdi2223entrega182.entities.Conversation;
import com.uniovi.sdi2223entrega182.entities.Offer;
import com.uniovi.sdi2223entrega182.entities.User;
import com.uniovi.sdi2223entrega182.services.ConversationServices;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface ConversationRepository extends CrudRepository<Conversation, Long> {
    @Query("SELECT c FROM Conversation c WHERE c.offer = ?2 AND ( c.creatorConver = ?1 OR c.creatorOffer = ?1)")
    Conversation findByOfferAndUsers( User actualUser,Offer offer);
    @Query("SELECT c FROM Conversation c WHERE c.creatorOffer = ?1 OR c.creatorConver = ?1")
    List<Conversation> findByUser(User actualUser);
    @Query("SELECT c FROM Conversation c WHERE (c.creatorConver.id = ?2 OR c.creatorOffer.id = ?2) AND c.id = ?1")
    Conversation findByUserAndId(Long id, Long idUser);
}
