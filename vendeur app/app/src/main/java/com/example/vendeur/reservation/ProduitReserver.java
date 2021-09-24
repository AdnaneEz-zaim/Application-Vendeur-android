package com.example.vendeur.reservation;

import android.graphics.Bitmap;

import com.example.vendeur.carte.IconMaker;
// ==> ProduitReserve represent the item showing in the listview of fragment reservation
public class ProduitReserver {
    private int idReserve;
    private String nomProd;
    private float quantiteProd;
    private String imageProd;
    private Bitmap imageBitmap;
    private IconMaker icon;

    public ProduitReserver(String nomProd, float quantiteProd, String imageProd,int idReserve) {
        this.nomProd = nomProd;
        this.quantiteProd = quantiteProd;
        // icon = new IconMaker(clickedConfirme,clickedDecline);
        this.imageProd = imageProd;
        this.idReserve=idReserve;
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

    public int getIdReserve() {
        return idReserve;
    }
}
