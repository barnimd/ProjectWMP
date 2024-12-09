package com.example.ngopskuy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private TextView usernameText, appTitle;
    private ImageView profileImage, home, custom, user, cartIcon;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        usernameText = findViewById(R.id.username_text);
        profileImage = findViewById(R.id.profile_image);
        logoutButton = findViewById(R.id.logout_button);
        home = findViewById(R.id.home);
        custom = findViewById(R.id.custom_coffee);
        user = findViewById(R.id.user);
        cartIcon = findViewById(R.id.cart_icon);
        appTitle = findViewById(R.id.app_title);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Username");
        usernameText.setText(username);

        cartIcon.setOnClickListener(v -> {
                    Intent intent = new Intent(UserActivity.this, OrderActivity.class);
                    startActivity(intent);
                });
        appTitle.setOnClickListener(v -> {
                    Intent intent = new Intent(UserActivity.this, HomeActivity.class);
                    startActivity(intent);
                });

        home.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, HomeActivity.class);
            startActivity(intent);
        });
        custom.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, CustomActivity.class);
            startActivity(intent);
        });

        user.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, UserActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(UserActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        profileImage.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(UserActivity.this, android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(UserActivity.this, new String[]{android.Manifest.permission.CAMERA},
                        REQUEST_CAMERA_PERMISSION);
            } else {
                openCamera();
            }
        });
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            profileImage.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Kamera permission diperlukan untuk mengambil foto", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
