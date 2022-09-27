package com.example.projekat1.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projekat1.R;

public class LoginActivity extends AppCompatActivity {

    private final static String adminPassword = "admin123";
    private final static String defaultPassword = "password";
    public static boolean isAdminLogged = false;
    public static boolean isUserLogged = false;

    private EditText usernameET;
    private EditText passwordET;
    private EditText emailET;
    private Button loginBttn;

    private static final String PREF_LOGIN_KEY = "prefLoginKey";
    private static final String PREF_LOGIN_USERNAME = "prefLoginUname";
    private static final String PREF_LOGIN_EMAIL = "prefLoginEmail";

    public static String savedUsername, savedEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(() -> {
            if(checkSharedPreferences()){
                loginCheck();
            }
            return false;
        });
        setContentView(R.layout.activity_login);
        init();

    }


    private boolean checkSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        String type = sharedPreferences.getString(PREF_LOGIN_KEY, "");
        savedEmail = sharedPreferences.getString(PREF_LOGIN_EMAIL, "");
        savedUsername = sharedPreferences.getString(PREF_LOGIN_USERNAME, "");
        if(type != null) {
            System.out.println(savedEmail + " " + savedUsername);
            if (type.equalsIgnoreCase("admin")) {
                isAdminLogged = true;
                return true;
            } else if (type.equalsIgnoreCase("default")){
                isUserLogged = true;
                return true;
             }

        }

        return false;
    }

    private void init(){
        initView();
        initListeners();
    }

    private void initListeners() {
        loginBttn.setOnClickListener(v -> {
            if(checkCredentials()){
                SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
                String identity;
                if(isAdminLogged)
                    identity = "admin";
                else
                    identity = "default";
                savedUsername = usernameET.getText().toString();
                savedEmail = emailET.getText().toString();

                sharedPreferences
                        .edit()
                        .putString(PREF_LOGIN_KEY, identity)
                        .putString(PREF_LOGIN_USERNAME, savedUsername)
                        .putString(PREF_LOGIN_EMAIL, savedEmail)
                        .apply();
                loginCheck();
            }
        });
    }

    private void loginCheck(){
        if(isAdminLogged || isUserLogged){
           Intent intent = new Intent(this, MainActivity.class);
            sharedPreferencesActivityResultLauncher.launch(intent);
        }
    }

    private void showErrorMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        View view = toast.getView();

        //Gets the actual oval background of the Toast then sets the colour filter
        view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

        //Gets the TextView from the Toast so it can be editted
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);

        toast.show();
    }

    private boolean checkCredentials() {
        if(usernameET.getText().toString().length() <= 0){
            showErrorMessage("Username mustn't be empty");
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailET.getText().toString()).matches()){
            showErrorMessage("Email is invalid");
            return false;
        }
        if(passwordET.getText().toString().length() <= 5){
            showErrorMessage("Password too short");
            return false;
        }
        if(usernameET.getText().toString().startsWith("admin") && passwordET.getText().toString().equals(adminPassword)){
            isAdminLogged = true;
            return true;
        }
        else if(usernameET.getText().toString().length() > 0 && passwordET.getText().toString().equals(defaultPassword)){
            isUserLogged = true;
            return true;
        }
        showErrorMessage("Wrong credentials");
        return false;
    }

    private void initView(){
        usernameET = findViewById(R.id.loginUsername);
        emailET = findViewById(R.id.loginEmail);
        passwordET = findViewById(R.id.loginPassword);
        loginBttn = findViewById(R.id.loginButton);
    }

    ActivityResultLauncher<Intent> sharedPreferencesActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    logout();
                }
            });

    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        isUserLogged = false;
        isAdminLogged = false;
    }
}