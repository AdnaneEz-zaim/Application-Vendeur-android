package com.example.vendeur.produitsVendeur;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.vendeur.volley_requests.MyRequest;
import com.example.vendeur.R;
import com.example.vendeur.espaceVendeur.SessionManager;

import java.util.ArrayList;

public class ProduitVendeurAdapter extends ArrayAdapter<ProduitVendeur> {
    Activity context;
    SessionManager sessionManager;
    Bitmap bitmap;
    ArrayList<ProduitVendeur> produitVendeurArrayList;
    RequestOptions option;
    //constrecter
    public ProduitVendeurAdapter(Activity context, ArrayList<ProduitVendeur> androidFlavors) {

        super(context, 0, androidFlavors);
        this.context=context;
        this.produitVendeurArrayList=androidFlavors;
        option = new RequestOptions().placeholder(R.drawable.white_shape).error(R.drawable.white_shape);

    }
    public ProduitVendeurAdapter(MyRequest.PDCallBack context, ArrayList<ProduitVendeur> androidFlavors) {

        super((Context) context, 0, androidFlavors);
    }
    public ProduitVendeurAdapter(MyRequest.PVCallBack  context, ArrayList<ProduitVendeur> androidFlavors) {

        super((Context) context, 0, androidFlavors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.produits_vendeur, parent, false);
        }
        ProduitVendeur currentAndroidFlavor = getItem(position);
        /**
         * inserting the prodect informations in the views
         */
        TextView nomProduit = (TextView) listItemView.findViewById(R.id.nomProd);
        nomProduit.setText(currentAndroidFlavor.getNomProd());
        TextView quantite = (TextView) listItemView.findViewById(R.id.quantProd);
        quantite.setText("" + currentAndroidFlavor.getQuantiteProd());
        ImageView img = (ImageView) listItemView.findViewById(R.id.imageProd);
        Glide.with(context).load(currentAndroidFlavor.getImageProd()).apply(option).into(img);
        //img.setImageResource(currentAndroidFlavor.getImageProd());
        TextView unite = (TextView) listItemView.findViewById(R.id.uniter);
        unite.setText(""+currentAndroidFlavor.getCateg());
        LinearLayout linearLayout=(LinearLayout) listItemView.findViewById(R.id.container);
        return listItemView;
    }


}

