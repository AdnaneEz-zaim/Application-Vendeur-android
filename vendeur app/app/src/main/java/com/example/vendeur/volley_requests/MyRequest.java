package com.example.vendeur.volley_requests;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.vendeur.Config;
import com.example.vendeur.carte.PointMap;
import com.example.vendeur.demande.ProdutsAllreadyDemande;
import com.example.vendeur.espaceVendeur.Vendeur;
import com.example.vendeur.produitsVendeur.AllProduitManager;
import com.example.vendeur.produitsVendeur.ProduitsAllreadyVendeur;
import com.example.vendeur.reservation.ProoduitAllreadyReserver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.yalantis.ucrop.UCropFragment.TAG;
//MyRequest contains all our requests of type Volley
public class MyRequest {

    private Context context;
    private RequestQueue queue;
    private StringRequest request;
    //constructor
    public MyRequest(Context context, RequestQueue queue) {
        this.context = context;
        this.queue = queue;
    }
    /**
     * these are the interfaces of the state of our requests
     */
    public interface LoginCallBack{
        void onSuccess(Vendeur vendeur);
        void onErr(String message);
    }
    public interface PDCallBack{
        void onSuccess(Vendeur vendeur);
        void onErr(String message);
    }
    public interface PVCallBack{
        void onSuccess(AllProduitManager prod);
        void onErr(String message);
    }
    public interface ConfermeDEclineCallBack{
        void onSuccess(String message);
        void inputErr(Map<String,String> errors);
        void onErr(String message);
    }
    /**
     * These are the volley requests, they have the same concept
     * starting with creating the URL of the script PHP in the server
     * then we send a request queue, and get a response or un error
     * in case of response we transform our string response to a json format, it could be a simple message or a table of data
     * then we extract our data and store it in variables or tables
     */
    public void connection(final String pseudo, final String password, final LoginCallBack callback) {


        String url = Config.URL+"rz.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json = null;

                try {
                    Log.i("Tagconvert", "[[[" + response + "]]]");
                    json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error) {


                       /*String id=json.getString("id");
                       String pseudo= json.getString("pseudo");
                       callback.onSuccess(id,pseudo);*/

                        int id_user = json.getInt("id");
                        int id_vendeur = json.getInt("idV");
                        String email = json.getString("pseudo");
                        String nom = json.getString("nom");
                        String prenom = json.getString("prenom");
                        String tele = json.getString("tele");
                        double solde = json.getDouble("solde");
                        String image=json.getString("image");

                        callback.onSuccess(new Vendeur(nom, prenom, tele, email, solde, id_vendeur, id_user,image));


                    } else {
                        callback.onErr(json.getString("message"));
                    }


                } catch (JSONException e) {
                    callback.onErr("Une erreur s'est produite");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof NetworkError) {
                    callback.onErr("impossible de se connecter");
                } else if (error instanceof VolleyError) {
                    callback.onErr("Une erreur s'est produite");
                }


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("pseudo", pseudo);
                map.put("password", password);

