package com.example.my_aac;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;

public class CreateAAC extends Activity {
    private EditText name;
    private EditText description;
    private Spinner spinner;
    private Button submit;
    private Intent intent;
    private String imagePath;
    private int id;
    private int parent_id;
    private ImageView imageView;
    private Bitmap bitmap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_new_button);
        Context context = getApplicationContext();
        name = findViewById(R.id.insert_aac_name);
        description = findViewById(R.id.insert_aac_description);
        spinner = findViewById(R.id.voice_select_spinner);
        submit = findViewById(R.id.aac_submit_button);
        imagePath = String.valueOf(context.getFilesDir()) + "/aac_images/";

        intent = getIntent();
        id = View.generateViewId();
        parent_id = intent.getIntExtra("parent_id", 0);

        String inputName = name.getText().toString().trim();
        String inputDescription = description.getText().toString().trim();
        imageView = findViewById(R.id.insert_aac_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType("image/*");

                startActivityForResult(intent1, 1);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();

                if (inputName != null && inputDescription != null) {
                    String return_name = String.valueOf(name.getText());
                    String return_desc = String.valueOf(description.getText());
                    String return_voice = (String) spinner.getSelectedItem();


                    ManageAAC.saveBitmap(bitmap, imagePath, return_name);

                    imagePath = imagePath + return_name + ".jpg";
                    AACModel aacModel = new AACModel(id, parent_id, imagePath, return_name, return_desc, return_voice);
                    intent.putExtra("aac_model", aacModel);

                    setResult(RESULT_OK, intent);
                    Toast.makeText(getApplicationContext(), "AAC button is successfuly added!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Wrong Values in input.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        onStart();
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
