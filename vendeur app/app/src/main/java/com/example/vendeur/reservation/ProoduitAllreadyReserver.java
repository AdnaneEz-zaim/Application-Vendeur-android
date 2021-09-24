package com.example.vendeur.reservation;
//==> ProdutsAllreadyDemande contains the reserve product informations
public class ProoduitAllreadyReserver {
    private String nomProduit;
    private int quantiteProduit;
    private int idClient;
    private int idVendeur;
    private int idReservation;
    private int idProduit;
    private String image;

    public ProoduitAllreadyReserver(String nomProduit, int quantiteProduit, int idClient, int idVendeur, int idReservation, int idProduit,String image) {
        this.nomProduit = nomProduit;
        this.quantiteProduit = quantiteProduit;
        this.idClient = idClient;
        this.idVendeur = idVendeur;
        this.idReservation = idReservation;
        this.idProduit = idProduit;
        this.image=image;
    }

    public String getNomProduit() {
        return nomProduit;
    }
    public int getQuantiteProduit() {
        return quantiteProduit;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdVendeur() {
        return idVendeur;
    }

    public int getIdReservation() {
        return idReservation;
    }

    public String getImage(){ return  image;}
    public int getIdProduit() {
        return idProduit;
    }
}
