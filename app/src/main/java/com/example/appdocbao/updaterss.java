package com.example.appdocbao;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class updaterss extends AppCompatActivity {
    EditText edtupname,edtuplink;
    Button btnup,btncancel;
    int id=0;
    String urluprss = "http://"+MainActivity.ipconfig+"/baorss/update.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updaterss);
        Intent intent = getIntent();
        DBmysql dBmysql = (DBmysql) intent.getSerializableExtra("datarss");
        Toast.makeText(this, dBmysql.getName(), Toast.LENGTH_SHORT).show();

        id= dBmysql.getId();
        btnup = findViewById(R.id.btn_up);
        edtuplink = findViewById(R.id.edt_uplink);
        edtupname = findViewById(R.id.edt_upten);
        edtupname.setText(dBmysql.getName());
        edtuplink.setText(dBmysql.getLink());
        btncancel=findViewById(R.id.btn_cancel);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(updaterss.this,mysql.class);
                startActivity(i);
            }
        });
        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = edtupname.getText().toString().trim();
                String Link = edtuplink.getText().toString().trim();
                if (Name.isEmpty() || Link.isEmpty()){
                    Toast.makeText(updaterss.this, "vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    capnhaprss(urluprss);
                }
            }
        });
    }
    private void capnhaprss(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("thanhcong")){
                            Toast.makeText(updaterss.this, "cập nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(updaterss.this,mysql.class);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(updaterss.this, "lỗi sml", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(updaterss.this, "lỗi sv", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idrss", String.valueOf(id));
                params.put("namerss",edtupname.getText().toString().trim());
                params.put("linkrss",edtuplink.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}