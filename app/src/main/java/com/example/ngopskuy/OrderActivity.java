package com.example.ngopskuy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    private TextView orderSummary;
    private TextView totalPrice;
    private Button payButton;
    private ImageView user, home, custom;
    private ArrayList<Coffee> selectedCoffees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Inisialisasi komponen UI
        orderSummary = findViewById(R.id.order_summary);
        totalPrice = findViewById(R.id.total_price);
        payButton = findViewById(R.id.pay_button);

        home = findViewById(R.id.home);
        custom = findViewById(R.id.custom_coffee);
        user = findViewById(R.id.user);

        // Ambil data dari Intent
        selectedCoffees = getIntent().getParcelableArrayListExtra("selectedCoffees");

        // Ambil data custom order (jika ada)
        String coffeeOrder = getIntent().getStringExtra("coffeeOrder");
        int customOrderPrice = getIntent().getIntExtra("totalPrice", 0);

        // Pastikan ArrayList tidak null
        if (selectedCoffees == null) {
            selectedCoffees = new ArrayList<>(); // Jika null, buat ArrayList baru
        }

        // Tambahkan custom order ke dalam daftar pesanan
        if (coffeeOrder != null && customOrderPrice > 0) {
            Coffee customCoffee = new Coffee(coffeeOrder, customOrderPrice);
            selectedCoffees.add(customCoffee);
        }

        // Debugging: Log isi ArrayList
        if (selectedCoffees.isEmpty()) {
            Log.e("OrderActivity", "selectedCoffees is null or empty!");
        } else {
            Log.d("OrderActivity", "selectedCoffees contains: " + selectedCoffees.size() + " items");
            for (Coffee coffee : selectedCoffees) {
                Log.d("OrderActivity", "Coffee: " + coffee.getName() + " - Rp " + coffee.getPrice());
            }
        }

        // Tampilkan daftar pesanan
        displayOrder();

        home.setOnClickListener(v -> {
            Intent intent = new Intent(OrderActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        user.setOnClickListener(v -> {
            Intent intent = new Intent(OrderActivity.this, UserActivity.class);
            startActivity(intent);
        });

        custom.setOnClickListener(v -> {
            Intent intent = new Intent(OrderActivity.this, CustomActivity.class);
            startActivity(intent);
        });

        // Tombol ke PaymentActivity
        payButton.setOnClickListener(v -> {
            Intent intent = new Intent(OrderActivity.this, PaymentActivity.class);
            intent.putParcelableArrayListExtra("selectedCoffees", selectedCoffees); // Kirim data ke PaymentActivity
            startActivity(intent);
        });
    }

    private void displayOrder() {
        if (selectedCoffees.isEmpty()) {
            orderSummary.setText("No items in cart.");
            totalPrice.setText("Total: Rp 0");
            return;
        }

        StringBuilder summary = new StringBuilder();
        int total = 0;

        for (Coffee coffee : selectedCoffees) {
            summary.append(coffee.getName()).append(" - Rp ").append(coffee.getPrice()).append("\n");
            total += coffee.getPrice();
        }

        orderSummary.setText(summary.toString());
        totalPrice.setText("Total: Rp " + total);
    }
}
