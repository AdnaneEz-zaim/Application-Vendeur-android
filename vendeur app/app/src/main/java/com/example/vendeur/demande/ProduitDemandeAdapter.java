package com.example.vendeur.demande;

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
import com.example.vendeur.espaceVendeur.SessionManager;
import com.example.vendeur.espaceVendeur.Vendeur;
import com.example.vendeur.volley_requests.VolleySingleton;

import java.util.ArrayList;
import java.util.Map;

import androidx.viewpager.widget.ViewPager;

import static androidx.constraintlayout.widget.Constraints.TAG;
//this class manage all the products DEMANDE
public class ProduitDemandeAdapter extends ArrayAdapter<ProduitDemande>  {
    Activity context;
    RequestQueue queue;
    MyRequest request;
    ArrayList<ProduitDemande> produitDemandes;
    SessionManager sessionManager;
    Bitmap bitmap;
    RequestOptions option;
    public ProduitDemandeAdapter(Activity context, ArrayList<ProduitDemande> androidFlavors) {

        super(context, 0, androidFlavors);
        this.context = context;
        this.produitDemandes=androidFlavors;
        option = new RequestOptions().placeholder(R.drawable.white_shape).error(R.drawable.white_shape);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        LayoutInflater inflater = (context).getLayoutInflater ();
        View row = inflater.inflate(R.layout.produit_demande,parent,false);

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.produit_demande, parent, false);
        }
        //queue of Volley request
        queue = VolleySingleton.getInstance(context).getRequestQueue();
        //object of MyRequest class
        request=new MyRequest(context,queue);
        //session manager that contains the user's informations
        sessionManager=new SessionManager(context);
        //get the current ProduitDemande
        ProduitDemande currentAndroidFlavor = getItem(position);
        /**
         * inserting the ProduitReserve's informations in the views
         */
        TextView nomProduit = (TextView) listItemView.findViewById(R.id.nomProd);
        nomProduit.setText(currentAndroidFlavor.getNomProd());
        TextView quantite = (TextView) listItemView.findViewById(R.id.quantProd);
        quantite.setText(currentAndroidFlavor.getQuantiteProd()+"");

        /**
         * inserting the ProduitDemande's informations in the views
         */
        TextView toClient= (TextView) listItemView.findViewById(R.id.voirClient);
        toClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.setComande(currentAndroidFlavor.getIdDemande(),"demande");
                ViewPager viewPager=(ViewPager) context.findViewById(R.id.view_pager);
                viewPager.setCurrentItem( 2,  true);
            }
        });


        ImageView img = (ImageView) listItemView.findViewById(R.id.imageProd);
        //loading the image from the url
        Glide.with(context).load(currentAndroidFlavor.getImageProd()).apply(option).into(img);
        //declaration and initialization the button "confirmation"
        Button b1 = (Button) listItemView.findViewById(R.id.confirme);
        b1.setBackgroundResource(R.drawable.iconfinder_tic);//currentAndroidFlavor.getIcon().getIconConfirme()
        //declaration and initialization the button "decline"
        Button b2 = (Button) listItemView.findViewById(R.id.decline);
        b2.setBackgroundResource(R.drawable.iconfinder_freebie);//currentAndroidFlavor.getIcon().getIconDecline()

        //Find and listen on both the button confirm and decline
        final Button decline=(Button)listItemView.findViewById(R.id.decline);
        final Button confirmer=(Button)listItemView.findViewById(R.id.confirme);

        try {
//if onclik in the tutton confirmation
            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //the dialog the confirirmation the decline the demande
                    AlertDialog.Builder altert=new AlertDialog.Builder(getContext());
                    altert.setTitle("annulation demande");
                    altert.setMessage("vous voulez annuler cette demande !");
                    altert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //remove the position of this demande
                            remove(getItem(position));
                            notifyDataSetChanged();
                            sessionManager.decreasesDemande();
                            Vendeur vendeur=sessionManager.getVendeur();
                            request.decline(sessionManager.getVendeur().getProductsDemande().get(position).getIdDemande(), new MyRequest.ConfermeDEclineCallBack() {
                                @Override
                                public void onSuccess(String message) {
                                }

                                @Override
                                public void inputErr(Map<String, String> errors) {

                                }

                                @Override
                                public void onErr(String message) {

                                }
                            });

                            Toast.makeText(getContext(),"la demande est bient annuller",Toast.LENGTH_SHORT).show();
                        }
                    });
                    altert.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getContext(),"la demande ne pas annuller",Toast.LENGTH_SHORT).show();
                        }
                    });

                    altert.create().show();

                }
            });
        }catch (Exception e){
            Log.i(TAG, "getView: "+e.getMessage());
        }
        try {
//if onclik in the tutton confirmation
            confirmer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove(getItem(position));
                    notifyDataSetChanged();
                    sessionManager.decreasesDemande();
                    Vendeur vendeur=sessionManager.getVendeur();
                    request.Confirme(sessionManager.getVendeur().getProductsDemande().get(position).getIdDemande(),sessionManager.getVendeur().getProductsDemande().get(position).getQuantiteProduit(),sessionManager.getVendeur().getId_vendeur(),sessionManager.getVendeur().getProductsDemande().get(position).getIdProduit(), new MyRequest.ConfermeDEclineCallBack() {
                        @Override
                        public void onSuccess(String message) {
                        }

                        @Override
                        public void inputErr(Map<String, String> errors) {
                        }

                        @Override
                        public void onErr(String message) {
                        }
                    });

                    Toast.makeText(getContext(),"la demande est bient confirmer",Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){

        }

        return listItemView;
    }

}
