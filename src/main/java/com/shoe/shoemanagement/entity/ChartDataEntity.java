package com.shoe.shoemanagement.entity;

// Alert.java

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ChartDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int uv;
    private int pv;
    private int amt;

    // Default constructor
    public ChartDataEntity() {
    }

    // Parameterized constructor
    public ChartDataEntity(String name, int uv, int pv, int amt) {
        this.name = name;
        this.uv = uv;
        this.pv = pv;
        this.amt = amt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUv() {
        return uv;
    }

    public void setUv(int uv) {
        this.uv = uv;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public int getAmt() {
        return amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }
}