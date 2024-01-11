package com.example.appdocbao;

import static android.widget.Toast.LENGTH_SHORT;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {
    private EditText emailedit,passedit;
    private Button btnLogin,btnRegis,btnpass;
    private FirebaseAuth mAuth;
   //private GoogleApiClient mGoogleSignInClient;
    private static int RC_SIGN_IN=100;
    private GoogleApiClient mGoogleApiClient;
    private ConnectionReceiver receiver;
    private IntentFilter intentFilter;



    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        btnpass= findViewById(R.id.btnUserforgotpassword);
        emailedit=findViewById(R.id.email);
        passedit=findViewById(R.id.password);
        btnLogin=findViewById(R.id.btnlogin);
        btnRegis=findViewById(R.id.btnregis);
        receiver = new ConnectionReceiver();
        intentFilter = new IntentFilter("com.example.appdocbao.SOME_ACTION");
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver,intentFilter);
        

        btnpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i = new Intent(LoginActivity.this,quenmkMainActivity.class);
            startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        findViewById(R.id.sign_in_button).setOnClickListener(this);


    }

    private void registerReceiver(ConnectionReceiver receiver, IntentFilter intentFilter) {
    }

    private void register() {
        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }

    private void login() {
        String email,pass;
        email = emailedit.getText().toString();
        pass=passedit.getText().toString();
        if(TextUtils.isEmpty(email)){

            Toast.makeText(this,"vui lòng nhập gmail", LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"vui lòng nhập password", LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công", LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,mysql.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Đăng nhập không thành công", LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Failed",connectionResult+"");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_button:

                signIn();
            break;
        }

    }
    private void signIn() {

            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
              startActivityForResult(signInIntent, RC_SIGN_IN);
            // Log.d("Success",mGoogleApiClient.isConnected()+"");

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            if (resultCode == RESULT_OK) {
                // Lấy thông tin người dùng đã đăng nhập
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
                // Chuyển sang Intent khác
                Toast.makeText(this,"Đăng nhập thành công bằng tài khoản google", LENGTH_SHORT).show();
                Intent intent = new Intent(this, mysql.class);
                startActivity(intent);
            }
        }
    }


    private class ConnectionReceiver {
    }
}
