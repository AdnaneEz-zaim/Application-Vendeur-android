package com.example.vendeur.espaceVendeur;

import com.example.vendeur.produitsVendeur.ProduitsAllreadyVendeur;
import com.example.vendeur.reservation.ProoduitAllreadyReserver;
import com.example.vendeur.carte.PointMap;
import com.example.vendeur.demande.ProdutsAllreadyDemande;

import java.util.Vector;
//this class contains all the informations of the user
public class Vendeur {
    private String nom;
    private String prenom;
    private String tele;
    private String email;
    private double solde;
    private int id_user;
    private int id_vendeur;
    private String image;
    private Vector<ProdutsAllreadyDemande> produitsDemande;
    private Vector<ProoduitAllreadyReserver> produitsReserver;
    private Vector<ProduitsAllreadyVendeur> produitsVendeur;
    private int nombreNoti;
    private PointMap pointCentre;
    public Vendeur(){

        produitsDemande=new Vector<ProdutsAllreadyDemande>();
        produitsReserver=new Vector<ProoduitAllreadyReserver>();
        produitsVendeur=new Vector<ProduitsAllreadyVendeur>();
        this.nombreNoti=0;
    }
    public Vendeur(String nom ,String prenom, String tele ,String email,double solde, int id_vendeur,int id_user,String image){

        this.email=email;
        this.id_user=id_user;
        this.nom=nom;
        this.id_vendeur=id_vendeur;
        this.prenom=prenom;
        this.solde=solde;
        this.tele=tele;
        this.image=image;
        produitsDemande=new Vector<ProdutsAllreadyDemande>();
        produitsReserver=new Vector<ProoduitAllreadyReserver>();
        produitsVendeur=new Vector<ProduitsAllreadyVendeur>();
        this.nombreNoti=0;
    }
    public Vendeur(String nom ,String prenom, String tele ,String email,double solde, int id_vendeur,int id_user,String image,PointMap pointCentre){

        this.email=email;
        this.id_user=id_user;
        this.nom=nom;
        this.id_vendeur=id_vendeur;
        this.prenom=prenom;
        this.solde=solde;
        this.tele=tele;
        this.image=image;
        produitsDemande=new Vector<ProdutsAllreadyDemande>();
        produitsReserver=new Vector<ProoduitAllreadyReserver>();
        produitsVendeur=new Vector<ProduitsAllreadyVendeur>();
        this.nombreNoti=0;
        this.pointCentre.setX(pointCentre.getX());
        this.pointCentre.setY(pointCentre.getY());
    }
    public Vector<ProdutsAllreadyDemande> getProductsDemande(){
        return produitsDemande;
    }
    public Vector<ProoduitAllreadyReserver> getProductsReserver(){
        return produitsReserver;
    }
    public Vector<ProduitsAllreadyVendeur> getProductsVendeur(){
        return produitsVendeur;
    }

    public void addToPrductsDemandes(ProdutsAllreadyDemande prod){
        produitsDemande.add(prod);
    }
    public void addToPrductsReservation(ProoduitAllreadyReserver prod){ produitsReserver.add(prod); }
    public void addToPrductsVendeur(ProduitsAllreadyVendeur prod){ produitsVendeur.add(prod); }

    public void centreMapZoneVendeur(PointMap point){ pointCentre.setY(point.getY());
        pointCentre.setX(point.getX());
    }

    public PointMap getPointCentre() {
        return pointCentre;
    }

    public void setPointCentre(PointMap pointCentre) {
        this.pointCentre = pointCentre;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTele() {
        return tele;
    }

    public String getEmail() {
        return email;
    }

    public double getSolde() {
        return solde;
    }
    public void setImage(String image){ this.image=image;}

    public int getId_user() {
        return id_user;
    }

    public int getNombreNoti() {
        return nombreNoti;
    }

    public void setNombreNoti(int nombreNoti){ this.nombreNoti=nombreNoti;}
    public String getImageProfile(){ return image;}

    public int getId_vendeur() {
        return id_vendeur;
    }
    public void clearDemandes(){
        produitsDemande.clear();
    }
    public void clearReserves(){
        produitsReserver.clear();
    }
    public void clearProduitsVendeur(){
        produitsVendeur.clear();
    }
    public void clearPointCentre(){
        pointCentre=null;
    }

}
