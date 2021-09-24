package com.example.vendeur.produitsNotification;

import java.util.Vector;
//this class nNOTIFICATION MANAGER
public class NotificationManager {
    Vector<ProduitsAllredyNotificationVendeur> produits;

    public NotificationManager(Vector<ProduitsAllredyNotificationVendeur> produits){
        this.produits=produits;
    }

    public NotificationManager(){
        produits=new Vector<ProduitsAllredyNotificationVendeur>();
    }

    public void addProduits(ProduitsAllredyNotificationVendeur prod){
        if (! produits.contains(prod))
            produits.add(prod);
    }

    public Vector<ProduitsAllredyNotificationVendeur> getProduits(){ return produits;}

    public  void setProduits(Vector<ProduitsAllredyNotificationVendeur> produits){
        this.produits=produits;
    }

    public Vector<ProduitsAllredyNotificationVendeur> getProduitsByCategorer(String categorer){
        Vector<ProduitsAllredyNotificationVendeur> var=new Vector<ProduitsAllredyNotificationVendeur>();
        for(ProduitsAllredyNotificationVendeur pr:produits){
            if(pr.getCategorier().equals((categorer)))
                var.add(pr);
        }
        return var;
    }
    //la distictions des produits qui sont faible en quontitier
    public int getNombrOfNotification(){
        int nb=0;
        for(ProduitsAllredyNotificationVendeur pr:produits){
            if(pr.getQuantiteProduit()<50)
                nb++;
        }
        return nb;
    }


}
