package com.uniovi.sdi2223entrega182.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="conversation")
public class Conversation {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "offer")
    private Offer offer;

    @ManyToOne
    @JoinColumn(name = "creatorConver")
    private User creatorConver;

    public Conversation() {

    }

    public Conversation(Offer offer, User creatorConver, User creatorOffer) {
        this.offer = offer;
        this.creatorConver = creatorConver;
        this.creatorOffer = creatorOffer;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getCreatorOffer() {
        return creatorOffer;
    }

    @ManyToOne
    @JoinColumn(name = "creatorOffer")
    private User creatorOffer;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<Message>();

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
    public long getId() {
        return id;
    }



    public User getCreatorConver() {
        return creatorConver;
    }
    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    public void setCreatorConver(User creatorConver) {
        this.creatorConver = creatorConver;
    }

    public void setCreatorOffer(User creatorOffer) {
        this.creatorOffer = creatorOffer;
    }


}
