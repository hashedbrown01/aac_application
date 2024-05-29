package com.example.my_aac;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.List;


public class AacMain extends Activity {
    private List<GroupModel> group_list;
    private SPManager spManager;
    private LinearLayout group_container;
    private Button add_group_button;
    private Intent intent;

    private ImageButton basic_group0;
    private ImageButton basic_group1;
    private ImageButton basic_group2;

    private int group_number = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aac_main);

        spManager = new SPManager(getApplicationContext());

        group_list = spManager.getGroupList();
        if(group_list != null){
            group_number = group_list.size();
        }

        intent = new Intent(getApplicationContext(), AacGroup.class);
        basic_group0 = findViewById(R.id.basic_group0);
        basic_group0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("group_id", basic_group0.getId());
                startActivity(intent);
            }
        });
        basic_group1 = findViewById(R.id.basic_group1);
        basic_group1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("group_id", basic_group1.getId());
                startActivity(intent);
            }
        });
        basic_group2 = findViewById(R.id.basic_group2);
        basic_group2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton imageButton = new ImageButton(getApplicationContext());
                intent.putExtra("group_id", basic_group2.getId());
                startActivity(intent);
            }
        });

        for(int i = 0; i < group_number; i++){
            String temp_id = "custom_group" + String.valueOf(i);
            //group_number 만큼 Group을 생성, 그룹은 이미지 정보와 id를 가지고 있으며 id를 통해 AAC Button들과 연결됨
            //여기서 버튼을 꾹 누르면 Group을 제거하는 명령 또한 구현해야 함
            intent.putExtra("group_id", temp_id);
        }

        //기본에 존재하는 3개의 그룹은 삭제할 수 없음
        add_group_button = findViewById(R.id.add_group_button);
        add_group_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(group_list != null){
                    group_number = group_list.size();
                }

                if(group_number < 3){

                }else{
                    Toast.makeText(getApplicationContext(), "You can't add group more!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
