package com.example.vendeur.demande;
//ProdutsAllreadyDemande contains the demand product informations
public class ProdutsAllreadyDemande {
    private String nomProduit;
    private int quantiteProduit;
    private int idClient;
    private int idVendeur;
    private int idDemande;
    private int idProduit;
    private String image;

    public ProdutsAllreadyDemande(String nomProduit, int quantiteProduit, int idClient, int idVendeur, int idDemande, int idProduit,String image) {
        this.nomProduit = nomProduit;
        this.quantiteProduit = quantiteProduit;
        this.idClient = idClient;
        this.idVendeur = idVendeur;
        this.idDemande = idDemande;
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

    public int getIdDemande() {
        return idDemande;
    }


    public String getImage(){ return  image;}
    public int getIdProduit() {
        return idProduit;
    }
}
