package com.example.appdocbao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    ListView lvTeuDe;
    ArrayList<String> arrayTitle , arrayLink;
    ArrayAdapter adapter;
    private GoogleApiClient mGoogleApiClient;
    private TextView txtName , texttenbao;
    public static String txttenbao = "";
    public static String ipconfig = "192.168.172.5";
    ImageView imgUser;
    public  static String link = "";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ReadRSS().execute(link);
        lvTeuDe = findViewById(R.id.lvTieude);
        arrayTitle = new ArrayList<>();
        arrayLink = new ArrayList<>();
        imgUser = findViewById(R.id.imageView1);
        txtName = findViewById(R.id.textViewuser);
        texttenbao = findViewById(R.id.texttenbao);
        texttenbao.setText(txttenbao);
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
        if (acc!=null) {
            // txtGmail.setText(acc.getEmail().toString());
            txtName.setText(acc.getDisplayName().toString());
        }
        txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,MainActivityUser.class);
                startActivity(i);
            }
        });
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MainActivityUser.class);
                startActivity(intent);
            }
        });
        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,arrayTitle);
        lvTeuDe.setAdapter(adapter);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        /*lvTeuDe.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,Newsactivity.class);
                intent.putExtra("link tin tuc",arrayLink.get(position));
                startActivity(intent);
                return false;
            }
        });*/
        lvTeuDe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,Newsactivity.class);
                intent.putExtra("link tin tuc",arrayLink.get(position));
                startActivity(intent);

            }
        });


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private class ReadRSS extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String Line ="";
                while ((Line = bufferedReader.readLine())  !=null){
                    content.append(Line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLDOMParser parser =new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");

            for (int i = 0 ; i< nodeList.getLength(); i++){
                Element element = (Element) nodeList.item(i);
               String tieuDe = parser.getValue(element , "title");
                arrayTitle.add(tieuDe);
                arrayLink.add(parser.getValue(element, "link"));
            }
            adapter.notifyDataSetChanged();

        }
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
                Toast.makeText(MainActivity.this, "logout selected", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
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
}