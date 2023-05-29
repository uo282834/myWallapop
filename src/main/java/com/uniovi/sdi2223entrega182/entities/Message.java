package com.uniovi.sdi2223entrega182.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue
    private long id;
    private String text;
    private Date date;


    @ManyToOne
    @JoinColumn(name = "conversation")
    private Conversation conversation;

    public Message(Date date, String text, Conversation conversation, User buyer, User offerOwner) {
        this.date = date;
        this.text = text;
        this.conversation = conversation;
        this.buyer = buyer;
        this.offerOwner = offerOwner;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    @ManyToOne
    @JoinColumn(name = "buyer")
    private User buyer;

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }


    public User getOfferOwner() {
        return offerOwner;
    }
    public Conversation getConversation() {
        return conversation;
    }

    @ManyToOne
    @JoinColumn(name = "offerOwner")
    private User offerOwner;

    public Message() {
    }
}
