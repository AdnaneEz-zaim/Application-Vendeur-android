package com.example.vendeur.reservation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.vendeur.volley_requests.MyRequest;
import com.example.vendeur.R;
import com.example.vendeur.volley_requests.VolleySingleton;
import com.example.vendeur.espaceVendeur.SessionManager;
import com.example.vendeur.espaceVendeur.Vendeur;

import java.util.ArrayList;
import java.util.Map;

import androidx.viewpager.widget.ViewPager;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProduitReserverAdapter extends ArrayAdapter<ProduitReserver> {
    Activity context;
    RequestQueue queue;
    MyRequest request;
    SessionManager sessionManager;
    Bitmap bitmap;
    RequestOptions option;
    ArrayList<ProduitReserver> produitReservers;
    public ProduitReserverAdapter(Activity context, ArrayList<ProduitReserver> androidFlavors) {
        super(context, 0, androidFlavors);
        this.context=context;
        this.produitReservers=androidFlavors;
        option = new RequestOptions().placeholder(R.drawable.white_shape).error(R.drawable.white_shape);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.produit_reserve, parent, false);
        }
        //queue of Volley request
        queue = VolleySingleton.getInstance(context).getRequestQueue();
        //object of MyRequest class
        request=new MyRequest(context,queue);
        //session manager that contains the user's informations
        sessionManager=new SessionManager(context);


        ProduitReserver currentAndroidFlavor = getItem(position);
        /**
         * inserting the ProduitReserve's informations in the views
         */
        TextView nomProduit = (TextView) listItemView.findViewById(R.id.nomProdR);
        nomProduit.setText(currentAndroidFlavor.getNomProd());
        TextView quantite = (TextView) listItemView.findViewById(R.id.quantProdR);
        quantite.setText(""+currentAndroidFlavor.getQuantiteProd());
        /**
         * inserting the ProduitDemande's informations in the views
         */
        TextView toClient = (TextView) listItemView.findViewById(R.id.voirClient);
        toClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.setComande(currentAndroidFlavor.getIdReserve(),"Reservation");
                ViewPager viewPager=(ViewPager) context.findViewById(R.id.view_pager);
                viewPager.setCurrentItem( 2,  true);
            }
        });


        ImageView img = (ImageView) listItemView.findViewById(R.id.imageProdR);
        //img.setImageResource(currentAndroidFlavor.getImageProd());
        //loading the image from the url
        Glide.with(context).load(currentAndroidFlavor.getImageProd()).apply(option).into(img);

        Button b1 = (Button) listItemView.findViewById(R.id.confirmeR);
        b1.setBackgroundResource(R.drawable.iconfinder_tic);

        Button b2 = (Button) listItemView.findViewById(R.id.declineR);
        b2.setBackgroundResource(R.drawable.iconfinder_freebie);
        //Find and listen on both the button confirm and decline
        final Button confirme= (Button) listItemView.findViewById(R.id.confirmeR);
        final Button decline= (Button) listItemView.findViewById(R.id.declineR);

        try {
            //si onclick sur le button de la confirmation de la reservation
            confirme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove(getItem(position));
                    notifyDataSetChanged();
                    sessionManager.decreasesReserve();
                    Vendeur vendeur=sessionManager.getVendeur();
                    request.ConfirmeR(sessionManager.getVendeur().getProductsReserver().get(position).getIdReservation(),sessionManager.getVendeur().getProductsReserver().get(position).getQuantiteProduit(),sessionManager.getVendeur().getId_vendeur(),sessionManager.getVendeur().getProductsReserver().get(position).getIdProduit(), new MyRequest.ConfermeDEclineCallBack()  {
                        @Override
                        public void onSuccess(String message) {
                            // confirme.setBackgroundResource(R.drawable.iconfinder_tic);

                        }

                        @Override
                        public void inputErr(Map<String, String> errors) {

                        }

                        @Override
                        public void onErr(String message) {

                        }
                    });
                    //Toast.makeText(getContext(),"la reservation est bient confirmer",Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.i(TAG, "getView: "+e.getMessage());
        }
        try {
            //si onclick sur le button de la confirmation de la reservation
            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder altert=new AlertDialog.Builder(getContext());
                    altert.setTitle("annuler réservation");
                    altert.setMessage("vous voulez annuler cette réservation !");
                    altert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            remove(getItem(position));
                            notifyDataSetChanged();
                            sessionManager.decreasesReserve();
                            request.declineR(sessionManager.getVendeur().getProductsReserver().get(position).getIdReservation(), new MyRequest.ConfermeDEclineCallBack(){
                                @Override
                                public void onSuccess(String message) {
                                    // decline.setBackgroundResource(R.drawable.iconfinder_freebie);

                                }

                                @Override
                                public void inputErr(Map<String, String> errors) {}

                                @Override
                                public void onErr(String message) {

                                }
                            });
                            Toast.makeText(getContext(),"la reservation est bient annuller",Toast.LENGTH_SHORT).show();
                        }
                    });
                    altert.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getContext(),"la reservation ne pas annuller",Toast.LENGTH_SHORT).show();
                        }
                    });

                    altert.create().show();
                }
            });


        }catch (Exception e){
            Log.i(TAG, "getView: "+e.getMessage());
        }
        return listItemView;

    }

}
