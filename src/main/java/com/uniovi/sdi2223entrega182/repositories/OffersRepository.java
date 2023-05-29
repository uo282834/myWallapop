package com.uniovi.sdi2223entrega182.repositories;

import com.uniovi.sdi2223entrega182.entities.Offer;
import com.uniovi.sdi2223entrega182.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OffersRepository extends CrudRepository<Offer, Long> {
    @Query("SELECT r FROM Offer r WHERE (LOWER(r.title) LIKE LOWER(?1))")
    Page<Offer> searchByNameAndLastName(Pageable pageable, String searchText);

    Page<Offer> findAll(Pageable pageable);

    @Query("select o from Offer o WHERE o.user = ?1")
    List<Offer> searchAllByEmail(User user);

}
