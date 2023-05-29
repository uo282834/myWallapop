package com.uniovi.sdi2223entrega182.entities;



import javax.persistence.*;
import java.util.Date;


@Entity
public class Offer {
    /*
    Un usuario identificado con perfil de Usuario Estándar, debe poder crear una oferta proporcionando:
    título descriptivo de la oferta, detalle textual de la oferta, fecha de alta de la oferta (puede ser la del
    sistema) y cantidad solicitada en euros
     */
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String details;
    private Date offerDate;
    private double amount;
    private boolean available = true;
    private String description;

    @ManyToOne
    @JoinColumn(name="buyer_id")
    private User buyer;
    private String image;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Offer(){   }

    public Offer(Long id, String title, String details, Date offerDate, double amount) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.offerDate = offerDate;
        this.amount = amount;
        this.available = true;
    }

    public Offer(String title, String details, Date offerDate, double amount, User user){
        super();
        this.title = title;
        this.details = details;
        this.offerDate = offerDate;
        this.amount = amount;
        this.user = user;
        this.image = "default-image.png";
        this.available = true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(Date offerDate) {
        this.offerDate = offerDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descripcion) {
        this.description = descripcion;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    public void setNotAvailable() {
        this.available = false;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", offerDate=" + offerDate +
                ", amount=" + amount +
                ", available=" + available +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }
}
