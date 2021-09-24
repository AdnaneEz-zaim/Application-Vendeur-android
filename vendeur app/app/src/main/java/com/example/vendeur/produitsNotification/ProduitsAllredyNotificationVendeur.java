package com.example.vendeur.produitsNotification;
//ProdutsAllreadyDemande contains the  product notification informations
public class ProduitsAllredyNotificationVendeur {
    private String nomProduit;
    private int quantiteProduit;
    private int idVendeur;
    private int idProduit;
    private String categorier;
    private String image;
    public ProduitsAllredyNotificationVendeur(String nomProduit, int quantiteProduit, int idVendeur, int idProduit,String categorier,String image) {
        this.nomProduit = nomProduit;
        this.categorier=categorier;
        this.image=image;
        this.quantiteProduit = quantiteProduit;
        this.idVendeur = idVendeur;
        this.idProduit = idProduit;
    }
    public String getNomProduit() {
        return nomProduit;
    }
    public String getCategorier(){return categorier;}
    public int getQuantiteProduit() {
        return quantiteProduit;
    }
    public int getIdVendeur() {
        return idVendeur;
    }
    public int getIdProduit() {
        return idProduit;
    }
    public String getImage(){ return  image;}
}
