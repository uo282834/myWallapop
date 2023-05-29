package com.uniovi.sdi2223entrega182.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_AddOfferView extends PO_NavView{
    static public void fillForm(WebDriver driver, String titlep, String detailsp, String amountp) {
        WebElement title1 = driver.findElement(By.name("title"));
        title1.click();
        title1.clear();
        title1.sendKeys(titlep);
        WebElement details1 = driver.findElement(By.name("details"));
        details1.click();
        details1.clear();
        details1.sendKeys(detailsp);
        WebElement amount1 = driver.findElement(By.name("amount"));
        amount1.click();
        amount1.clear();
        amount1.sendKeys(amountp);

        //Pulsar el boton de Alta.
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }
    static public void fillSearchTextForm(WebDriver driver, String searchText) {
        WebElement search = driver.findElement(By.name("searchText"));
        search.click();
        search.clear();
        search.sendKeys(searchText);
        By boton = By.id("buscar");
        driver.findElement(boton).click();
    }
    static public void fillFormWithImage(WebDriver driver, String titlep, String detailsp, String amountp, String imagep) {
        WebElement title1 = driver.findElement(By.name("title"));
        title1.click();
        title1.clear();
        title1.sendKeys(titlep);
        WebElement details1 = driver.findElement(By.name("details"));
        details1.click();
        details1.clear();
        details1.sendKeys(detailsp);
        WebElement amount1 = driver.findElement(By.name("amount"));
        amount1.click();
        amount1.clear();
        amount1.sendKeys(amountp);
        WebElement image1 = driver.findElement(By.name("file"));
        image1.sendKeys(imagep);

        //Pulsar el boton de Alta.
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }
}
