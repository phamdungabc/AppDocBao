package com.example.appdocbao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class quenmkMainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ProgressBar progressBar;
    EditText usergmail;
    Button userpass, cancle;
    FirebaseAuth firebaseAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quenmk_main);
        usergmail = findViewById(R.id.etusergmail);
        userpass = findViewById(R.id.btnpass);
        cancle = findViewById(R.id.passcancle);
        firebaseAuth = FirebaseAuth.getInstance();
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(quenmkMainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        userpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.sendPasswordResetEmail(usergmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(quenmkMainActivity.this, "đã gửi xác thưc về gmail của bạn", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(quenmkMainActivity.this, "gmail không đúng", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

    }
}