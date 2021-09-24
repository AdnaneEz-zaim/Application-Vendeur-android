package com.example.vendeur.ui.main;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.vendeur.espaceVendeur.Vendeur;

public class UserLocalStore {

    public  static  final String Sp_nmae="userDetails";
    SharedPreferences userLocalDataBases;

    public UserLocalStore(Context context){
        userLocalDataBases= context.getSharedPreferences(Sp_nmae,0);

    }
    public void StoreUserData(Vendeur vendeur){
        SharedPreferences.Editor spEditer=userLocalDataBases.edit();

        spEditer.putString("nom",vendeur.getNom());
        spEditer.putString("prenom",vendeur.getPrenom());
        spEditer.putString("email",vendeur.getEmail());
        spEditer.putString("tele",vendeur.getTele());
        spEditer.putInt("id_user",vendeur.getId_user());
        spEditer.putInt("id_vendeur",vendeur.getId_vendeur());
        spEditer.putFloat("solde",(float)vendeur.getSolde());
        spEditer.commit();
    }

    public Vendeur getLoggedUser(){

        String nom=userLocalDataBases.getString("nom","");
        String prenom=userLocalDataBases.getString("prenom","");
        String tele=userLocalDataBases.getString("tele","");
        String email=userLocalDataBases.getString("email","");
        String image=userLocalDataBases.getString("image","");
        int id_user=userLocalDataBases.getInt("id_user",-1);
        int id_vendeur=userLocalDataBases.getInt("id_vendeur",-1);
        long solde=userLocalDataBases.getLong("solde",0);

        Vendeur StoreUser=new Vendeur(nom ,prenom,tele ,email,solde,id_vendeur,id_user,image);
        return StoreUser;
    }

    public void setUserLoggedUser(boolean loggedIn){
        SharedPreferences.Editor spEditer=userLocalDataBases.edit();
        spEditer.putBoolean("loggedIn",loggedIn);
        spEditer.commit();
    }

    public boolean getUserLoggedIn(){
       if(userLocalDataBases.getBoolean("loggedIn",false)==true){
        return true;
       }
       return false;
    }
    public  void clearUserData(){
        SharedPreferences.Editor loggedIn=userLocalDataBases.edit();
        loggedIn.clear();
        loggedIn.commit();
    }



}
