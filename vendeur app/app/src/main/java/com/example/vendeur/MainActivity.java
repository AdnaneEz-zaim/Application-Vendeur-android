package com.example.vendeur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.vendeur.espaceVendeur.SessionManager;
import com.example.vendeur.espaceVendeur.Vendeur;
import com.example.vendeur.espaceVendeur.espaceVendeur;
import com.example.vendeur.volley_requests.MyRequest;
import com.example.vendeur.volley_requests.VolleySingleton;
import com.google.android.material.textfield.TextInputLayout;
//  ===>  MainActivity describe the login activity
public class MainActivity extends AppCompatActivity {
    private TextInputLayout til_pseudo,til_passwd;
    private SessionManager sessiom;
    private Button btn_send;
    private RequestQueue queue;
    private MyRequest request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //session manager that contains the user's informations
        sessiom=new SessionManager(this);
        //if the user is logged in we will automatically go to his space
        if(sessiom.isLogged()){
            Intent intent = new Intent(getApplicationContext(), espaceVendeur.class);
            startActivity(intent);
            finish();
        }
        //Initialize the views
        til_pseudo=(TextInputLayout) findViewById(R.id.id_email_log);
        til_passwd=(TextInputLayout) findViewById(R.id.id_pass_log);
        btn_send=(Button) findViewById(R.id.login);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);
        btn_send.setOnClickListener(new View.OnClickListener() {
            /**
             * if onclik on "connexion" button
             */
            @Override
            public void onClick(View v) {
                String pseudo = til_pseudo.getEditText().getText().toString().trim();
                String password = til_passwd.getEditText().getText().toString().trim();
                //checking for password length
                if(pseudo.length() >0 && password.length()>0) {
                    //send request to the server
                    request.connection(pseudo, password, new MyRequest.LoginCallBack() {

                        @Override
                        public void onSuccess(Vendeur vendeur) {
                            //in case of the request was done successfully
                            sessiom.insertVendeur(vendeur);
                            Intent intent=new Intent(getApplicationContext(),espaceVendeur.class);
                            startActivity(intent);
                            finish();

                        }
                        @Override
                        public void onErr(String message) {
                            //in case of the request was failed
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();


                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),"Veuillez remplir tous les champs",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}

