package com.example.my_aac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.Nullable;

public class Option extends Activity {
    private int volume = 50;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        SeekBar volume_seekbar = findViewById(R.id.volume_seekbar);

        intent = getIntent();
        volume = intent.getIntExtra("volume_value", 50);

        volume_seekbar.setProgress(volume);
        volume_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                volume = seekBar.getProgress();
            }
        });
        Button option_save_button = findViewById(R.id.option_save_button);
        option_save_button.setOnClickListener(v -> {
            intent = new Intent();
            intent.putExtra("volume_value", volume);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }
}
