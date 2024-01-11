package com.example.appdocbao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class mysql extends AppCompatActivity {
    String urlGetData ="http://"+MainActivity.ipconfig+"/baorss/getdata.php";
    ListView lvDBmysql;
    ArrayList<DBmysql> arrayDBmysqlList;
    mysqlAdapter adapter;
    private TextView txtName;
    ImageView imgUser;
    String urldelete = "http://"+MainActivity.ipconfig+"/baorss/delete.php";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysql);
        lvDBmysql = (ListView) findViewById(R.id.listviewDBmysql);
        arrayDBmysqlList = new ArrayList<>();
        adapter = new mysqlAdapter(this,R.layout.dbmysql,arrayDBmysqlList);
        lvDBmysql.setAdapter(adapter);
        Getdata(urlGetData);

        lvDBmysql.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                MainActivity.link = arrayDBmysqlList.get(i).getLink();
               MainActivity.txttenbao = arrayDBmysqlList.get(i).getName();
                Intent intent = new Intent(mysql.this,MainActivity.class);
                startActivity(intent);
            }
        });
        imgUser = findViewById(R.id.imageView1);
        txtName = findViewById(R.id.textViewuser);
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
        if (acc!=null) {
            // txtGmail.setText(acc.getEmail().toString());
            txtName.setText(acc.getDisplayName().toString());
        }
        txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mysql.this,MainActivityUser.class);
                startActivity(i);
            }
        });
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mysql.this,MainActivityUser.class);
                startActivity(intent);
            }
        });
    }
    private void Getdata(String url){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        arrayDBmysqlList.clear();
                       for (int i = 0; i<response.length();i++){
                           try {
                               JSONObject object = response.getJSONObject(i);
                               arrayDBmysqlList.add(new DBmysql(
                                       object.getInt("ID"),
                                       object.getString("NAMES"),
                                       object.getString("LINK")
                               ));
                           } catch (JSONException e) {
                               throw new RuntimeException(e);
                           }
                       }
                       adapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mysql.this, "lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_rss,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAddrss){
            Intent intent = new Intent(mysql.this,addrss.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    public void deleterss(int idrss){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urldelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("thanhcong")){
                            Toast.makeText(mysql.this, "xóa thành công", Toast.LENGTH_SHORT).show();
                            Getdata(urlGetData);
                        }
                        else {
                            Toast.makeText(mysql.this, "Lỗi rùi cưng", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mysql.this, "lỗi hãy xóa lại thử", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idcuarss",String.valueOf(idrss));
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
}