package com.example.my_aac;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.io.IOException;

public class CreateGroup extends Activity {
    private Intent intent;
    private EditText name;
    private ImageView imageView;
    private Bitmap bitmap;
    private String imagePath;
    private int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_new_group);
        Context context = getApplicationContext();
        imagePath = context.getFilesDir() + "/images/";
        intent = getIntent();
        id = intent.getIntExtra("group_id", -1);
        if(id == -1){
            finish();
        }
        Log.d("CREATE_GROUP", String.valueOf(id));
        name = findViewById(R.id.insert_group_name);
        imageView = findViewById(R.id.insert_group_image);
        imageView.setOnClickListener(v -> {
            Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
            intent1.setType("image/*");

            startActivityForResult(intent1, 1);
        });
        imageView.setImageResource(R.drawable.no_image);
        Button submit = findViewById(R.id.group_submit_button);
        submit.setOnClickListener(v -> {
            String return_name = String.valueOf(name.getText());
            if(bitmap != null){
                ManageGroup.saveBitmap(bitmap, imagePath, return_name);

                imagePath = imagePath + return_name + ".jpg";
                GroupModel groupModel = new GroupModel(id, return_name, imagePath);
                intent = new Intent();
                intent.putExtra("group_model", groupModel);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uri = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                 imageView.setImageBitmap(bitmap);
                 bitmap = Bitmap.createScaledBitmap(bitmap, 125, 125, true);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
