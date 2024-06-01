package com.example.my_aac;

import static com.example.my_aac.ManageAAC.loadBitmap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.List;

public class AacGroup extends Activity {
    private GridLayout gridLayout;
    private List<AACModel> aac_list;
    private int parent_id;
    TextView textView;
    Button add_aac;
    Intent intent;

    private SPManager spManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aac_group_detail);
       gridLayout = findViewById(R.id.aac_container);
        spManager = new SPManager(getApplicationContext());
        aac_list = spManager.getAACList();
        intent = getIntent();
        parent_id = intent.getIntExtra("group_id", 0);

        if(aac_list !=null){
            for(int i = 0; i < aac_list.size(); i++){

                AACModel aacModel = aac_list.get(i);
                if(parent_id == aacModel.getParent_id()){
                    //부모 뷰가 맞을 때 확인하고 뷰 생성
                    ManageAAC.showAAC(AacGroup.this, gridLayout, aacModel);
                }
            }
        }


        String parent_name = intent.getStringExtra("group_name");
        textView = findViewById(R.id.group_name);
        textView.setText("GROUP " + String.valueOf(parent_name));

        add_aac = findViewById(R.id.create_aac_button);
        add_aac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), CreateAAC.class);
                intent1.putExtra("parent_id", parent_id);

                startActivityForResult(intent1, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AACModel recieved_aacModel = data.getParcelableExtra("aac_model");
        ManageAAC.createAAC(getApplicationContext(), gridLayout, recieved_aacModel);
    }
}
