package com.shoe.shoemanagement.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "given_price")
    private double givenPrice;

    @Column(name = "sold_price")
    private double soldPrice;

    @Column(name = "profit")
    private double profit;

    @Column(name = "date")
    private LocalDate date;
    public Sale() {
    }

    public Sale(String itemName, double givenPrice, double soldPrice) {
        this.itemName = itemName;
        this.givenPrice = givenPrice;
        this.soldPrice = soldPrice;
        this.profit = soldPrice - givenPrice;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getGivenPrice() {
        return givenPrice;
    }

    public void setGivenPrice(double givenPrice) {
        this.givenPrice = givenPrice;
    }

    public double getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(double soldPrice) {
        this.soldPrice = soldPrice;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }



    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
}


