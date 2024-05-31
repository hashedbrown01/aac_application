package com.example.my_aac;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import static android.speech.tts.TextToSpeech.ERROR;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ManageAAC {
    private static TextToSpeech tts;
    public static void showAAC(Context context, GridLayout gridLayout, AACModel aacModel){
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.aac_button, null);
        TextView textView = layout.findViewById(R.id.aac_name);
        ImageView imageView = layout.findViewById(R.id.aac_image);
        SPManager spManager = new SPManager(context);

        textView.setText(aacModel.getAacTitle());
        imageView.setImageBitmap(loadBitmap(aacModel.getFilePath()));
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //여기에 구현
                speak(context, aacModel.getAacDescription());
            }
        });

        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SPManager spManager = new SPManager(context);
                List<AACModel> list = spManager.getAACList();
                Iterator<AACModel> iterator = list.iterator();
                while (iterator.hasNext()) {
                    AACModel model = iterator.next();
                    if (model.getId() == aacModel.getId()) {
                        iterator.remove();
                        deleteFile(aacModel.getFilePath(), aacModel.getAacTitle());
                        spManager.saveAACList(list);
                        break;
                    }
                }
                gridLayout.removeView(v);
                return true;
            }
        });

        gridLayout.addView(layout);
    }
    public static void createAAC(Context context, GridLayout gridLayout, AACModel aacModel){
        Bitmap bitmap = loadBitmap(aacModel.getFilePath());

        SPManager spManager = new SPManager(context);
        List<AACModel> list = spManager.getAACList();

        if(list == null){
            list = new ArrayList<>();
        }
        list.add(aacModel);
        spManager.saveAACList(list);

        showAAC(context, gridLayout, aacModel);
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

    public static void speak(Context context, String text){
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR){
                    tts.setLanguage(Locale.ENGLISH);
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String utteranceId) {}

                        @Override
                        public void onDone(String utteranceId) {
                            tts.shutdown();
                        }

                        @Override
                        public void onError(String utteranceId) {}
                    });
                }
            }
        });
    }
}

