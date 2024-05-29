package com.example.my_aac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class CreateAAC extends Activity {
    private EditText name;
    private EditText description;
    private ImageView image;
    private Spinner spinner;
    private Button submit;
    private Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_new_button);

        name = findViewById(R.id.insert_aac_name);
        description = findViewById(R.id.insert_aac_description);
        image = findViewById(R.id.insert_aac_image);
        spinner = findViewById(R.id.voice_select_spinner);
        submit = findViewById(R.id.aac_submit_button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();

                String inputName = name.getText().toString().trim();
                String inputDescription = description.getText().toString().trim();

                if (!inputName.isEmpty() && !inputDescription.isEmpty()) {
                    String return_name = String.valueOf(name.getText());
                    String return_desc = String.valueOf(description.getText());
                    int return_imgId = image.getId();
                    int return_voice = spinner.getId();

                    intent.putExtra("aac_name", return_name);
                    intent.putExtra("aac_desc", return_desc);
                    intent.putExtra("image_id", return_imgId);
                    intent.putExtra("voice_type", return_voice);

                    setResult(RESULT_OK, intent);
                    Toast.makeText(getApplicationContext(), "AAC button is successfuly added!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                Toast.makeText(getApplicationContext(), "Wrong Values in input.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
