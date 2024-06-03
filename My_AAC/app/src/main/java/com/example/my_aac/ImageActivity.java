package com.example.my_aac;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        Intent intent = getIntent();

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.image_popup);
        ImageView imageView = findViewById(R.id.popup_image);
        String path = intent.getStringExtra("imagePath");
        imageView.setImageBitmap(ManageAAC.loadBitmap(path));
    }
}
