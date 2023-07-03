package com.example.ecommerceapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Modals.LoginData;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.Retro_Instance_Class;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText email_edt, password_edt;
    TextView register_text;
    Button login_btn;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_edt = findViewById(R.id.email_edt);
        password_edt = findViewById(R.id.password_edt);
        register_text = findViewById(R.id.register_text);
        login_btn = findViewById(R.id.login_btn);

        preferences = getSharedPreferences("Login_pref", MODE_PRIVATE);
        editor = preferences.edit();

        register_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        if (isNetworkConnected())
        {
            login_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!email_edt.getText().toString().isEmpty() && !password_edt.getText().toString().isEmpty())
                    {
                        String email = email_edt.getText().toString();
                        String password = password_edt.getText().toString();

                        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                        progressDialog.setTitle("Please Wait");
                        progressDialog.setMessage("Logging In...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        Retro_Instance_Class.CallApi().LOGIN_USER_CALL(email,password).enqueue(new Callback<LoginData>() {
                            @Override
                            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                                if (response.body().getConnection() == 1) {
                                    if (response.body().getResult() == 1) {
                                        editor.putBoolean("isLogin",true);
                                        editor.putString("name",response.body().getUserdata().getName());
                                        editor.putString("email",response.body().getUserdata().getEmail());
                                        editor.putInt("usrid", response.body().getUserdata().getId());
                                        editor.apply();

                                        Toast.makeText(LoginActivity.this, "Welcome " + response.body().getUserdata().getName(), Toast.LENGTH_SHORT).show();

                                        progressDialog.dismiss();

                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "email or password doesn't match", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<LoginData> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else
                    {
                        if (email_edt.getText().toString().isEmpty())
                        {
                            email_edt.setError("Enter Email");
                        }
                        if (password_edt.getText().toString().isEmpty()) {
                            password_edt.setError("Enter Password");
                        }
                    }
                }
            });
        }else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}