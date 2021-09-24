package com.example.vendeur.demande;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.vendeur.volley_requests.MyRequest;
import com.example.vendeur.R;
import com.example.vendeur.espaceVendeur.SessionManager;
import com.example.vendeur.espaceVendeur.Vendeur;
import com.example.vendeur.volley_requests.VolleySingleton;

import java.util.ArrayList;
import java.util.Vector;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
/*
    this is the fragment Demande
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class Demande extends Fragment {
    private View view;

    private Context context;
    private SessionManager sessionManager;
    private Vendeur vendeur;
    private MyRequest myRequest;
    private RequestQueue queue;
    private MyRequest request;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_demande, container, false);
        //queue of Volley request
        queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
        //object of MyRequest class
        request = new MyRequest(getActivity(), queue);
        //sessionManager contains all the user informations
        sessionManager = new SessionManager(getActivity());

        //swipeRefreshLayout allows us to refresh the fragment
        swipeRefreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.swipeD);
        swipeRefreshLayout. setColorSchemeColors (Color.rgb(244,67,54), Color.rgb(244,67,54), Color.rgb(244,67,54), Color.rgb(244,67,54));

        //testing if the user is logged in
        if (sessionManager.isLogged()) {
            //initialize the vendeur object with his informations stored in session manager
            vendeur = sessionManager.getVendeur();
            //clearing the demandes before loading new ones from the server
            vendeur.clearDemandes();
            //loading his demandes from the server
            request.loadProductsDemandes(vendeur, new MyRequest.PDCallBack() {
                @Override
                public void onSuccess(Vendeur vendeur) {
                    //in case of the request was done successfully
                    //inserting the new demandes in the session manager
                    sessionManager.insertVendeur(vendeur);
                     /*
                          Now we will get the products coming from the server and storing in vector of "ProdutsAllreadyDemande"
                          and then we will loop and store each one inside a vector of "ProduitDemande"
                          because the object of "ProduitDemande" will be shown in the listview
                     */
                    ArrayList<ProduitDemande> arrayList = new ArrayList<ProduitDemande>();
                    Vector<ProdutsAllreadyDemande> prods = vendeur.getProductsDemande();
                    for (int i = 0; i < prods.size(); i++) {
                        ProdutsAllreadyDemande prod = prods.get(i);
                        arrayList.add(new ProduitDemande(prod.getNomProduit(), prod.getQuantiteProduit(), prod.getImage(),prod.getIdDemande()));
                    }
                    ProduitDemandeAdapter adapter = new ProduitDemandeAdapter(getActivity(), arrayList);

                    ListView listView = (ListView) view.findViewById(R.id.listviewD);
                    listView.setAdapter(adapter);
                }

                @Override
                public void onErr(String message) {
                    //in case of the request was failed
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                }
            });

            /*
                now if the user try to refresh the fragment we will do the same process again
             */
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    vendeur.clearDemandes();
                    request.loadProductsDemandes(vendeur, new MyRequest.PDCallBack() {
                        @Override
                        public void onSuccess(Vendeur vendeur) {
                            sessionManager.insertVendeur(vendeur);
                            ArrayList<ProduitDemande> arrayList = new ArrayList<ProduitDemande>();
                            Vector<ProdutsAllreadyDemande> prods = vendeur.getProductsDemande();
                            for (int i = 0; i < prods.size(); i++) {
                                ProdutsAllreadyDemande prod = prods.get(i);
                                arrayList.add(new ProduitDemande(prod.getNomProduit(), prod.getQuantiteProduit(), prod.getImage(),prod.getIdDemande()));
                            }
                            ProduitDemandeAdapter adapter = new ProduitDemandeAdapter(getActivity(), arrayList);

                            ListView listView = (ListView) view.findViewById(R.id.listviewD);
                            listView.setAdapter(adapter);
                        }

                        @Override
                        public void onErr(String message) {
                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        }
                    });

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //setRefreshing(false) : indique à la vue que le contenu de la ListView à été mise à jour
                            swipeRefreshLayout. setColorSchemeColors (Color.rgb(244,67,54), Color.rgb(244,67,54), Color.rgb(244,67,54), Color.rgb(244,67,54));
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    },2000);//2000
                }
            });
        }

        return view;
    }

    @Override
    public void onStop() {
        //if the fragment has died we cancel the process of the request
        super.onStop();
        request.kill();
    }
}