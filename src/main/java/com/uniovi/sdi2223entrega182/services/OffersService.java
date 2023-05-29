package com.uniovi.sdi2223entrega182.services;
import com.uniovi.sdi2223entrega182.entities.Offer;
import com.uniovi.sdi2223entrega182.entities.User;
import com.uniovi.sdi2223entrega182.repositories.OffersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class OffersService {

    @Autowired
    private OffersRepository offersRepository;

    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    public List<Offer> getOffers() {
        List<Offer> offers = new ArrayList<Offer>();
        offersRepository.findAll().forEach(offers::add);
        return offers;
    }
    public Offer getOffer(Long id) {
        return offersRepository.findById(id).get();
    }
    public void addOffer(Offer offer) {
        // Si en Id es null le asignamos el ultimo + 1 de la lista
        offersRepository.save(offer);
        logger.info(String.format("Offer %s added", offer.getTitle()));
    }
    public void deleteOffer(Long id) {
        offersRepository.deleteById(id);
        logger.info(String.format("Offer %s deleted" + "offer id: ", id.toString()));
    }

    public Page<Offer> searchOffersByTitle(Pageable pageable, String searchText) {
        searchText ="%"+searchText+"%";
        Page<Offer> users  = offersRepository.searchByNameAndLastName(pageable, searchText);
        return users;
    }

    public Page<Offer> getOffers(Pageable pageable) {
        Page<Offer> list = offersRepository.findAll(pageable);
        return list;
    }

    public void addImage(MultipartFile image) throws IOException {
        Path directorioImagenes = Paths.get("src//main//resources//static/images");
        String absolutePath = directorioImagenes.toFile().getAbsolutePath();
        byte[] bytesImg = image.getBytes();
        Path completePath = Paths.get(absolutePath + "//" + image.getOriginalFilename());
        Files.write(completePath, bytesImg);
    }
    public Page<Offer> getPageOffers(Pageable pageable, String searchText ){
        Page<Offer> offers;
        if(searchText != null && !searchText.isEmpty()){
            offers = searchOffersByTitle(pageable, searchText);
        } else {
            offers = getOffers(pageable);
        }
        return offers;
    }

    public void updateOfferUser(User activeUser, Offer offer) {
        activeUser.setMoney(activeUser.getMoney() - offer.getAmount());
        offer.setBuyer(activeUser);
        Set<Offer> offerSet = activeUser.getOffersBought();
        offerSet.add(offer);
        activeUser.setOffersBought(offerSet);
        offer.setNotAvailable();
    }
}
