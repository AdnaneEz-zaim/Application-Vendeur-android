package com.example.vendeur.espaceVendeur;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

//import com.amazonaws.http.UrlHttpClient;
import com.android.volley.RequestQueue;
import com.example.vendeur.Config;
import com.example.vendeur.produitsVendeur.AllProduitManager;
import com.example.vendeur.volley_requests.MyRequest;
import com.example.vendeur.produitsVendeur.ProduitVendeur;
import com.example.vendeur.produitsVendeur.ProduitsAllreadyVendeur;
import com.example.vendeur.produitsNotification.ProduitsNotification;
import com.example.vendeur.R;
import com.example.vendeur.volley_requests.VolleySingleton;
import com.example.vendeur.detailleVendeur.detailsVendeur;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vendeur.ui.main.SectionsPagerAdapter;
import com.yalantis.ucrop.UCrop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
//the vendeur SPACE, you can say it is the main activity
public class espaceVendeur extends AppCompatActivity {

    private int comp=0;
    private Context context;
    private SessionManager sessionManager;
    private MyRequest myRequest;
    private RequestQueue queue;
    private CircleImageView imageprofile;
    private final int CODE_IMG_GALORIE=1;
    private final String SIPLE_GROUBDEE_IMAGE="SimpleCropImg";
    Bitmap bitmap;

    private TextView NombrProduitNoti;
    private ProduitsAllreadyVendeur produitsAll;
    AllProduitManager produtsManager;
    ArrayList<ProduitVendeur> data=new ArrayList<ProduitVendeur>();
    JSONArray jsonArray= null;
    final Handler handler = new Handler();
    Timer timer = new Timer();

