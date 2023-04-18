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

import com.example.spendsmart_soen357_app.Callback;
import com.example.spendsmart_soen357_app.DatabaseController;
import com.example.spendsmart_soen357_app.R;
import com.example.spendsmart_soen357_app.databinding.FragmentAnalyticsBinding;
import com.example.spendsmart_soen357_app.databinding.FragmentHomeBinding;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONObject;

public class AnalyticsFragment extends Fragment {

    private FragmentAnalyticsBinding binding;
    private PieChart pieChart;
    private DatabaseController db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAnalyticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db = new DatabaseController();
        PieChart pieChart = (PieChart) root.findViewById(R.id.piechart);
        db.getTransactions(new Callback<JSONArray>() {
            @Override
            public void onCallback(JSONArray data) {
                double grocery = 0.0;
                try {

                for (int i = 0; i < data.length(); i++) {

                        JSONObject obj = data.getJSONObject(i);
                        System.out.println("");
                        String user_name = obj.getString("username");
                        boolean iscorrrectUser = user_name == db.getLOGGEDIN_USER();


                        if (iscorrrectUser)
                        {
                           String category = obj.getString("category");
                           if (category == "Food and groceries")
                           {
                               grocery = grocery + Double.parseDouble(obj.getString("amount"));
                           }

                        }


                    }
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Grocery",
                                    grocery,
                                    ResourcesCompat.getColor(getResources(), R.color.teal_200, null)));

                }
                catch (Exception e){
                    System.out.println(e);
                }

            }
        });




//        pieChart.addPieSlice(
//                new PieModel(
//                        "Transport",
//                        25,
//                        ResourcesCompat.getColor(getResources(), R.color.purple_700, null)));
//        pieChart.addPieSlice(
//                new PieModel(
//                        "Lifestyle",
//                        25,
//                        ResourcesCompat.getColor(getResources(), R.color.purple_200, null)));
//        pieChart.addPieSlice(
//                new PieModel(
//                        "Invest",
//                        25,
//                        ResourcesCompat.getColor(getResources(), R.color.dark_blue, null)));
//        AnalyticsViewModel analyticsViewModel =
//                new ViewModelProvider(this).get(AnalyticsViewModel.class);
        pieChart.startAnimation();
        System.out.println("analytics page");



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}