    package com.example.my_aac;

    import android.app.Activity;
    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.graphics.Bitmap;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.widget.Button;
    import android.widget.GridLayout;
    import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.LinearLayout;
    import android.widget.Toast;

    import androidx.annotation.Nullable;
    import androidx.constraintlayout.widget.Group;

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
        private LayoutInflater inflater;
        private View view;
        private LinearLayout container;
        private int id;
        private Bitmap bitmap;
        private int group_number = 0;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_aac_main);

            spManager = new SPManager(getApplicationContext());
            group_list = spManager.getGroupList();
            if(group_list != null){
                Log.d("AAC_MAIN", String.valueOf(group_list.size()));
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
            container = findViewById(R.id.group_container);
            for(int i = 0; i < group_number; i++){
                //group_number 만큼 Group을 생성, 그룹은 이미지 정보와 id를 가지고 있으며 id를 통해 AAC Button들과 연결됨
                GroupModel groupModel = group_list.get(i);
                ManageGroup.showGroup(getApplicationContext(), container, groupModel);
                //여기서 버튼을 꾹 누르면 Group을 제거하는 명령 또한 구현해야 함
            }

            //기본에 존재하는 3개의 그룹은 삭제할 수 없음
            add_group_button = findViewById(R.id.add_group_button);
            add_group_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    group_list = spManager.getGroupList();
                    if(group_list != null){
                        group_number = group_list.size();
                        if(group_number <= 1){
                            Intent intent1 = new Intent(getApplicationContext(), CreateGroup.class);
                            id = View.generateViewId();
                            intent1.putExtra("group_id", id);
                            startActivityForResult(intent1, 1);
                        }else{
                            Toast.makeText(getApplicationContext(), "You can't add group more!", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        //처음으로 그룹을 생성
                        group_number = 0;
                    }
                }
            });
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == 1 && resultCode == RESULT_OK && data != null){
                    container = findViewById(R.id.group_container);
                    GroupModel recieved_groupModel = data.getParcelableExtra("group_model");
                    ManageGroup.createGroup(getApplicationContext(), container, recieved_groupModel);
                }
        }
    }

