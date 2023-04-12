package com.example.spendsmart_soen357_app.ui.analytics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spendsmart_soen357_app.databinding.FragmentAnalyticsBinding;

public class AnalyticsFragment extends Fragment {

    private FragmentAnalyticsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AnalyticsViewModel analyticsViewModel =
                new ViewModelProvider(this).get(AnalyticsViewModel.class);

        binding = FragmentAnalyticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAnalytics;
        analyticsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}