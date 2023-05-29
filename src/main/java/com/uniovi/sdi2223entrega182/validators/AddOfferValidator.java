package com.uniovi.sdi2223entrega182.validators;

import com.uniovi.sdi2223entrega182.entities.Offer;
import com.uniovi.sdi2223entrega182.services.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AddOfferValidator implements Validator {
    @Autowired
    private OffersService offersService;
    @Override
    public boolean supports(Class<?> aClass) {
        return Offer.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Offer offer = (Offer) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Error.empty");
        if (offer.getTitle().length() < 5 || offer.getTitle().length() > 50) {
            errors.rejectValue("title", "Error.addOffer.title.length");}
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "details", "Error.empty");
        if (offer.getDetails().length() < 10 || offer.getDetails().length() > 100) {
            errors.rejectValue("details", "Error.addOffer.details.length");}
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "amount", "Error.empty");
        if (offer.getAmount() <= 0) {
            errors.rejectValue("amount", "Error.addOffer.amount.domain");}
    }
}
