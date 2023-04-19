package com.example.spendsmart_soen357_app.ui.analytics;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendsmart_soen357_app.Callback;
import com.example.spendsmart_soen357_app.DatabaseController;
import com.example.spendsmart_soen357_app.R;
import com.example.spendsmart_soen357_app.databinding.FragmentAnalyticsBinding;
import com.example.spendsmart_soen357_app.databinding.FragmentHomeBinding;
import com.example.spendsmart_soen357_app.ui.budget.RecyclerModel;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsFragment extends Fragment {

    private FragmentAnalyticsBinding binding;
    private PieChart pieChart;
    private DatabaseController db;
    private ValueLineChart mCubicValueLineChart;
    float grocerySum = 0;
    float clothingSum = 0;
    float electronicsSum = 0;
    float travelSum = 0;
    float servicesAndSubscriptionsSum = 0;
   // ProgressBar pb;
   RecyclerView recyclerView;

    List<RecyclerModel> goalList;

    public void addMoneyToTextView(TextView text, float value){
        String originalText = text.getText().toString();
        String newText = originalText + ": $" + Float.toString(value);
        text.setText(newText);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAnalyticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db = new DatabaseController();
        PieChart pieChart = (PieChart) root.findViewById(R.id.piechart);
        mCubicValueLineChart = (ValueLineChart) root.findViewById(R.id.cubiclinechart);


        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);
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
                                Color.parseColor("#FF03DAC5")));
                pieChart.addPieSlice(
                        new PieModel(
                                "Clothing",
                                clothingSum,
                                Color.parseColor("#FF018786")));
                pieChart.addPieSlice(
                        new PieModel(
                                "Electronics",
                                electronicsSum,
                                Color.parseColor("#FFBB86FC")));
                pieChart.addPieSlice(
                        new PieModel(
                                "Travel",
                                travelSum,
                                Color.parseColor("#151A50")));
                pieChart.addPieSlice(
                        new PieModel(
                                "Subscriptions",
                                servicesAndSubscriptionsSum,
                                Color.parseColor("#F30A18")));

                TextView groceries_view = root.findViewById(R.id.grocery_id);
                addMoneyToTextView(groceries_view,grocerySum);

                TextView clothing_view = root.findViewById(R.id.clothing_id);
                addMoneyToTextView(clothing_view,clothingSum);

                TextView electronics_view = root.findViewById(R.id.electronics_id);
                addMoneyToTextView(electronics_view,electronicsSum);

                TextView invest_view = root.findViewById(R.id.travel_id);
                addMoneyToTextView(invest_view,travelSum);

                TextView sub_view = root.findViewById(R.id.subscriptions_id);
                addMoneyToTextView(sub_view ,servicesAndSubscriptionsSum);

                pieChart.startAnimation();
            }
        });



        series.addPoint(new ValueLinePoint("Jan", 6800f));
        series.addPoint(new ValueLinePoint("Feb", 7102f));
        series.addPoint(new ValueLinePoint("Mar", 7205f));
        series.addPoint(new ValueLinePoint("Apr", 7350f));
        series.addPoint(new ValueLinePoint("Mai", 7500f));
        series.addPoint(new ValueLinePoint("Jun", 7650f));
        series.addPoint(new ValueLinePoint("Jul", 7800f));
        series.addPoint(new ValueLinePoint("Aug", 8000f));
        series.addPoint(new ValueLinePoint("Sep", 8200f));
        series.addPoint(new ValueLinePoint("Oct", 8500f));
        series.addPoint(new ValueLinePoint("Nov", 8750f));
        series.addPoint(new ValueLinePoint("Dec", 9150f));


        //ProgressBar pb = (ProgressBar) root.findViewById(R.id.pb);
        //pb.setProgress(50);
        setupProgress(root);
        intiliazedata();

        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.startAnimation();

        System.out.println("analytics page");

        return root;
    }


    private void setupProgress(View root){
        recyclerView = root.findViewById(R.id.budget_list_recyclerview);

    }

    private void intiliazedata(){
        goalList = new ArrayList<>();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}