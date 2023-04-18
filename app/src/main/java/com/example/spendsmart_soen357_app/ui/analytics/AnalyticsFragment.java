package com.example.spendsmart_soen357_app.ui.analytics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spendsmart_soen357_app.R;
import com.example.spendsmart_soen357_app.databinding.FragmentAnalyticsBinding;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class AnalyticsFragment extends Fragment {

    private FragmentAnalyticsBinding binding;
    private PieChart pieChart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pieChart.addPieSlice(
                new PieModel(
                        "Lifestyle",
                        25,
                        ResourcesCompat.getColor(getResources(), R.color.teal_200, null)));
        pieChart.addPieSlice(
                new PieModel(
                        "Transport",
                        25,
                        ResourcesCompat.getColor(getResources(), R.color.purple_700, null)));
        pieChart.addPieSlice(
                new PieModel(
                        "Grocery",
                        25,
                        ResourcesCompat.getColor(getResources(), R.color.purple_200, null)));
        pieChart.addPieSlice(
                new PieModel(
                        "Invest",
                        25,
                        ResourcesCompat.getColor(getResources(), R.color.dark_blue, null)));
        AnalyticsViewModel analyticsViewModel =
                new ViewModelProvider(this).get(AnalyticsViewModel.class);

        binding = FragmentAnalyticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}