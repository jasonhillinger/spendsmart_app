package com.example.spendsmart_soen357_app.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBindings;

import com.example.spendsmart_soen357_app.Adapter.TransactionAdapter;
import com.example.spendsmart_soen357_app.Callback;
import com.example.spendsmart_soen357_app.DatabaseController;
import com.example.spendsmart_soen357_app.Model.Transaction;
import com.example.spendsmart_soen357_app.R;
import com.example.spendsmart_soen357_app.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView.Adapter adapter;
    private RecyclerView rvTransactions;
    Calendar calendar = Calendar.getInstance();
    int currentMonth = calendar.get(Calendar.MONTH) + 1;
    int currentYear = calendar.get(Calendar.YEAR);


    public void setExpenseIncome(TextView expenses_view,TextView income_view,DatabaseController db, int currentMonth, int radioButtonVal){
        String date_time_radio = "this_month";
        ArrayList<Transaction> trans = new ArrayList<Transaction>();
        // Getting the transactions for expenses and income based on radio button selection
        db.getTransactions(new Callback<JSONArray>() {
            @Override
            public void onCallback(JSONArray data) {
                double expenses = 0;
                double income = 0;
                for (int i = 0; i < data.length(); i++) {
                    try {
                        JSONObject obj = data.getJSONObject(i);
                        String category = obj.getString("category");
                        String dateStr = obj.getString("date");
                        String accountName = obj.getString("account");
                        String nameOfSubject = obj.getString("subject");
                        String type = obj.getString("type");
                        String time = obj.getString("time");
                        LocalDate date = LocalDate.parse(dateStr);

                        int month = date.getMonthValue();
                        int year = date.getYear();
                        if(radioButtonVal == R.id.this_month && month == currentMonth){
                            if (category.equals("income")){
                                income += Double.parseDouble(obj.getString("amount"));
                            }
                            else{
                                expenses += Double.parseDouble(obj.getString("amount"));
                            }
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date1 = dateFormat.parse(dateStr);
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                            Date time1 = timeFormat.parse(time);
                            trans.add(new Transaction(accountName,Double.parseDouble(obj.getString("amount")
                            ), category, nameOfSubject, type, date1, time1, ""));
                        }

                        boolean monthCheckPast3Month = ( (month == currentMonth )|| (month == currentMonth -1 ) || (month == currentMonth -2 ) ) && (year == currentYear);
                        if(radioButtonVal == R.id.past_3_month && monthCheckPast3Month){
                            if (category.equals("income")){
                                income += Double.parseDouble(obj.getString("amount"));
                            }
                            else{
                                expenses += Double.parseDouble(obj.getString("amount"));
                            }
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date1 = dateFormat.parse(dateStr);
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                            Date time1 = timeFormat.parse(time);
                            trans.add(new Transaction(accountName,Double.parseDouble(obj.getString("amount")
                            ), category, nameOfSubject, type, date1, time1, ""));
                        }

                        boolean monthCheckPast6Month = ( (month == currentMonth ) || (month == currentMonth -1 ) || (month == currentMonth -2 )  || (month == currentMonth -3 )  || (month == currentMonth -4 ) || (month == currentMonth -5 ) ) && (year == currentYear);
                        if(radioButtonVal == R.id.past_6_month && monthCheckPast6Month){
                            if (category.equals("income")){
                                income += Double.parseDouble(obj.getString("amount"));
                            }
                            else{
                                expenses += Double.parseDouble(obj.getString("amount"));
                            }
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date1 = dateFormat.parse(dateStr);
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                            Date time1 = timeFormat.parse(time);
                            trans.add(new Transaction(accountName,Double.parseDouble(obj.getString("amount")
                            ), category, nameOfSubject, type, date1, time1, ""));
                        }

                        boolean monthCheckPastYear = ( (month == currentMonth )|| (month == currentMonth -1 ) || (month == currentMonth -2 )  || (month == currentMonth -3 )  || (month == currentMonth -4 ) || (month == currentMonth -5 ) || (month == currentMonth -6 )|| (month == currentMonth -7 ) || (month == currentMonth -8 )  || (month == currentMonth -9 )  || (month == currentMonth -10 ) || (month == currentMonth -11 ) ) && ( (year == currentYear - 1) || (year == currentYear));
                        if(radioButtonVal == R.id.past_year && monthCheckPastYear){
                            if (category.equals("income")){
                                income += Double.parseDouble(obj.getString("amount"));
                            }
                            else{
                                expenses += Double.parseDouble(obj.getString("amount"));
                            }
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date1 = dateFormat.parse(dateStr);
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                            Date time1 = timeFormat.parse(time);
                            trans.add(new Transaction(accountName,Double.parseDouble(obj.getString("amount")
                            ), category, nameOfSubject, type, date1, time1, ""));
                        }


                        System.out.println("TEST");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
                System.out.println(income);
                String income_str = String.format("%.2f",income);
                income_view.setText("$ " + income_str);
                String expenses_str = String.format("%.2f",expenses);
                expenses_view.setText("$ " + expenses_str);
                // Set transaction view
                recyclerViewTransaction(getContext(),trans);
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        DatabaseController db = new DatabaseController();

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Setting good morning message for user
        TextView welcome_message = root.findViewById(R.id.welcome_message);
        welcome_message.setText("Good Morning, " + db.getLOGGEDIN_USER() + "!");



        // Setting the checking account balance for the user
        TextView balance = root.findViewById(R.id.tv_balance_value);
        db.getCheckingAccountFunds(new Callback<String>() {
            @Override
            public void onCallback(String data) {
                // Do something with the data
                balance.setText("$ "+data);
//                System.out.println(data);
            }
        });

        TextView expenses_view = root.findViewById(R.id.tv_expense_value);
        TextView income_view = root.findViewById(R.id.tv_income_value);

        setExpenseIncome(expenses_view,income_view,db,currentMonth, R.id.this_month);

        //Setup UI elements
        setupUI(root);

        Button month = binding.btnMonth;
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        final int[] lastSelectedId = {sharedPref.getInt("lastSelectedId", R.id.this_month)};

        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getContext(), view);
                popup.inflate(R.menu.balance_menu);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.this_month:
                                item.setChecked(true);
                                lastSelectedId[0] = R.id.this_month;
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putInt("lastSelectedId", R.id.this_month);
                                editor.apply();

                                setExpenseIncome(expenses_view,income_view,db,currentMonth,R.id.this_month);


                                break;
                            case R.id.past_3_month:
                                item.setChecked(true);
                                lastSelectedId[0] = R.id.past_3_month;
                                SharedPreferences.Editor editor1 = sharedPref.edit();
                                editor1.putInt("lastSelectedId", R.id.past_3_month);
                                editor1.apply();

                                // Add onClickListener for this case
                                setExpenseIncome(expenses_view,income_view,db,currentMonth,R.id.past_3_month);

                                break;
                            case R.id.past_6_month:
                                item.setChecked(true);
                                lastSelectedId[0] = R.id.past_6_month;
                                SharedPreferences.Editor editor2 = sharedPref.edit();
                                editor2.putInt("lastSelectedId", R.id.past_6_month);
                                editor2.apply();

                                setExpenseIncome(expenses_view,income_view,db,currentMonth,R.id.past_6_month);

                                break;
                            case R.id.past_year:
                                item.setChecked(true);
                                lastSelectedId[0] = R.id.past_year;
                                SharedPreferences.Editor editor3 = sharedPref.edit();
                                editor3.putInt("lastSelectedId", R.id.past_year);
                                editor3.apply();
                                setExpenseIncome(expenses_view,income_view,db,currentMonth,R.id.past_year);

                                break;
                            default:
                                return false;
                        }

                        return true;
                    }
                });

                // Set the default checked radio button before showing the popup
                MenuItem lastSelectedMenuItem = popup.getMenu().findItem(lastSelectedId[0]);
                lastSelectedMenuItem.setChecked(true);

                popup.show();
            }
        });







        return root;
    }

    private void setupUI(View v) {
        rvTransactions = v.findViewById(R.id.view_transaction);
    }

    private void recyclerViewTransaction(Context context, ArrayList<Transaction> trans) {
        ArrayList<Transaction> transactions = trans;


        adapter = new TransactionAdapter(getActivity(), transactions);
        rvTransactions.setAdapter(adapter);
        rvTransactions.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }

    private void showMenu(Context context, View v, int balance_menu) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.getMenuInflater().inflate(balance_menu, popup.getMenu());

        // Retrieve the state of the checkable item from SharedPreferences
        SharedPreferences menuPref = context.getSharedPreferences(getString(R.string.balance_menu), Context.MODE_PRIVATE);
        boolean thisMonthSelected = menuPref.getBoolean("menu_item_this_month", false);
        boolean threeMonthSelected = menuPref.getBoolean("menu_item_3_month", false);
        boolean sixMonthSelected = menuPref.getBoolean("menu_item_6_month", false);
        boolean yearSelected = menuPref.getBoolean("menu_item_year", false);
        if (thisMonthSelected)
            popup.getMenu().findItem(R.id.this_month).setChecked(thisMonthSelected);
        else if (threeMonthSelected) {
            popup.getMenu().findItem(R.id.past_3_month).setChecked(threeMonthSelected);
        } else if (sixMonthSelected) {
            popup.getMenu().findItem(R.id.past_6_month).setChecked(sixMonthSelected);
        } else if (yearSelected) {
            popup.getMenu().findItem(R.id.past_year).setChecked(yearSelected);
        }
        else{
            popup.getMenu().findItem(R.id.this_month).setChecked(true);
        }


        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.balance_menu),Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                // Handle item selection
                switch (menuItem.getItemId()) {
                    case R.id.this_month:
                        menuItem.setChecked(true);
                        editor.putBoolean("menu_item_this_month",true);
                        editor.putBoolean("menu_item_3_month",false);
                        editor.putBoolean("menu_item_6_month",false);
                        editor.putBoolean("menu_item_year",false);
                        editor.apply();
                        return true;
                    case R.id.past_3_month:
                        menuItem.setChecked(true);
                        editor.putBoolean("menu_item_this_month",false);
                        editor.putBoolean("menu_item_3_month",true);
                        editor.putBoolean("menu_item_6_month",false);
                        editor.putBoolean("menu_item_year",false);
                        editor.apply();
                        return true;
                    case R.id.past_6_month:
                        menuItem.setChecked(true);
                        editor.putBoolean("menu_item_this_month",false);
                        editor.putBoolean("menu_item_3_month",false);
                        editor.putBoolean("menu_item_6_month",true);
                        editor.putBoolean("menu_item_year",false);
                        editor.apply();
                        return true;
                    case R.id.past_year:
                        menuItem.setChecked(true);
                        editor.putBoolean("menu_item_this_month",false);
                        editor.putBoolean("menu_item_3_month",false);
                        editor.putBoolean("menu_item_6_month",false);
                        editor.putBoolean("menu_item_year",true);
                        editor.apply();
                        return true;
                    default:
                        return false;
                }
            }
        });

        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                // Respond to popup being dismissed.
            }
        });

        // Show the popup menu.
        popup.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}