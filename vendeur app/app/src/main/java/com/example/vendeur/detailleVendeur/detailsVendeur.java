package com.example.vendeur.detailleVendeur;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.vendeur.Config;
import com.example.vendeur.produitsVendeur.AllProduitManager;
import com.example.vendeur.MainActivity;
import com.example.vendeur.volley_requests.MyRequest;
import com.example.vendeur.produitsVendeur.ProduitVendeur;
import com.example.vendeur.produitsVendeur.ProduitVendeurAdapter;
import com.example.vendeur.produitsVendeur.ProduitsAllreadyVendeur;
import com.example.vendeur.R;
import com.example.vendeur.espaceVendeur.SessionManager;
import com.example.vendeur.espaceVendeur.Vendeur;
import com.example.vendeur.volley_requests.VolleySingleton;
import com.example.vendeur.espaceVendeur.espaceVendeur;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Vector;
//this is the all prodcts vendeur and button deconnection
public class detailsVendeur extends AppCompatActivity {
    JSONArray jsonArray= null;
    private Activity context;
    private SessionManager sessionManager;
    private ProduitsAllreadyVendeur produitsAll;
    AllProduitManager produtsManager;
    private Vendeur vendeur;
    private MyRequest myRequest;
    private RequestQueue queue;
    private MyRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//l'initialisation de view
        setContentView(R.layout.activity_details_vendeur);
//l'initialisation de spiner
        Spinner spinner= findViewById(R.id.categorer);
        produtsManager=new AllProduitManager();
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.categ,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        TextView b;
        context = this;
        //creat new object sessionmanager
        sessionManager=new SessionManager(this);
        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);
        //declaration the button deconexion
        b = findViewById(R.id.deconecter);
        b.setOnClickListener(new View.OnClickListener() {
            //if onclick on button deconexion
            @Override
            public void onClick(View v) {
                sessionManager.logout();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        sessionManager=new SessionManager(this);
        if (sessionManager.isLogged()) {
            //load the all prodect vendeur
            getJSON(Config.URL+"produitVendeur.php?idVendeur="+sessionManager.getVendeur().getId_vendeur());
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                    //initalisation of liste the prodects vendeur
                    ArrayList<ProduitVendeur> data=new ArrayList<ProduitVendeur>();
                    Spinner sp=(Spinner) findViewById(R.id.categorer);
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);

                    switch (position) {
                        case 1:

                            data = showListe(produtsManager, "kg");

                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                            sp.setBackgroundDrawable(getResources().getDrawable(R.drawable.shep_));
                            break;
                        case 2:
                            data = showListe(produtsManager, "unité");
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                            sp.setBackgroundDrawable(getResources().getDrawable(R.drawable.shep_));

                            break;
                        case 3:
                            data = showListe(produtsManager, "l");

                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                            sp.setBackgroundDrawable(getResources().getDrawable(R.drawable.shep_));
                            break;
                    }
                    ProduitVendeurAdapter adapter1 = new ProduitVendeurAdapter(context, data);
                    //initailisation the listeView the prodct by category
                    ListView listView = (ListView) findViewById(R.id.listview);
                    listView.setAdapter(adapter1);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }


    }


    public void espaceven(View view){
        Intent i = new Intent(this, espaceVendeur.class);
        startActivity(i);
        finish();

    }
    //chose the liste prodect vendeur by category
    public ArrayList<ProduitVendeur> showListe(AllProduitManager manager, String categ){
        ArrayList<ProduitVendeur> arrayList = new ArrayList<ProduitVendeur>();
        arrayList.clear();
        Vector<ProduitsAllreadyVendeur> prods = manager.getProduitsByCategorer(categ);
        for (int i = 0; i < prods.size(); i++) {
            ProduitsAllreadyVendeur prod = prods.get(i);
            arrayList.add(new ProduitVendeur(prod.getNomProduit(), prod.getQuantiteProduit(), prod.getImage(),prod.getCategorier()));
        }
        return arrayList;
    }
    //get the all prodect vendeur by data base
    private void getJSON(final String UrlWebService){
        class GetJSON extends AsyncTask< Void, Void,String>{
            public static final String REQUEST_METHOD = "GET";
            JSONObject postData;
            @Override
            protected String doInBackground(Void... voids) {
                String json="";
                try {
                    URL url = new URL(UrlWebService);
                    URLConnection con = url.openConnection();
                    InputStreamReader inputStreamReader=new InputStreamReader(con.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    json=bufferedReader.readLine();
                    jsonArray=new JSONArray(json);
                    for (int i = 0; i <jsonArray.length()-1 ; i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String nomProduit=jsonObject.getString("nomProduit").trim();
                        String categorier=jsonObject.getString("categorier").trim();
                        String image=jsonObject.getString("imag").trim();
                        int idProduit=jsonObject.getInt("idProduit");
                        int quantiteProduit=jsonObject.getInt("quantite");
                        int idVendeur=jsonObject.getInt("idVendeur");
                        produtsManager.addProduits(new ProduitsAllreadyVendeur( nomProduit, quantiteProduit,  idVendeur,  idProduit,categorier,image));
                    }
                }catch (Exception e){
                    return  null;
                }
                return null;
            }

        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();

    }
    //button retur in the espace vendeur
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,espaceVendeur.class);
        startActivity(i);
        super.onBackPressed();
    }
}
