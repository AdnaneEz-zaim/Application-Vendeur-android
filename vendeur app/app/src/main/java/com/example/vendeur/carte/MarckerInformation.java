package com.example.vendeur.carte;
//this class contains all the informations of the MARCKER
public class MarckerInformation {
    PointMap pointMap;
    String nomProduit;
    String nomClient;
    String numTeleClinet;
    int qunatitie;
    String imageclient;
    int idDemande;
    String categorie;

    public MarckerInformation() {
        this.pointMap = new PointMap();
        this.nomProduit = "";
        this.nomClient = "";
        this.numTeleClinet = "";
        this.qunatitie = 0;
        this.idDemande=-1;
        this.imageclient="";
        this.categorie="";
    }
    public MarckerInformation(PointMap pointMap, String nomProduit, String nomClient, String numTeleClinet, int qunatitie,int idDemande,String imageclient,String categorie) {
        this.pointMap = pointMap;
        this.nomProduit = nomProduit;
        this.nomClient = nomClient;
        this.numTeleClinet = numTeleClinet;
        this.qunatitie = qunatitie;
        this.idDemande=idDemande;
        this.imageclient=imageclient;
        this.categorie=categorie;
    }
    public PointMap getPointMap() {
        return pointMap;
    }

    public void setPointMap(PointMap pointMap) {
        this.pointMap = pointMap;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getNumTeleClinet() {
        return numTeleClinet;
    }

    public void setNumTeleClinet(String numTeleClinet) {
        this.numTeleClinet = numTeleClinet;
    }

    public int getQunatitie() {
        return qunatitie;
    }

    public void setQunatitie(int qunatitie) {
        this.qunatitie = qunatitie;
    }
    public int getIdDemande() {
        return idDemande;
    }
    public void setIdDemande(int idDemande) {
        this.idDemande = idDemande;
    }
    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

}
