package com.example.spendsmart_soen357_app.ui.budget;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.app.Activity;

public class BudgetFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<RecyclerModel> userList;
    Adapter adapter;
   // private static final int ADD_BUDGET_REQUEST_CODE = 1;

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
                //Toast.makeText(getContext(), "it works!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), AddBudgetActivity.class);
                addBudgetLauncher.launch(intent);
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
        userList.add(new RecyclerModel(R.drawable.ic_income, "100$", "Personal", "Income"));
        userList.add(new RecyclerModel(R.drawable.ic_food_and_groceries, "200$", "Food", "Food and Groceries"));
        userList.add(new RecyclerModel(R.drawable.ic_clothing_and_accessory, "100$", "Clothing", "Clothing and Accessory"));

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

    private final ActivityResultLauncher<Intent> addBudgetLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            // Get the input data from the intent
                            String name = data.getStringExtra("name");
                            String category = data.getStringExtra("category");
                            String price = data.getStringExtra("price");
                            int imageResource = data.getIntExtra("imageResource", R.drawable.ic_income);

                            // Add the new data to the list and update the RecyclerView
                            userList.add(new RecyclerModel(imageResource, price, name, category));
                            adapter.notifyDataSetChanged();
                        }
                    });
}

