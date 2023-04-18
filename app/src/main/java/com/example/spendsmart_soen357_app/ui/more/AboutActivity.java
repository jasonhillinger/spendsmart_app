package com.example.spendsmart_soen357_app.ui.more;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.spendsmart_soen357_app.MainActivity;
import com.example.spendsmart_soen357_app.R;
import com.example.spendsmart_soen357_app.databinding.ActivityAboutBinding;
import com.example.spendsmart_soen357_app.databinding.ActivityMainBinding;

public class AboutActivity  extends AppCompatActivity {

    private ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ensure the app is edge to edge
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView question1 = binding.aboutQuestion1;
        question1.setText("What is Spend Smart?");
        question1.setPaintFlags(question1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView question2 = binding.aboutQuestion2;
        question2.setText("What problem are you trying to address?");
        question2.setPaintFlags(question2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // turn off action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ConstraintLayout backButton =  binding.aboutTopNavbar;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
