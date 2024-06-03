package com.example.my_aac;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_GOTO_OPTION = 2;
    private Intent intent;
    private int volume;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("volume_value", Context.MODE_PRIVATE);
        getPreferences();

        ImageButton goto_aac = findViewById(R.id.goto_aac);
        goto_aac.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), AacMain.class);
            startActivity(intent);
        });
        ImageButton goto_qr_reading = findViewById(R.id.goto_qr_reading);
        goto_qr_reading.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
            } else {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
            }
        });
        ImageButton goto_option = findViewById(R.id.goto_option);
        goto_option.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), Option.class);
            intent.putExtra("volume_value", volume);
            startActivityForResult(intent, REQUEST_GOTO_OPTION);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null && requestCode == REQUEST_GOTO_OPTION){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("volume_value", data.getIntExtra("volume_value", 50));
            editor.apply();
            getPreferences();

            Toast.makeText(getApplicationContext(), "Option is Saved! Volume Value is now: " + volume, Toast.LENGTH_SHORT).show();
        }
    }
    private void getPreferences(){
        volume = sharedPreferences.getInt("volume_value", 50);
    }
}