                return map;
            }
        };
        queue.add(request);
    }

    public void loadProductsDemandes(final Vendeur vendeur,final PDCallBack callBack){
        String url = Config.URL+"prodiutDemanader.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONArray jsonArray= new JSONArray(response);
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String nomProduit=jsonObject.getString("nomProduit").trim();
                        String image=jsonObject.getString("imag").trim();

                        int idProduit=jsonObject.getInt("idProduit");
                        int idDemande=jsonObject.getInt("idDemande");
                        int quantiteProduit=jsonObject.getInt("quantite");
                        int idVendeur=jsonObject.getInt("idVendeur");
                        int idClient=jsonObject.getInt("idClient");
                        vendeur.addToPrductsDemandes(new ProdutsAllreadyDemande(nomProduit,quantiteProduit,idClient,idVendeur,idDemande, idProduit,image));
                    }
                    callBack.onSuccess(vendeur);
                } catch (JSONException e) {
                    callBack.onErr("Une erreur s'est produite");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError)
                    callBack.onErr("Impoussible de se connecter");
                else if (error instanceof VolleyError)
                    callBack.onErr("Une erreur s'est produite");

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();

                map.put("idVendeur", vendeur.getId_vendeur()+"");
                return map;
            }
        };
        request.setTag(TAG);
        queue.add(request);
    }

    public void loadProductsReservation(final Vendeur vendeur,final PDCallBack callBack){
        String url = Config.URL+"produitReservation.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONArray jsonArray= new JSONArray(response);
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String nomProduit=jsonObject.getString("nomProduit").trim();
                        String image=jsonObject.getString("imag").trim();
                        int idProduit=jsonObject.getInt("idProduit");
                        int idReservation=jsonObject.getInt("idReservation");
                        int quantiteProduit=jsonObject.getInt("quantite");
                        int idVendeur=jsonObject.getInt("idVendeur");
                        int idClient=jsonObject.getInt("idClient");

                        vendeur.addToPrductsReservation(new ProoduitAllreadyReserver(nomProduit,quantiteProduit,idClient,idVendeur,idReservation, idProduit,image));
                    }
                    callBack.onSuccess(vendeur);
                } catch (JSONException e) {
                    callBack.onErr("Une erreur s'est produite");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError)
                    callBack.onErr("Impoussible de se connecter");
                else if (error instanceof VolleyError)
                    callBack.onErr("Une erreur s'est produite");

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("idVendeur", vendeur.getId_vendeur()+"");
                return map;
            }
        };
        request.setTag(TAG);
        queue.add(request);


    }

    public void loadProductsVendeur(final Vendeur vendeur,final AllProduitManager manager, final PVCallBack callBack){
        String url = Config.URL+"produitVendeur.php";

        StringRequest  request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {


                    Log.i("tagconvertstr", "["+response+"]");
                    JSONArray jsonArray= new JSONArray(response);
                    for (int i = 0; i <jsonArray.length()-1 ; i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String nomProduit=jsonObject.getString("nomProduit").trim();
                        String categorier=jsonObject.getString("categorier").trim();
                        String image=jsonObject.getString("imag").trim();
                        int idProduit=jsonObject.getInt("idProduit");
                        int quantiteProduit=jsonObject.getInt("quantite");
                        int idVendeur=jsonObject.getInt("idVendeur");

                        manager.addProduits(new ProduitsAllreadyVendeur( nomProduit, quantiteProduit,  idVendeur,  idProduit,categorier,image));
                    }
                    callBack.onSuccess(manager);
                } catch (JSONException e) {
                    callBack.onErr("Une erreur s'est produite");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError)
                    callBack.onErr("Impoussible de se connecter");
                else if (error instanceof VolleyError)
                    callBack.onErr("Une erreur s'est produite");

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("idVendeur", vendeur.getId_vendeur()+"");
                return map;
            }
        };

        queue.add(request);


    }

    public void Confirme(final int idD,final int qnt,final int idVen,final int idProd, final ConfermeDEclineCallBack callBack){

        String url = Config.URL+"confirmerVendeurD.php?qnt="+qnt+"&idProd="+idProd+"&idVen="+idVen+"&idD="+idD;

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json = null;
                try {

                    json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error){
                        callBack.onSuccess("Bien Confirmer");
                    }else{
                        callBack.onErr(json.getString("messages"));
                    }
                } catch (JSONException e) {
                    callBack.onErr("Une erreur s'est produite");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError)
                    callBack.onErr("Impoussible de se connecter");
                else if (error instanceof VolleyError)
                    callBack.onErr("Une erreur s'est produite");

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                return map;
            }
        };

        queue.add(request);

    }

    public void decline(final int idD, final ConfermeDEclineCallBack callBack){

        String url = Config.URL+"declineVendeurD.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json = null;
                try {

                    json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error){
                        callBack.onSuccess("Bien Rejeter");
                    }else{
                        callBack.onErr(json.getString("messages"));
                    }
                } catch (JSONException e) {
                    callBack.onErr("Une erreur s'est produite");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError)
                    callBack.onErr("Impoussible de se connecter");
                else if (error instanceof VolleyError)
                    callBack.onErr("Une erreur s'est produite");

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("idD", idD+"");

                return map;
            }
        };

        queue.add(request);

    }
    public void ConfirmeR(final int idR,final int qnt,final int idVen,final int idProd, final ConfermeDEclineCallBack callBack){

        String url = Config.URL+"confirmerVendeurR.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json = null;
                try {

                    json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error){
                        callBack.onSuccess("Bien Confirmer");
                    }else{
                        callBack.onErr(json.getString("messages"));
                    }
                } catch (JSONException e) {
                    callBack.onErr("Une erreur s'est produite");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError)
                    callBack.onErr("Impoussible de se connecter");
                else if (error instanceof VolleyError)
                    callBack.onErr("Une erreur s'est produite");

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("idR", idR+"");
                map.put("qnt", qnt+"");
                map.put("idProd", idProd+"");
                map.put("idVen", idVen+"");

                return map;
            }
        };

        queue.add(request);

    }

    public void declineR(final int idR, final ConfermeDEclineCallBack callBack){

        String url = Config.URL+"declineVendeurV.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json = null;
                try {

                    json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error){
                        callBack.onSuccess("Bien Rejeter");
                    }else{
                        callBack.onErr(json.getString("messages"));
                    }
                } catch (JSONException e) {
                    callBack.onErr("Une erreur s'est produite");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError)
                    callBack.onErr("Impoussible de se connecter");
                else if (error instanceof VolleyError)
                    callBack.onErr("Une erreur s'est produite");

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("idR", idR+"");

                return map;
            }
        };

        queue.add(request);

    }

    public void nombreNotification(final Vendeur v, final LoginCallBack callback) {


        String url = Config.URL+"rz.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json = null;
                // Log.d("APP", response);
                try {

                    json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error) {

                        int nb = json.getInt("nb");
                        v.setNombreNoti(nb);
                        callback.onSuccess(v);

                    } else {
                        callback.onErr(json.getString("message"));
                    }


                } catch (JSONException e) {
                    callback.onErr("Une erreur s'est produite");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.d("APP", "ERROR = " + error);

                if (error instanceof NetworkError) {
                    callback.onErr("il ya un erreurs concerne le nombre des notification");
                } else if (error instanceof VolleyError) {
                    callback.onErr("il ya un erreurs concerne le nombre des notification");
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("idV", v.getId_vendeur()+"");

                return map;
            }
        };
        queue.add(request);
    }

    public void UploadProfileImg(final String image,final int idU, final ConfermeDEclineCallBack callBack){

        String url = Config.URL+"uplodeProfileVendeur.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json = null;
                try {

                    json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error){
                        callBack.onSuccess("Bien Ajouter");
                    }else{
                        callBack.onErr(json.getString("messages"));
                    }
                } catch (JSONException e) {
                    callBack.onErr("Une erreur s'est produite");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError)
                    callBack.onErr("Impoussible de se connecter");
                else if (error instanceof VolleyError)
                    callBack.onErr("Une erreur s'est produite");

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("idU", idU+"");
                map.put("img", image);
                return map;
            }
        };

        queue.add(request);

    }

    public void uplodeCentreZone(Vendeur v, final LoginCallBack callback) {


        String url = Config.URL+"rz.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json = null;
                try {
                    Log.i("Tagconvert", "[[[" + response + "]]]");
                    json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error) {



                        double centre_position_x = json.getDouble("centre_position_x");
                        double centre_position_y = json.getDouble("centre_position_y");
                        v.centreMapZoneVendeur(new PointMap(centre_position_x,centre_position_y));
                        callback.onSuccess(v);

                    } else {
                        callback.onErr(json.getString("message"));
                    }


                } catch (JSONException e) {
                    callback.onErr("Une erreur s'est produite");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof NetworkError) {
                    callback.onErr("impossible de se connecter");
                } else if (error instanceof VolleyError) {
                    callback.onErr("Une erreur s'est produite");
                }


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("pseudo", v.getId_vendeur()+"");

                return map;
            }
        };
        queue.add(request);
    }

    public void kill(){
        if (queue!=null){
            queue.cancelAll(TAG);
        }
    }

}
