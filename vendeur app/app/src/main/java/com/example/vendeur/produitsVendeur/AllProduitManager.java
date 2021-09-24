package com.example.vendeur.produitsVendeur;

import java.util.Vector;
//this class manage all the products
public class AllProduitManager {
    Vector<ProduitsAllreadyVendeur> produits;

    public AllProduitManager(Vector<ProduitsAllreadyVendeur> produits){
        this.produits=produits;
    }
    public AllProduitManager(){
        produits=new Vector<ProduitsAllreadyVendeur>();
    }

    public void addProduits(ProduitsAllreadyVendeur prod){
        if (! produits.contains(prod))
            produits.add(prod);
    }

    public Vector<ProduitsAllreadyVendeur> getProduits(){ return produits;}

    public  void setProduits(Vector<ProduitsAllreadyVendeur> produits){
        this.produits=produits;
    }
    //get the products of the category passed in the parameters
    public Vector<ProduitsAllreadyVendeur> getProduitsByCategorer(String categorer){
        Vector<ProduitsAllreadyVendeur> var=new Vector<ProduitsAllreadyVendeur>();
        for(ProduitsAllreadyVendeur pr:produits){
            if(pr.getCategorier().equals((categorer)))
                var.add(pr);
        }
        return var;
    }


}
