package com.example.appdocbao;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.HashMap;
import java.util.Map;

public class addrss extends AppCompatActivity {
    EditText edtName,edtLink;
    Button btnthem,btncancel;
    String urlInsert = "http://"+MainActivity.ipconfig+"/baorss/insert.php";
    private TextView txtName;
    ImageView imgUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrss);
        btncancel = findViewById(R.id.btn_cancel);
        btnthem = findViewById(R.id.btn_up);
        edtLink = findViewById(R.id.edt_uplink);
        edtName = findViewById(R.id.edt_upten);
        imgUser = findViewById(R.id.img13);
        txtName = findViewById(R.id.txtadduser);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(addrss.this,mysql.class);
                startActivity(i);
            }
        });
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
        if (acc!=null) {
            // txtGmail.setText(acc.getEmail().toString());
            txtName.setText(acc.getDisplayName().toString());
        }
        txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(addrss.this,MainActivityUser.class);
                startActivity(i);
            }
        });
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addrss.this,MainActivityUser.class);
                startActivity(intent);
            }
        });
        btnthem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    String inputString = edtLink.getText().toString();
                    if (inputString.endsWith(".rss")||inputString.endsWith("/rss/")) {
                        // Chuỗi inputString có đuôi mở rộng là ".rss"

                                edtLink.clearFocus();
                                String Name = edtName.getText().toString().trim();
                                String Link = edtLink.getText().toString().trim();
                                if (Name.isEmpty() || Link.isEmpty()){
                                    Toast.makeText(addrss.this, "vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    themrss(urlInsert);
                                }

                    } else {
                        // Chuỗi inputString không có đuôi mở rộng là ".rss"
                        Toast.makeText(addrss.this, "link không hợp lệ", Toast.LENGTH_SHORT).show();

                    }

            }
        });

        /*btnthem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String Name = edtName.getText().toString().trim();
                String Link = edtLink.getText().toString().trim();
                if (Name.isEmpty() || Link.isEmpty()){
                    Toast.makeText(addrss.this, "vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    themrss(urlInsert);
                }

            }
        });*/

    }
    private void themrss(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("thanhcong")){
                            Toast.makeText(addrss.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(addrss.this,mysql.class);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(addrss.this, "lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(addrss.this, "xảy lỗi", Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("namerss",edtName.getText().toString().trim());
                params.put("linkrss",edtLink.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}