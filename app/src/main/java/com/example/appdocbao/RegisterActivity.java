package com.example.appdocbao;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailedit,passedit;
    private Button btnRegis,btnCancle;
    private FirebaseAuth mAuth;
    private ConnectionReceiver receiver;
    private IntentFilter intentFilter;
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        emailedit=findViewById(R.id.email);
        passedit=findViewById(R.id.password);
        btnRegis=findViewById(R.id.btnregis);
        btnCancle = findViewById(R.id.btncan);
        receiver = new ConnectionReceiver();
        intentFilter = new IntentFilter("com.example.appdocbao.SOME_ACTION");
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver,intentFilter);
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });


        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    private void registerReceiver(ConnectionReceiver receiver, IntentFilter intentFilter) {
    }

    private void register() {
        String email,pass;
        email = emailedit.getText().toString();
        pass=passedit.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"vui lòng nhập gmail",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"vui lòng nhập password",Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Tạo tài khoản thành công",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent (RegisterActivity.this,mysql.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Tạo tài đã tồn tại hoặc không có " +
                            "đuôi là @gmail.com, mật khẩu không đủ 8 kí tự ",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private class ConnectionReceiver {
    }
}
