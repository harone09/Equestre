package com.project.equestre;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.equestre.LocalData.UserLocalStorage;
import com.project.equestre.Models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText emailBox, passwordBox;
    Button loginButton;
    Button guestButton;
    TextView registerLink;
    String URL="http://192.168.0.4:8080/Equestre_WS/login.jsp";
    User currentUser;
    UserLocalStorage userLocalStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userLocalStorage=new UserLocalStorage(this);



        emailBox = (EditText)findViewById(R.id.emailBox);
        passwordBox = (EditText)findViewById(R.id.passwordBox);
        loginButton = (Button)findViewById(R.id.loginButton);
        guestButton = (Button)findViewById(R.id.btn_guest);
        registerLink = (TextView)findViewById(R.id.registerLink);
       if(userLocalStorage.isLoggedIn()){
           if(userLocalStorage.getLoggedInUser().getRole().equals("Client")){
               Login.this.finish();

               startActivity(new Intent(Login.this, MainActivityClient.class));
           }
           if(userLocalStorage.getLoggedInUser().getRole().equals("Monitor")){
               Login.this.finish();

               startActivity(new Intent(Login.this, MainActivityMonitor.class));
           }
           if(userLocalStorage.getLoggedInUser().getRole().equals("Admin")){
               Login.this.finish();

               startActivity(new Intent(Login.this, MainActivityAdmin.class));
           }

       }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //  Toast.makeText(Login.this, "click", Toast.LENGTH_LONG).show();
                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONObject json=new JSONObject(s);

                            if(json.has("error")){
                                Toast.makeText(Login.this,json.getString("error"),Toast.LENGTH_LONG).show();
                                Toast.makeText(Login.this,"Incorrect Details",Toast.LENGTH_LONG).show();

                            }else {
                                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();
                                currentUser= new User(json.getString("id_user") , json.getString("email")  ,
                                        json.getString("password") , json.getString("username") ,
                                        json.getString("name")  , json.getString("phone") , json.getString("role")
                                        , json.getString("abonnement"),json.getString("reservation"));
                                userLocalStorage.clearUserData();
                                userLocalStorage.storeUserData(currentUser);
                                userLocalStorage.setUserLoggedIn();
                                if(currentUser.getRole().equals("Client")){
                                    Login.this.finish();
                                    startActivity(new Intent(Login.this, MainActivityClient.class));
                                }
                                if(currentUser.getRole().equals("Monitor")){
                                    Login.this.finish();
                                    startActivity(new Intent(Login.this, MainActivityMonitor.class));
                                }
                                if(currentUser.getRole().equals("Admin")){
                                    Login.this.finish();
                                    startActivity(new Intent(Login.this, MainActivityAdmin.class));
                                }





                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Login.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("email", emailBox.getText().toString());
                        parameters.put("password", passwordBox.getText().toString());
                        return parameters;
                    }
                };

                RequestQueue rQueue = Volley.newRequestQueue(Login.this);
                rQueue.add(request);
            }
        });
       guestButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               startActivity(new Intent(Login.this, MainActivityGuest.class));
               Login.this.finish();
           }
       });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }
}

