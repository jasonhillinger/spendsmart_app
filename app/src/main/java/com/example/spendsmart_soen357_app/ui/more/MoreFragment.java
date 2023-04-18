package com.example.spendsmart_soen357_app.ui.more;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.spendsmart_soen357_app.DatabaseController;
import com.example.spendsmart_soen357_app.LoginActivity;
import com.example.spendsmart_soen357_app.MainActivity;
import com.example.spendsmart_soen357_app.databinding.FragmentMoreBinding;


public class MoreFragment extends Fragment {

    private FragmentMoreBinding binding;

    private DatabaseController db;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        binding = FragmentMoreBinding.inflate(inflater, container, false);

        db = new DatabaseController()
;

        ConstraintLayout moreView =  binding.moreAboutView;
        moreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.setLOGGEDIN_USERNAME(null);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        View root = binding.getRoot();
        return root;
    }


}
