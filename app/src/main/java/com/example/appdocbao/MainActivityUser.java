package com.example.appdocbao;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

public class MainActivityUser extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private TextView txtName,txtGmail;
    ImageView imgUser;
    Button btnUser,btnCancle;
    private GoogleApiClient mGoogleApiClient;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        txtGmail=findViewById(R.id.textgmailuser);
        txtName=findViewById(R.id.textenuser);
        imgUser = findViewById(R.id.imguser);
        btnUser = findViewById(R.id.btnlogout1);
        btnCancle = findViewById(R.id.btncancle1);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
        if (acc!=null) {
            // txtGmail.setText(acc.getEmail().toString());
            txtName.setText(acc.getDisplayName().toString());
            txtGmail.setText(acc.getEmail().toString());
            Picasso.get().load(acc.getPhotoUrl()).into(imgUser);
        }
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
                Toast.makeText(MainActivityUser.this, "logout selected", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivityUser.this,LoginActivity.class);
                startActivity(i);

            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivityUser.this,mysql.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void signOut(){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {

            }
        });
    }
}