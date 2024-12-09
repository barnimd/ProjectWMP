package com.example.ngopskuy;

import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {
    private TextView paymentSummary;
    private ImageView user, home, custom;
    private RadioGroup paymentOptions;
    private Button confirmPayment;
    private ArrayList<Coffee> selectedCoffees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Inisialisasi komponen UI
        paymentSummary = findViewById(R.id.payment_summary);
        paymentOptions = findViewById(R.id.payment_options);
        confirmPayment = findViewById(R.id.confirm_payment);
        home = findViewById(R.id.home);
        custom = findViewById(R.id.custom_coffee);
        user = findViewById(R.id.user);

        // Ambil data dari Intent
        selectedCoffees = getIntent().getParcelableArrayListExtra("selectedCoffees");

        // Pastikan ArrayList tidak null
        if (selectedCoffees == null) {
            selectedCoffees = new ArrayList<>(); // Jika null, buat ArrayList baru
        }

        // Debugging: Log isi ArrayList
        if (selectedCoffees.isEmpty()) {
            Log.e("PaymentActivity", "selectedCoffees is null or empty!");
        } else {
            Log.d("PaymentActivity", "selectedCoffees contains: " + selectedCoffees.size() + " items");
            for (Coffee coffee : selectedCoffees) {
                Log.d("PaymentActivity", "Coffee: " + coffee.getName() + " - Rp " + coffee.getPrice());
            }
        }

        // Tampilkan detail pembayaran
        displayPaymentDetails();

        home.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        user.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, UserActivity.class);
            startActivity(intent);
        });

        custom.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, CustomActivity.class);
            startActivity(intent);
        });

        // Tombol konfirmasi pembayaran
        confirmPayment.setOnClickListener(v -> {
            int selectedOptionId = paymentOptions.getCheckedRadioButtonId();
            if (selectedOptionId == -1) {
                Toast.makeText(PaymentActivity.this, "Please select a payment method", Toast.LENGTH_SHORT).show();
                return;
            }

            // Ambil metode pembayaran yang dipilih
            RadioButton selectedOption = findViewById(selectedOptionId);
            String paymentMethod = selectedOption.getText().toString();

            // Vibrasi untuk konfirmasi pembayaran
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            if (vibrator != null) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            }

            Toast.makeText(this, "Payment Successful via " + paymentMethod, Toast.LENGTH_SHORT).show();

            // Kembali ke HomeActivity
            Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Membersihkan stack
            startActivity(intent);
        });
    }

    private void displayPaymentDetails() {
        if (selectedCoffees.isEmpty()) {
            paymentSummary.setText("No items in cart.");
            return;
        }

        StringBuilder summary = new StringBuilder();
        int total = 0;

        for (Coffee coffee : selectedCoffees) {
            summary.append(coffee.getName()).append(" - Rp ").append(coffee.getPrice()).append("\n");
            total += coffee.getPrice();
        }

        summary.append("\nTotal: Rp ").append(total);
        paymentSummary.setText(summary.toString());
    }
}