    GetJSON getJSON;
    ForNotif notif;
    int current=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_vendeur);
        //session manager that contains the user's informations
        sessionManager = new SessionManager(this);


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        produtsManager=new AllProduitManager();


        //queue of Volley request
        queue = VolleySingleton.getInstance(this).getRequestQueue();
        //object of MyRequest class
        myRequest=new MyRequest(this,queue);
        //Initialize the views
        imageprofile=(CircleImageView) findViewById(R.id.imageProfile);
        TextView nom = (TextView) findViewById(R.id.nomVendeur);
        TextView email = (TextView) findViewById(R.id.emailVendeur);
        TextView solde = (TextView) findViewById(R.id.soldeVendeur);
        //LE JNOMBRE QUI EST AFFICHER DANS LA NOTIFICATION DES PRODUITS FAIBLE ON QUANTITIE
        NombrProduitNoti = (TextView) findViewById(R.id.compture);
        //testing if the user is logged in
        if (sessionManager.isLogged()) {
            //geting the vendeur informations and put them in the views
            final Vendeur vendeur;
            vendeur = sessionManager.getVendeur();
            email.setText(vendeur.getEmail());
            nom.setText(vendeur.getNom() + " " + vendeur.getPrenom());


            getJSON = new GetJSON(Config.URL+"produitVendeur.php?idVendeur="+sessionManager.getVendeur().getId_vendeur());
            getJSON.execute();
            notif=new ForNotif(Config.URL+"produits_notif.php?idVendeur="+sessionManager.getVendeur().getId_vendeur());
            notif.execute();




            solde.setText(vendeur.getSolde() + "");
            if(vendeur.getSolde()<2000.0)
                solde.setTextColor(Color.RED);
            //Loading the profile image from the server
            LoadImage loadImage=new LoadImage(imageprofile);
            loadImage.execute(sessionManager.getVendeur().getImageProfile());
            imageprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(
                            new Intent().setAction(Intent.ACTION_GET_CONTENT).setType("image/*"),CODE_IMG_GALORIE
                    );
                }
            });
            vendeur.getProductsVendeur().size();
        }

    }
    //initialisation the picture the vendeur
    private void init(){
        this.imageprofile = (CircleImageView) findViewById(R.id.imageProfile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CODE_IMG_GALORIE && resultCode==RESULT_OK){
            Uri imageUri=data.getData();
            if(imageUri!=null){
                startCrop(imageUri);

            }
        }
        else if(requestCode == UCrop.REQUEST_CROP && resultCode==RESULT_OK){
            Uri imageRusulteCrop= UCrop.getOutput(data);
            if(imageRusulteCrop!=null){
                try {
                    bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageRusulteCrop);
                    imageprofile.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Upload the image to the server
                uploadImage(ImageToString(bitmap),sessionManager.getVendeur().getId_user());
                //get the image stored in the server to insert it in session manager
                GetLink getLink = new GetLink();
                getLink.execute(Config.URL+"getImg.php?idU="+sessionManager.getVendeur().getId_user());
            }
        }

    }
    /**
     * this method allows the client to crop his image before inserting it
     * @param uri
     */
    private void startCrop(@Nullable Uri uri){
        String destinationFileName = SIPLE_GROUBDEE_IMAGE;
        destinationFileName+=".jpg";

        UCrop uCrop= UCrop.of(uri,Uri.fromFile(new File(getCacheDir(),destinationFileName)));
        uCrop.withAspectRatio(1,1);
        uCrop.withMaxResultSize(450, 450);
        uCrop.withOptions(getCropOptions());
        uCrop.start(espaceVendeur.this);
    }
    private UCrop.Options getCropOptions(){
        UCrop.Options options=new UCrop.Options();
        options.setCompressionQuality(70);
        //options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(false);
        options.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        options.setToolbarTitle("Recotarte imager");
        return options;
    }
    /**
     * this method is called when the button "nootification" is clicked
     * @param view
     */
    public void notificationProd (View view){
        Intent intent = new Intent(getApplicationContext(), ProduitsNotification.class);
        startActivity(intent);
    }

    public class GetLink extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String...params){
            try{
                URL url = new URL(params[0]);
                URLConnection con = url.openConnection();
                InputStreamReader inputStreamReader=new InputStreamReader(con.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                JSONObject json = new JSONObject(bufferedReader.readLine());
                String link = json.getString("image").toString().trim();
                return link;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Vendeur vendeur=sessionManager.getVendeur();
            vendeur.setImage(s);
            sessionManager.insertVendeur(vendeur);
        }
    }

    /**
     * this method listen on the button "back"
     * allows us to go back to the vendeur space
     * @param view
     */
    public void etailsVend (View view){
        Intent i = new Intent(this, detailsVendeur.class);
        startActivity(i);
        finish();
    }

    /**
     * this method upload the new image to the server
     * @param image
     * @param id
     */
    private void uploadImage(String image,int id){
        myRequest.UploadProfileImg(image, id, new MyRequest.ConfermeDEclineCallBack() {
            @Override
            public void onSuccess(String message) {
                Log.i("TAG", "onSuccess: "+message);
            }

            @Override
            public void inputErr(Map<String, String> errors) {
                Log.i("TAG", "inputErr: ");
            }

            @Override
            public void onErr(String message) {
                Log.i("TAG", "onErr: "+message);
            }
        });
    }
    /**
     * this method transfer the bitmap to string so we can upload the image to the server
     * @param bitmap
     * @return
     */
    private String ImageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] imgBytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }

    /**
     * this method allows us to get the image profile from the server
     */
    private class LoadImage extends AsyncTask<String,Void,Bitmap> {
        CircleImageView circleImageView;
        public LoadImage(CircleImageView imageprofile) {
            this.circleImageView=imageprofile;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            Bitmap bitmap=null;
            try {
                InputStream inputStream=new URL(urlLink).openStream();
                bitmap= BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            circleImageView.setImageBitmap(bitmap);
        }
    }
    /**
     * this method allows us to get the information vendeur from the server
     */
    class GetJSON extends AsyncTask< Void, Void,String>{
        public static final String REQUEST_METHOD = "GET";
        JSONObject postData;
        String UrlWebService;

        public  GetJSON(String link){
            UrlWebService=link;
        }

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
                    if(quantiteProduit< 50){
                        comp++;
                    }
                }
            }catch (Exception e){
                return  null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //tester si il ya des produits qui sont faible on quantitie
            if(comp>0){

                Button sp=(Button) findViewById(R.id.notification_button);
                sp.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_notifications_roge1));
                NombrProduitNoti.setBackgroundDrawable(getResources().getDrawable(R.drawable.shep_));
                NombrProduitNoti.setText(""+comp);

            }

            comp=0;
        }
    }
    /**
     * this method allows us to get the number the prodcts low from the server
     */
    public class ForNotif extends AsyncTask<Void,Void,String>{
        String UrlWebService;
        public static final String REQUEST_METHOD = "GET";
        JSONObject postData;
        int temp_res;
        int temp_dem;
        int temp_prod;
        public ForNotif(String UrlWebService){
            this.UrlWebService=UrlWebService;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String json = "";
            try {
                URL url = new URL(UrlWebService);
                URLConnection con = url.openConnection();
                InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                json = bufferedReader.readLine();
                postData = new JSONObject(json);
                temp_res=postData.getInt("nombreReservation");
                temp_dem=postData.getInt("nombreDemande");
                temp_prod=postData.getInt("produitS");
                String message="";
                Log.i("TAG", "Tese: "+temp_dem+", "+sessionManager.getNotifDem());
                if (temp_dem > sessionManager.getNotifDem()){
                    message+="Vous avez "+(temp_dem-sessionManager.getNotifDem())+" Nouvelle Demande \n";
                }
                if (temp_res > sessionManager.getNotifRes()){
                    message+="Vous avez "+(temp_res-sessionManager.getNotifRes())+" Nouvelle Réservation \n";
                }
                if (temp_prod > sessionManager.getNotifProd()){
                    message+="Vous avez "+(temp_prod-sessionManager.getNotifProd())+" produits faibles";
                }
                sessionManager.updateDemande(temp_dem);
                sessionManager.updateReserve(temp_res);
                sessionManager.updateProd(temp_prod);
                if (! message.equals(""))
                    sendNotif(message);
                Thread.sleep(2000);
            } catch (Exception ignored) {
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            new ForNotif(Config.URL+"produits_notif.php?idVendeur="+sessionManager.getVendeur().getId_vendeur()).execute();
        }
    }

    public void sendNotif(String message){
        Intent intent = new Intent();
        intent.setAction("com.example.Broadcast");
        intent.putExtra("msg", message);
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        if (getJSON!=null)
            getJSON.cancel(true);
        if (notif!=null)
            notif.cancel(true);
        super.onDestroy();
    }
}
