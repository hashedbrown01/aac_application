package com.example.my_aac;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

public class ManageGroup {
    public static void showGroup(Context context, LinearLayout layout, GroupModel groupModel){
        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(loadBitmap(groupModel.getImagePath()));
        imageView.setId(groupModel.getId());
        float scale = context.getResources().getDisplayMetrics().density;
        int dp = (int) (125 * scale + 0.5f);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(dp, dp));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), AacGroup.class);
                intent.putExtra("group_id", groupModel.getId());
                intent.putExtra("group_name", groupModel.getName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SPManager spManager = new SPManager(context);
                List<GroupModel> list = spManager.getGroupList();
                Iterator<GroupModel> iterator = list.iterator();
                while (iterator.hasNext()) {
                    GroupModel model = iterator.next();
                    if (model.getId() == groupModel.getId()) {
                        // AACModel 삭제
                        List<AACModel> aac_list = spManager.getAACList();
                        Iterator<AACModel> aac_iterator = aac_list.iterator();
                        while (aac_iterator.hasNext()) {
                            AACModel aacModel = aac_iterator.next();
                            if (aacModel.getParent_id() == groupModel.getId()) {
                                // AACModel 삭제
                                aac_iterator.remove();
                                deleteFile(aacModel.getFilePath(), aacModel.getAacTitle());
                            }
                        }

                        // GroupModel 삭제
                        iterator.remove();
                        deleteFile(groupModel.getImagePath(), groupModel.getName());

                        // 저장
                        spManager.saveAACList(aac_list);
                        spManager.saveGroupList(list);
                        layout.removeView(v);
                        Toast.makeText(context, "The Group and AAC Buttons are Deleted", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                return true; // 롱 클릭 이벤트를 소비함
            }
        });
        layout.addView(imageView);
    }

    public static void createGroup(Context context, LinearLayout layout, GroupModel groupModel) {

        Bitmap bitmap = loadBitmap(groupModel.getImagePath());

        SPManager spManager = new SPManager(context);
        List<GroupModel> list = spManager.getGroupList();

        list.add(groupModel);
        spManager.saveGroupList(list);

        showGroup(context, layout, groupModel);

    }

    public static Bitmap loadBitmap(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static void saveBitmap(Bitmap bitmap, String path, String filename){
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }

        File fileCache = new File(path + filename + ".jpg");
        OutputStream out = null;

        try{
            fileCache.createNewFile();
            out = new FileOutputStream(fileCache);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if(out != null){
                    out.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static boolean deleteFile(String filePath, String filename) {
        File file = new File(filePath + filename + ".jpg");
        if (file.exists()) {
            if (file.delete()) {
                // 파일 삭제 성공
                return true;
            } else {
                // 파일 삭제 실패
                return false;
            }
        } else {
            // 파일이 존재하지 않음
            return false;
        }
    }
}
