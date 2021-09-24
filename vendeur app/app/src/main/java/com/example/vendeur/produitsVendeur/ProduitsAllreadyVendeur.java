package com.example.vendeur.produitsVendeur;
//ProductsAllreadyVendeur contains the all product informations the vendeur

public class ProduitsAllreadyVendeur {
    private String nomProduit;
    private int quantiteProduit;
    private int idVendeur;
    private int idProduit;
    private String categorier;
    private String image;
    public ProduitsAllreadyVendeur(String nomProduit, int quantiteProduit, int idVendeur, int idProduit,String categorier,String image) {
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
