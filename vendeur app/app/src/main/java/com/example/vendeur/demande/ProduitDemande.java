package com.example.vendeur.demande;

import android.graphics.Bitmap;

import com.example.vendeur.carte.IconMaker;
//ProduitReserve represent the item showing in the listview of fragment DEMANDE
public class ProduitDemande {
    private int idDemande;
    private String nomProd;
    private float quantiteProd;
    private String imageProd;
    private Bitmap imageBitmap;
    private IconMaker icon;

    public ProduitDemande(String nomProd, float quantiteProd, String imageProd,int idDemande) {
        this.nomProd = nomProd;
        this.quantiteProd = quantiteProd;
        this.imageProd = imageProd;
        this.idDemande=idDemande;
    }

    public String getNomProd() {
        return nomProd;
    }

    public float getQuantiteProd() {
        return quantiteProd;
    }

    public String getImageProd() {
        return imageProd;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public IconMaker getIcon() {
        return icon;
    }

    public int getIdDemande() {
        return idDemande;
    }
}
