package com.assignation.models;

public class Reservation {
    private int id;
    private String idClient;
    private int nbpassagers;
    private String dateheure;
    private int idHotel;
    private String hotelNom;

    public Reservation() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public int getNbpassagers() {
        return nbpassagers;
    }

    public void setNbpassagers(int nbpassagers) {
        this.nbpassagers = nbpassagers;
    }

    public String getDateheure() {
        return dateheure;
    }

    public void setDateheure(String dateheure) {
        this.dateheure = dateheure;
    }

    public int getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public String getHotelNom() {
        return hotelNom;
    }

    public void setHotelNom(String hotelNom) {
        this.hotelNom = hotelNom;
    }
}
