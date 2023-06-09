package com.example.spendsmart_soen357_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;


import com.example.spendsmart_soen357_app.databinding.ActivityMainBinding;

import org.json.JSONArray;

public class LoginActivity extends AppCompatActivity {

//    private ActivityMainBinding binding;

    // Set to true if you want to skip login for debugging purposes
    private boolean DEBUG_MODE = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("LoginActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DatabaseController db = new DatabaseController();
        // ensure the app is edge to edge
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        // turn off action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        EditText username_textfield = (EditText) findViewById(R.id.username_textfield);
        EditText password_textfield = (EditText) findViewById(R.id.password_textfield);
        Button loginButton = findViewById(R.id.loginButton);

//        ImageView imageView = findViewById(R.id.logo);
//        imageView.setImageResource(R.drawable.spendsmart_logo);
//
//        ImageView sloganView = findViewById(R.id.slogan);
//        sloganView.setImageResource(R.drawable.slogan);
//
//        ImageView welcome_message = findViewById(R.id.welcome_message);
//        welcome_message.setImageResource(R.drawable.welcome_message);
//        loginButton.setStyle(R.style.BlueButton);

//        Button loginButton = (Button) findViewById(R.id.login_button);
//        loginButton.setBackgroundResource(R.drawable.button_blue);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code to be executed when the button is clicked
                String username = username_textfield.getText().toString();
                String password = password_textfield.getText().toString();
                System.out.println("button clicked");

//                TODO: Comment intent and uncomment db
                if(DEBUG_MODE){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    db.login(username, password, new Callback<Boolean>() {
                        @Override
                        public void onCallback(Boolean success) {
                            if (success) {
                                db.setLOGGEDIN_USERNAME(username);
                                System.out.println("Success");

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);

                            } else {
                                // Login failed, do something here
                                //TODO create toast message indicating that user login failed
                                System.out.println("Failure");
                                Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }


            }
        });





    }
}
