// Import necessary packages
package com.example.spendsmart_soen357_app.ui.more;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.spendsmart_soen357_app.DatabaseController;
import com.example.spendsmart_soen357_app.LoginActivity;
import com.example.spendsmart_soen357_app.MainActivity;
import com.example.spendsmart_soen357_app.R;
import com.example.spendsmart_soen357_app.databinding.FragmentMoreBinding;

// Define a MoreFragment class that extends the Fragment class
public class MoreFragment extends Fragment {

    // Declare an instance of FragmentMoreBinding to hold the binding information
    private FragmentMoreBinding binding;
    DatabaseController db = new DatabaseController();
    // Override the onCreateView() method to inflate the layout of the fragment
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout using the FragmentMoreBinding class
        binding = FragmentMoreBinding.inflate(inflater, container, false);

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.setLOGGEDIN_USERNAME(null);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        // Get the root view of the inflated layout and return it
        View root = binding.getRoot();
        return root;
    }
}