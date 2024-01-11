package com.example.appdocbao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class Newsactivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    WebView webView;
    private TextView txtName;
    ImageView imgUser;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsactivity);
        Intent intent = getIntent();
        String  link = intent.getStringExtra("link tin tuc");
        webView= findViewById(R.id.webtintuc);
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient());
        imgUser = findViewById(R.id.imageView12);
        txtName = findViewById(R.id.textViewuser1);
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
        }
        txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Newsactivity.this,MainActivityUser.class);
                startActivity(i);
            }
        });
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Newsactivity.this,MainActivityUser.class);
                startActivity(intent);
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.item_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.dangxuat:
                signOut();
                Toast.makeText(Newsactivity.this, "logout selected", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Newsactivity.this,LoginActivity.class);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    private void signOut(){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {

            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}