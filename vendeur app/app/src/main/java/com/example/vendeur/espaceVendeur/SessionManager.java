package com.example.vendeur.espaceVendeur;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.vendeur.produitsVendeur.AllProduitManager;
import com.example.vendeur.produitsNotification.NotificationManager;
import com.google.gson.Gson;
//SessionManager stores informations in the user's device
public class SessionManager {

    SharedPreferences preferences;

    SharedPreferences.Editor editor;

    private static final String PREFS_NAMES= "app_prefs";

    private static final String IS_LOGGED= "isLogged";

    private static final int PRIVATE_MODE= 0;

    private static final String VENDEUR="vendeur";

    private static final String PRODUITS="produits";

    private static final int IDCOMANDE=0;

    private static final String TYPECOMANDE="type";

    private static final int NOTIF_DEM=1;

    private static final int  NOTIF_RES=2;

    private static final int  NOTIF_PRODS=3;


    Gson gson;



    private Context context;


    //Constructor
    public SessionManager(Context context) {

        this.context = context;

        preferences=context.getSharedPreferences(PREFS_NAMES,PRIVATE_MODE);

        editor=preferences.edit();

    }


    public boolean isLogged(){

        return preferences.getBoolean(IS_LOGGED,false);

    }


// la fontion pour inserer les information dans l'objet vendeur qui nous recuparer appartir de la base de donnees par toJson

    public void insertVendeur(Vendeur vendeur){

        editor.putBoolean(IS_LOGGED,true);

        gson= new Gson();

        String str=gson.toJson(vendeur,Vendeur.class);

        editor.putString(VENDEUR,str);

        editor.apply();


    }


    //remplir les produits de vendeur
    public  void setProduits(AllProduitManager produits){

        gson =new Gson();

        String str=gson.toJson(produits,AllProduitManager.class);

        editor.putString(PRODUITS,str);

        editor.apply();

    }


//remplir les produits de vendeur qui sont faible on quantitie

    public  void setProduits(NotificationManager produits){

        gson =new Gson();

        String str=gson.toJson(produits,NotificationManager.class);

        editor.putString(PRODUITS,str);

        editor.apply();

    }

//obtenir tous les produits de vendeur

    public  AllProduitManager getProduits(){

        gson=new Gson();

        String json = preferences.getString("produits", "");

        return gson.fromJson(json, AllProduitManager.class);

    }


//obtenir tous les produits qui sont faible on quantitie


    public  NotificationManager getProduitsNotification(){

        gson=new Gson();

        String json = preferences.getString("produits", "");

        return gson.fromJson(json, NotificationManager.class);

    }



//obtenir tous les information de vendeur

    public Vendeur getVendeur(){

        gson=new Gson();

        String json = preferences.getString("vendeur", null);

        return gson.fromJson(json, Vendeur.class);

    }

//finir la session de vendeur


    public void logout(){

        editor.clear().commit();

    }

//remplir une commande soit demande ou reservation



    public void setComande(int id,String type){

        editor.putString(TYPECOMANDE,type);

        editor.putInt(String.valueOf(IDCOMANDE),id);

        editor.apply();

    }


//obtenir le type de la comande(demande ou reservation)

    public String getTypeComande(){

        return preferences.getString(TYPECOMANDE,"");

    }

//RETURNER L'indice de la comnde


    public int getIdComande(){

        return preferences.getInt(String.valueOf(IDCOMANDE),0);

    }


    public boolean isPassed(){

        preferences.getInt(String.valueOf(IDCOMANDE),0);

        return (preferences.getInt(String.valueOf(IDCOMANDE),0)!=0);

    }


    public void decreasesDemande(){

        editor.putInt(String.valueOf(NOTIF_DEM),preferences.getInt(String.valueOf(NOTIF_DEM),0)-1);

        editor.apply();

    }


//la modification de la demande


    public void updateDemande(int replace){

        editor.putInt(String.valueOf(NOTIF_DEM),replace);

        editor.apply();

    }

    public void decreasesReserve(){

        editor.putInt(String.valueOf(NOTIF_RES),preferences.getInt(String.valueOf(NOTIF_RES),0)-1);

        editor.apply();
    }


//modification de la reservation

    public void updateReserve(int replace){

        editor.putInt(String.valueOf(NOTIF_RES),replace);

        editor.apply();

    }

    public void decreasesProd(){

        editor.putInt(String.valueOf(NOTIF_PRODS),preferences.getInt(String.valueOf(NOTIF_PRODS),0)-1);

        editor.apply();

    }


//la modification de la produits

    public void updateProd(int replace){

        Log.i("TAG", "Tese: 1 "+replace+", "+preferences.getInt(String.valueOf(NOTIF_DEM),0)+", "+preferences.getInt(String.valueOf(NOTIF_RES),0)+", "+preferences.getInt(String.valueOf(NOTIF_PRODS),0));

        editor.putInt(String.valueOf(NOTIF_PRODS),replace);

        editor.apply();

        Log.i("TAG", "Tese: 1 "+replace+", "+preferences.getInt(String.valueOf(NOTIF_DEM),0)+", "+preferences.getInt(String.valueOf(NOTIF_RES),0)+", "+preferences.getInt(String.valueOf(NOTIF_PRODS),0));


    }

    public int getNotifDem(){
        return preferences.getInt(String.valueOf(NOTIF_DEM),0);

    }

//return le nombre des notification


    public int getNotifRes(){

        return preferences.getInt(String.valueOf(NOTIF_RES),0);

    }


    public int getNotifProd(){

        return preferences.getInt(String.valueOf(NOTIF_PRODS),0);

    }


}
