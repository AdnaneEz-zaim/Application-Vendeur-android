package com.example.vendeur.produitsNotification;

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

public class ProduitsNotificationAdapter extends ArrayAdapter<ProduitVendeurNotification> {
    Activity context;
    SessionManager sessionManager;
    Bitmap bitmap;
    ArrayList<ProduitVendeurNotification> produitVendeurNotificationArrayList;
    RequestOptions option;

    public ProduitsNotificationAdapter(Activity context, ArrayList<ProduitVendeurNotification> androidFlavors) {

        super(context,0,androidFlavors);
        this.context=context;
        this.produitVendeurNotificationArrayList=androidFlavors;
        option = new RequestOptions().placeholder(R.drawable.white_shape).error(R.drawable.white_shape);

    }

    public ProduitsNotificationAdapter(MyRequest.PDCallBack context, ArrayList<ProduitVendeurNotification> androidFlavors) {

        super((Context) context, 0, androidFlavors);
    }

    public ProduitsNotificationAdapter(MyRequest.PVCallBack  context, ArrayList<ProduitVendeurNotification> androidFlavors) {

        super((Context) context, 0, androidFlavors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.produits_vendeur_notification, parent, false);
        }
        ProduitVendeurNotification currentAndroidFlavor = getItem(position);
        TextView nomProduit = (TextView) listItemView.findViewById(R.id.nomProdN);
        nomProduit.setText(currentAndroidFlavor.getNomProd());
        TextView quantite = (TextView) listItemView.findViewById(R.id.quantProdN);
        quantite.setText("" + currentAndroidFlavor.getQuantiteProd());
        ImageView img = (ImageView) listItemView.findViewById(R.id.imageProdN);
        Glide.with(context).load(currentAndroidFlavor.getImageProd()).apply(option).into(img);
        //img.setImageResource(currentAndroidFlavor.getImageProd());
        TextView unite = (TextView) listItemView.findViewById(R.id.uniterN);
        unite.setText(""+currentAndroidFlavor.getCateg());
        LinearLayout linearLayout=(LinearLayout) listItemView.findViewById(R.id.containerN);
        return listItemView;
    }


}