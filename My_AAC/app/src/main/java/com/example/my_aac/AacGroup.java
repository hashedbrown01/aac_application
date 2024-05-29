package com.example.my_aac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.List;

public class AacGroup extends Activity {
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

        spManager = new SPManager(getApplicationContext());
        aac_list = spManager.getAACList();

        if(aac_list !=null){
            for(int i = 0; i < aac_list.size(); i++){
                AACModel aacModel = aac_list.get(i);
                if(aacModel.getParent_id() == parent_id){
                    //부모 뷰가 맞을 때 확인하고 뷰 생성
                }
            }
        }

        intent = getIntent();
        parent_id = intent.getIntExtra("group_id", 0);

        textView = findViewById(R.id.group_name);
        textView.setText("GROUP " + String.valueOf(parent_id));

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
    }
}
