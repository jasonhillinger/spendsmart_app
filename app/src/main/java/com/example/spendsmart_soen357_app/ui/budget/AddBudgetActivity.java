package com.example.spendsmart_soen357_app.ui.budget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spendsmart_soen357_app.R;

public class AddBudgetActivity extends AppCompatActivity {
    EditText nameField;
    Spinner categorySpinner;
    EditText priceField;
    Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_budget);

        getSupportActionBar().hide();

         nameField = findViewById(R.id.nameField);
         categorySpinner = findViewById(R.id.categorySpinner);
         priceField = findViewById(R.id.priceField);
         confirmButton = findViewById(R.id.confirmButton);

        // Add a click listener to the confirm button
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input data
                String name = nameField.getText().toString();
                String category = categorySpinner.getSelectedItem().toString();
                String price = priceField.getText().toString();

                // Get the appropriate image based on the selected category
                int imageResource;
                switch (category) {
                    case "Income":
                        imageResource = R.drawable.ic_income;
                        break;
                    case "Food and Groceries":
                        imageResource = R.drawable.ic_food_and_groceries;
                        break;
                    case "Clothing and Accessories":
                        imageResource = R.drawable.ic_clothing_and_accessory;
                        break;
                    case "Electronics":
                        imageResource = R.drawable.ic_electronics;
                        break;
                    case "Services and Subscriptions":
                        imageResource = R.drawable.ic_services_and_subscriptions;
                        break;
                    case "Travel":
                        imageResource = R.drawable.ic_travel;
                        break;
                    default:
                        imageResource = R.drawable.ic_income;
                        break;
                }

                // Create an intent to pass the data back to the BudgetFragment
                Intent intent = new Intent();
                intent.putExtra("name", name);
                intent.putExtra("category", category);
                intent.putExtra("price", price +"$");
                intent.putExtra("imageResource", imageResource);
                setResult(RESULT_OK, intent);

                // Finish the activity and return to the BudgetFragment
                finish();
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}
