package com.example.restapiwithjpa;

import javax.persistence.*;

@Entity
@Table(name="vehicles")

public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String makeModel;
    private int year;
    private double retailPrice;

    public Vehicle() {
        this.id = id;
        this.makeModel = makeModel;
        this.year = year;
        this.retailPrice = retailPrice;
    }

    public Vehicle(String makeModel, int year, double retailPrice) {
        this.makeModel = makeModel;
        this.year = year;
        this.retailPrice = retailPrice;
    }

    public int getId() {
        return this.id;
    }

    public String getMakeModel() {
        return makeModel;
    }

    public int getYear() {
        return year;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMakeModel(String makeModel) {
        this.makeModel = makeModel;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
