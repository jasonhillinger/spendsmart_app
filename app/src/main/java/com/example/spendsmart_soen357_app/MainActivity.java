package com.example.spendsmart_soen357_app;

import android.os.Bundle;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.spendsmart_soen357_app.databinding.ActivityMainBinding;
import com.example.spendsmart_soen357_app.Callback;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ensure the app is edge to edge
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        // turn off action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_budget, R.id.navigation_analytics, R.id.navigation_more)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        DatabaseController db = new DatabaseController();

        // Call the login function with the desired username and password
        String username = "Jackey";
        String password = "123";
        db.login(username, password, new Callback<Boolean>() {
            @Override
            public void onCallback(Boolean success) {
                if (success) {
                    db.setLOGGEDIN_USERNAME(username);
                    System.out.println("Success");
                    // Login successful, do something here

                    // Sample code on how to add funds to checkings account
//                    db.addCheckingAccountFunds(1000);

                    // Sample code on how to add funds to savings account
//                    db.addSavingAccountFunds(2000);

                    // Sample code on how to get checking account funds
                    // done exactly the same way with saving account
                    db.getCheckingAccountFunds(new Callback<String>() {
                        @Override
                        public void onCallback(String data) {
                            // Do something with the data
                            // Should change data in ui
                            System.out.println(data);
                        }
                    });

                    //Sample code to get transactions for user
                    db.getTransactions(new Callback<JSONArray>() {
                        @Override
                        public void onCallback(JSONArray data) {
                            System.out.println(data);
                        }
                    });



                } else {
                    // Login failed, do something here
                    System.out.println("Failure");
                }
            }
        });


        // Handle overlap when app has handle bars
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Apply the insets as a margin to the view. Here the system is setting
            // only the bottom, left, and right dimensions, but apply whichever insets are
            // appropriate to your layout. You can also update the view padding
            // if that's more appropriate.
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            mlp.leftMargin = insets.left;
            mlp.bottomMargin = insets.bottom;
            mlp.rightMargin = insets.right;
            v.setLayoutParams(mlp);

            // Return CONSUMED if you don't want want the window insets to keep being
            // passed down to descendant views.
            return WindowInsetsCompat.CONSUMED;
        });
    }

}