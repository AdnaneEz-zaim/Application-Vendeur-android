package com.example.vendeur.produitsVendeur;

import android.graphics.Bitmap;

import com.example.vendeur.carte.IconMaker;
//this class stores all the product on the vendeur
public class ProduitVendeur {


    private String nomProd;
    private float quantiteProd;
    private String imageProd;
    private String categ;
    private Bitmap imageBitmap;

    private IconMaker icon;

    public ProduitVendeur(String nomProd, float quantiteProd , String imageProd,String categ) {
        this.nomProd = nomProd;
        this.quantiteProd = quantiteProd;
        this.imageProd = imageProd;
        this.categ=categ;
    }

    public String getNomProd() {
        return nomProd;
    }
    public float getQuantiteProd() {
        return quantiteProd;
    }
    public Bitmap getImageBitmap() {
        return imageBitmap;
    }
    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
    public String getImageProd() {
        return imageProd;
    }
    public String getCateg(){ return categ.toUpperCase();}


}
