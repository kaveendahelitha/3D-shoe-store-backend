package com.shoe.shoemanagement.controller;

// ChartData.java
public class ChartData {
    private String name;
    private int uv;
    private int pv;
    private int amt;

    public ChartData(String name, int uv, int pv, int amt) {
        this.name = name;
        this.uv = uv;
        this.pv = pv;
        this.amt = amt;
    }




    public String getName() {
        return name;
    }

    public int getUv() {
        return uv;
    }

    public int getPv() {
        return pv;
    }

    public int getAmt() {
        return amt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUv(int uv) {
        this.uv = uv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }
}

