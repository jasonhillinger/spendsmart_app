package com.example.spendsmart_soen357_app.ui.analytics;

import android.graphics.Color;
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
    float grocerySum = 0;
    float clothingSum = 0;
    float electronicsSum = 0;
    float travelSum = 0;
    float servicesAndSubscriptionsSum = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAnalyticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db = new DatabaseController();
        PieChart pieChart = (PieChart) root.findViewById(R.id.piechart);
        String loggedInUser = db.LOGGEDIN_USERNAME;
        db.getTransactions(new Callback<JSONArray>() {
            @Override
            public void onCallback(JSONArray data) {
                try {
                    for (int i = 0; i < data.length(); i++) {


                        JSONObject obj = data.getJSONObject(i);
                        System.out.println("");
                        String user_name = obj.getString("username");
                        boolean isCorrectUser = user_name.equals(loggedInUser);

                        if (isCorrectUser) {
                            String category = obj.getString("category");
                            if (category.equals("Food and groceries")) {
                                // increase amount for pie slice of grocery
                                grocerySum += Float.parseFloat(obj.getString("amount"));
                            }
                            if (category.equals("Clothing and accessories")) {
                                clothingSum += Float.parseFloat(obj.getString("amount"));
                            }
                            if (category.equals("Electronics")) {
                                electronicsSum += Float.parseFloat(obj.getString("amount"));
                            }
                            if (category.equals("Travel")) {
                                travelSum += Float.parseFloat(obj.getString("amount"));
                            }
                            if (category.equals("Services and subscriptions")) {
                                servicesAndSubscriptionsSum += Float.parseFloat(obj.getString("amount"));
                            }

                        }
                    }
                }
                catch (Exception e){
                    System.out.println(e);
                }
                pieChart.addPieSlice(
                        new PieModel(
                                "Grocery",
                                grocerySum,
                                Color.parseColor("#FE6DA8")));
                pieChart.addPieSlice(
                        new PieModel(
                                "Clothing",
                                clothingSum,
                                Color.parseColor("#56B7F1")));
                pieChart.addPieSlice(
                        new PieModel(
                                "Electronics",
                                electronicsSum,
                                Color.parseColor("#CDA67F")));
                pieChart.addPieSlice(
                        new PieModel(
                                "Travel",
                                travelSum,
                                Color.parseColor("#FED70E")));
                pieChart.addPieSlice(
                        new PieModel(
                                "Subscriptions",
                                servicesAndSubscriptionsSum,
                                Color.parseColor("#AE6DA8")));

                pieChart.startAnimation();
            }
        });

        System.out.println("analytics page");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}