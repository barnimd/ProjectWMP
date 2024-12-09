package com.example.ngopskuy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CustomActivity extends AppCompatActivity {

    private RadioGroup rgCoffeeStrength, rgSugar, rgMilk;
    private TextView tvTotalPrice;
    private ImageView home, custom, user;
    private Button tombolorder;

    private int coffeePrice = 0;
    private int sugarPrice = 0;
    private int milkPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        rgCoffeeStrength = findViewById(R.id.rg_coffee_strength);
        rgSugar = findViewById(R.id.rg_sugar);
        rgMilk = findViewById(R.id.rg_milk);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        tombolorder = findViewById(R.id.tombol_order);
        home = findViewById(R.id.home);
        custom = findViewById(R.id.custom_coffee);
        user = findViewById(R.id.user);

        // Listener for calculating changing price
        rgCoffeeStrength.setOnCheckedChangeListener((group, checkedId) -> calculateTotalPrice());
        rgSugar.setOnCheckedChangeListener((group, checkedId) -> calculateTotalPrice());
        rgMilk.setOnCheckedChangeListener((group, checkedId) -> calculateTotalPrice());

        home.setOnClickListener(v -> {
            Intent intent = new Intent(CustomActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        user.setOnClickListener(v -> {
            Intent intent = new Intent(CustomActivity.this, UserActivity.class);
            startActivity(intent);
        });

        custom.setOnClickListener(v -> {
            Intent intent = new Intent(CustomActivity.this, CustomActivity.class);
            startActivity(intent);
        });

        // button order
        tombolorder.setOnClickListener(v -> {
            if (isSelectionValid()) {
                String coffeeOrder = getSelectedOptions();
                int totalPrice = coffeePrice + sugarPrice + milkPrice;

                Intent intent = new Intent(CustomActivity.this, OrderActivity.class);
                intent.putExtra("coffeeOrder", coffeeOrder);
                intent.putExtra("totalPrice", totalPrice);
                startActivity(intent);
            } else {
                Toast.makeText(CustomActivity.this, "Silakan pilih semua opsi terlebih dahulu!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isSelectionValid() {
        return rgCoffeeStrength.getCheckedRadioButtonId() != -1 &&
                rgSugar.getCheckedRadioButtonId() != -1 &&
                rgMilk.getCheckedRadioButtonId() != -1;
    }

    private String getSelectedOptions() {
        RadioButton selectedCoffeeStrength = findViewById(rgCoffeeStrength.getCheckedRadioButtonId());
        RadioButton selectedSugar = findViewById(rgSugar.getCheckedRadioButtonId());
        RadioButton selectedMilk = findViewById(rgMilk.getCheckedRadioButtonId());

        return selectedCoffeeStrength.getText().toString() + ", " +
                selectedSugar.getText().toString() + ", " +
                selectedMilk.getText().toString();
    }

    private void calculateTotalPrice() {
        // declare price
        coffeePrice = 0;
        sugarPrice = 0;
        milkPrice = 0;

        // calculating coffee price
        int coffeeCheckedId = rgCoffeeStrength.getCheckedRadioButtonId();
        if (coffeeCheckedId == R.id.rb_weak) {
            coffeePrice = 5000;
        } else if (coffeeCheckedId == R.id.rb_medium) {
            coffeePrice = 8000;
        } else if (coffeeCheckedId == R.id.rb_strong) {
            coffeePrice = 10000;
        }

        // calculating sugar price
        int sugarCheckedId = rgSugar.getCheckedRadioButtonId();
        if (sugarCheckedId == R.id.rb_normal_sugar) {
            sugarPrice = 3000;
        }

        // calculating milk price
        int milkCheckedId = rgMilk.getCheckedRadioButtonId();
        if (milkCheckedId == R.id.rb_with_milk) {
            milkPrice = 5000;
        }

        // total price
        int totalPrice = coffeePrice + sugarPrice + milkPrice;
        tvTotalPrice.setText("Total Harga: Rp " + totalPrice);
    }
}
