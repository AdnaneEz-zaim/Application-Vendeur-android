package com.example.vendeur.reservation;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.vendeur.volley_requests.MyRequest;
import com.example.vendeur.R;
import com.example.vendeur.volley_requests.VolleySingleton;
import com.example.vendeur.espaceVendeur.SessionManager;
import com.example.vendeur.espaceVendeur.Vendeur;

import java.util.ArrayList;
import java.util.Vector;

//====> this is the fragment reservation
/**
 * A simple {@link Fragment} subclass.
 */
public class Reservation extends Fragment {
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reservation, container, false);//fragmant_demande

        // view = inflater.inflate(R.layout.fragment_demande, container, false);
        //queue of Volley request
        queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
        //object of MyRequest class
        request = new MyRequest(getActivity(), queue);
        //sessionManager contains all the user informations
        sessionManager = new SessionManager(getActivity());
        //swipeRefreshLayout allows us to refresh the fragment
        swipeRefreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.swipeR);
        swipeRefreshLayout. setColorSchemeColors (Color.rgb(244,67,54), Color.rgb(244,67,54), Color.rgb(244,67,54), Color.rgb(244,67,54));


        if (sessionManager.isLogged()) {
            //initialize the vendeur object with his informations stored in session manager
            vendeur = sessionManager.getVendeur();
            vendeur.clearReserves();
            //loadProductsDemandes(client);
            request.loadProductsReservation(vendeur, new MyRequest.PDCallBack() {
                @Override
                public void onSuccess(Vendeur vendeur) {
                    sessionManager.insertVendeur(vendeur);
                    // Log.i(TAG, "onCreateView: " + client.getProductsDemande().size());
                    /**
                     * Now we will get the products coming from the server and storing in vector of "ProductsAllreadyReserved"
                     * and then we will loop and store each one inside a vector of "ProduitReserve"
                     * because the object of "ProduitReserve" will be shown in the listview
                     */
                    ArrayList<ProduitReserver> arrayList = new ArrayList<ProduitReserver>();
                    Vector<ProoduitAllreadyReserver> prods = vendeur.getProductsReserver();
                    for (int i = 0; i < prods.size(); i++) {
                        ProoduitAllreadyReserver prod = prods.get(i);
                        arrayList.add(new ProduitReserver(prod.getNomProduit(), prod.getQuantiteProduit(), prod.getImage(),prod.getIdReservation()));
                    }

                    ProduitReserverAdapter adapter = new ProduitReserverAdapter(getActivity(), arrayList);

                    ListView listView = (ListView) view.findViewById(R.id.listviewR);
                    listView.setAdapter(adapter);
                }

                @Override
                public void onErr(String message) {
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                }
            });
            /**
             * now if the user try to refresh the fragment we will do the same process again
             */
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    vendeur.clearReserves();
                    request.loadProductsReservation(vendeur, new MyRequest.PDCallBack() {
                        @Override
                        public void onSuccess(Vendeur vendeur) {
                            sessionManager.insertVendeur(vendeur);
                            ArrayList<ProduitReserver> arrayList = new ArrayList<ProduitReserver>();
                            Vector<ProoduitAllreadyReserver> prods = vendeur.getProductsReserver();
                            for (int i = 0; i < prods.size(); i++) {
                                ProoduitAllreadyReserver prod = prods.get(i);
                                arrayList.add(new ProduitReserver(prod.getNomProduit(), prod.getQuantiteProduit(), prod.getImage(),prod.getIdReservation()));
                            }

                            ProduitReserverAdapter adapter = new ProduitReserverAdapter(getActivity(), arrayList);

                            ListView listView = (ListView) view.findViewById(R.id.listviewR);
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
                            //setColorSchemeColors(int… colors)
                            swipeRefreshLayout. setColorSchemeColors (Color.rgb(244,67,54), Color.rgb(244,67,54), Color.rgb(244,67,54), Color.rgb(244,67,54));
                            swipeRefreshLayout.setRefreshing(false);
                            //swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
                        }
                    },2000);//2000df

                }
            });
        }

        return view;
    }
    @Override
    public void onStop() {
        super.onStop();
        request.kill();
    }

}