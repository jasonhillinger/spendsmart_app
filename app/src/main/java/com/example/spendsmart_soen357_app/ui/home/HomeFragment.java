package com.example.spendsmart_soen357_app.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendsmart_soen357_app.Adapter.TransactionAdapter;
import com.example.spendsmart_soen357_app.Model.Transaction;
import com.example.spendsmart_soen357_app.R;
import com.example.spendsmart_soen357_app.databinding.FragmentHomeBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView.Adapter adapter;
    private RecyclerView rvTransactions;

    private boolean toggle_account = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //Setup UI elements
        setupUI(root);

        Button month = binding.btnMonth;
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(getContext(),view, R.menu.balance_menu);
            }
        });

        ConstraintLayout walletBalanceCard = binding.walletBalanceCard;

        walletBalanceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toggle_account){
                    Toast.makeText(getContext(), "You clicked the balance card! SHOWING CHECKING ACCCOUNT", Toast.LENGTH_SHORT).show();
                    binding.tvBalance.setText(R.string.checkingBalance);
                    toggle_account = false;
                }else{
                    Toast.makeText(getContext(), "You clicked the balance card! SHOWING SAVING ACCCOUNT", Toast.LENGTH_SHORT).show();
                    binding.tvBalance.setText(R.string.savingBalance);
                    toggle_account = true;
                }

            }
        });

        // Set transaction view
        recyclerViewTransaction(getContext());

        return root;
    }

    private void setupUI(View v) {
        rvTransactions = v.findViewById(R.id.view_transaction);
    }

    private void recyclerViewTransaction(Context context) {
        ArrayList<Transaction> transactions = new ArrayList<>();

        Date date1 = null;
        Date date2 = null;
        Date time1 = null;
        Date time2 = null;

        try{
            // Formatting the date and time
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date1 = dateFormat.parse("2023-04-20");
            date2 = dateFormat.parse("2023-04-15");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            time1 = timeFormat.parse("04:20");
            time2 = timeFormat.parse("06:20");

        } catch (ParseException e) {
            e.printStackTrace();
        }


        transactions.add(new Transaction("Checking Account",140.00,"Food and groceries","Tim Hortons",
                "Purchase",date1,time1, "Jackey"));

        transactions.add(new Transaction("Checking Account",10.00,"Services and subscriptions","Spotify Premium",
                "Purchase",date2,time2, "Jackey"));

        transactions.add(new Transaction("Checking Account",10.00,"Services and subscriptions","Transfer",
                "Income",date2,time2, "Jackey"));

        transactions.add(new Transaction("Checking Account",10.00,"Services and subscriptions","Transfer",
                "Income",date2,time2, "Jackey"));

        transactions.add(new Transaction("Checking Account",10.00,"Services and subscriptions","Transfer",
                "Income",date2,time2, "Jackey"));


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
        }else{
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