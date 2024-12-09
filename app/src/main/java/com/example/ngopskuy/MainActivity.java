package com.example.ngopskuy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi FirebaseApp jika belum dilakukan
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
        }

        // Menambahkan delay 2 detik untuk menampilkan Splash Screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Setelah delay, menuju ke LoginActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Tutup Splash Screen agar tidak kembali ke MainActivity
            }
        }, 2000); // 2000 ms = 2 detik
    }
}
