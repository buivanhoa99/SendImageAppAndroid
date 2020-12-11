package com.example.bathi.uploadimagevolleydemo;

import android.content.Context;
import android.content.Intent;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText userName = findViewById(R.id.input_username);
        final EditText passWord = findViewById(R.id.input_password);
        Button btnLogin = findViewById(R.id.btn_login);
        Toast.makeText(LoginActivity.this,"Text",Toast.LENGTH_LONG).show();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = userName.getText().toString();
                String pass = passWord.getText().toString();

                CheckLogin(user, pass);

            }
        });
    }

    private void CheckLogin(String user, String pass) {
        final String userName = user;
        final String passWord = pass;
        final boolean result = false;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.urlLogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("1")) {
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("userName",userName);
                    intent.putExtra("passWord",passWord);
                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                }
                else {
                    Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu chưa đúng",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                params.put("userName",userName);
                params.put("passWord",passWord);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
    }
}
