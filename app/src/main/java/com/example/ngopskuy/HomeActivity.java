package com.example.ngopskuy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ngopskuy.Coffee;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private ImageView cartIcon,custom_coffee, user, home;
    private ArrayList<Coffee> selectedCoffees = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView appTitle = findViewById(R.id.app_title);
        cartIcon = findViewById(R.id.cart_icon);
        home = findViewById(R.id.home);
        custom_coffee = findViewById(R.id.custom_coffee);
        user = findViewById(R.id.user);

        // Set name of the app on navbar
        appTitle.setText("NgopsKuy");

        home.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "You are already on the Home screen!", Toast.LENGTH_SHORT).show();
        });

        custom_coffee.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Navigating to CustomActivity", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this, CustomActivity.class);
            startActivity(intent);
        });

        user.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, UserActivity.class);
            startActivity(intent);
        });

        cartIcon.setOnClickListener(v -> {
            if (selectedCoffees == null || selectedCoffees.isEmpty()) {
                Toast.makeText(HomeActivity.this, "Cart is empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(HomeActivity.this, OrderActivity.class);
            ArrayList<Coffee> coffeesToSend = new ArrayList<>(selectedCoffees);
            intent.putParcelableArrayListExtra("selectedCoffees", coffeesToSend);
            startActivity(intent);

        });


        //coffee selection
        setupCoffeeSelection();
    }

    private void setupCoffeeSelection() {
        // list of coffee
        Coffee[] coffees = {
                new Coffee("Espresso", 15000),
                new Coffee("Americano", 20000),
                new Coffee("Cappuccino", 25000),
                new Coffee("Latte", 30000),
                new Coffee("Mocha", 35000),
                new Coffee("Macchiato", 40000)
        };

        // for all list of coffee, adding button listener "order"
        for (int i = 0; i < coffees.length; i++) {
            int resourceId = getResources().getIdentifier("btn_coffee_" + (i + 1), "id", getPackageName());
            Button button = findViewById(resourceId);

            int index = i; // Final variabel untuk lambda
            button.setOnClickListener(v -> {
                selectedCoffees.add(coffees[index]);
                Toast.makeText(HomeActivity.this, coffees[index].getName() + " added to cart!", Toast.LENGTH_SHORT).show();
            });
        }
    }
}