package com.example.spendsmart_soen357_app.ui.budget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendsmart_soen357_app.R;
import com.example.spendsmart_soen357_app.databinding.FragmentBudgetBinding;

import java.util.ArrayList;
import java.util.List;

public class BudgetFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<RecyclerModel> userList;
    Adapter adapter;

    private FragmentBudgetBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BudgetViewModel budgetViewModel =
                new ViewModelProvider(this).get(BudgetViewModel.class);

        binding = FragmentBudgetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ConstraintLayout btnBudget = binding.addBudgetButton;
        btnBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "it works!", Toast.LENGTH_LONG).show();

            }
        });


        //Setup UI elements
        setupUI(root);

        initData();
        initRecyclerView();
        return root;
    }

    private void setupUI(View root) {
        recyclerView = root.findViewById(R.id.budget_list_recyclerview);
    }

    private void initData() {
        userList = new ArrayList<>();
        userList.add(new RecyclerModel(R.drawable.ic_income, "100$", "Personal", "income"));
        userList.add(new RecyclerModel(R.drawable.ic_food_and_groceries, "200$", "Food", "food_and_groceries"));
        userList.add(new RecyclerModel(R.drawable.ic_clothing_and_accessory, "100$", "Clothing", "clothing_and_accessory"));

    }

    private void initRecyclerView(){
        //recyclerView= recyclerView.findViewById(R.id.budget_list_recyclerview);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new Adapter(getActivity(), userList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

//        final TextView textView = binding.textBudget;
//        budgetViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
