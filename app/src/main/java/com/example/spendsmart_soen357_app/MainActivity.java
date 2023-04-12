package com.example.spendsmart_soen357_app;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.spendsmart_soen357_app.databinding.ActivityMainBinding;
import com.example.spendsmart_soen357_app.Callback;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        DatabaseController db = new DatabaseController();

        // Call the login function with the desired username and password
        String username = "jack";
        String password = "123";
        db.login(username, password, new Callback<Boolean>() {
            @Override
            public void onCallback(Boolean success) {
                if (success) {
                    db.setLOGGEDIN_USERNAME(username);
                    System.out.println("Success");
                    // Login successful, do something here
                    db.addCheckingAccountFunds(1000);
                    db.addSavingAccountFunds(2000);
                } else {
                    // Login failed, do something here
                    System.out.println("Failure");
                }
            }
        });


    }

}