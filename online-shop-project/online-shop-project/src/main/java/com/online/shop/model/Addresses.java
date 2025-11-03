package com.online.shop.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "address")
public class Addresses {
    private int addressId;
    private int userId;
    private String street;
    private String city;

    public Addresses(int addressId, int userId, String street, String city) {
        this.addressId = addressId;
        this.userId = userId;
        this.street = street;
        this.city = city;
    }

    public Addresses() {}

    @XmlElement
    public int getAddressId() {
        return addressId;
    }

    @XmlElement
    public int getUserId() {
        return userId;
    }

    @XmlElement
    public String getStreet() {
        return street;
    }

    @XmlElement
    public String getCity() {
        return city;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Addresses{" +
                "addressId=" + addressId +
                ", userId=" + userId +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
