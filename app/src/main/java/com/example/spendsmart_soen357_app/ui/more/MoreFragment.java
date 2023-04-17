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

import com.example.spendsmart_soen357_app.MainActivity;
import com.example.spendsmart_soen357_app.databinding.FragmentMoreBinding;


public class MoreFragment extends Fragment {

    private FragmentMoreBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        binding = FragmentMoreBinding.inflate(inflater, container, false);



        ConstraintLayout moreView =  binding.moreAboutView;
        moreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() == 1) {
                    //no fragments left
                    finish();
                } else {
                    super.onBackPressed();
                }

            }
            }
        });

        View root = binding.getRoot();
        return root;
    }


}
