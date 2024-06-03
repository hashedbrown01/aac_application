package com.example.my_aac;

import static android.speech.tts.TextToSpeech.ERROR;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


public class ManageAAC {
    private static TextToSpeech tts;
    public static void showAAC(Context context, GridLayout gridLayout, AACModel aacModel){
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.aac_button, null);
        TextView textView = layout.findViewById(R.id.aac_name);
        ImageView imageView = layout.findViewById(R.id.aac_image);

        textView.setText(aacModel.getAacTitle());
        imageView.setImageBitmap(loadBitmap(aacModel.getFilePath()));
        layout.setOnClickListener(v -> {
            //여기에 구현
            speak(context, aacModel.getAacDescription(), aacModel.getVoiceType());

            saveRecentAAC(context, aacModel.getId());

            Intent intent = new Intent(context, ImageActivity.class);
            intent.putExtra("imagePath", aacModel.getFilePath());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        });

        layout.setOnLongClickListener(v -> {
            SPManager spManager = new SPManager(context);
            List<AACModel> list = spManager.getAACList();
            Iterator<AACModel> iterator = list.iterator();
            while (iterator.hasNext()) {
                AACModel model = iterator.next();
                if (model.getId() == aacModel.getId()) {
                    iterator.remove();

                    removeRecentAAC(context, aacModel.getId());
                    deleteFile(aacModel.getFilePath(), aacModel.getAacTitle());
                    spManager.saveAACList(list);
                    break;
                }
            }
            gridLayout.removeView(v);
            return true;
        });

        gridLayout.addView(layout);
    }
    public static void createAAC(Context context, GridLayout gridLayout, AACModel aacModel){

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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                out = Files.newOutputStream(fileCache.toPath());
            }

            assert out != null;
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

    public static void deleteFile(String filePath, String filename) {
        File file = new File(filePath + filename + ".jpg");
        if (file.exists()) {
            file.delete();
        }

    }

    private static void saveRecentAAC(Context context, int aacId){
        SharedPreferences sharedPreferences = context.getSharedPreferences("recent_aac_log", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String recentId = sharedPreferences.getString("recent_aac_id", "");
        StringBuilder newrecentAACId = new StringBuilder(String.valueOf(aacId));
        if(!recentId.isEmpty()){
            newrecentAACId.append(",").append(recentId);
        }

        String[] idArray = newrecentAACId.toString().split(",");
        if(idArray.length > 4){
            StringBuilder trimmedId = new StringBuilder();

            for(int i = 0; i < 4; i++){
                if(i > 0){
                    trimmedId.append(",");
                }
                trimmedId.append(idArray[i]);
            }
            newrecentAACId = trimmedId;
        }

        editor.putString("recent_aac_id", newrecentAACId.toString());
        editor.apply();
    }
    private static void removeRecentAAC(Context context, int aacId){
        SharedPreferences sharedPreferences = context.getSharedPreferences("recent_aac_log", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String recentId = sharedPreferences.getString("recent_aac_id", "");
        String[] idArray = recentId.split(",");
        StringBuilder newRecentId = new StringBuilder();
        for(String idString : idArray){
            if(!idString.isEmpty()){
                int id = Integer.parseInt(idString);
                if(id != aacId){
                    if(newRecentId.length() > 0){
                        newRecentId.append(",");
                    }
                    newRecentId.append(id);
                }
            }
        }

        editor.putString("recent_aac_id", newRecentId.toString());
        editor.apply();
    }

    public static void speak(Context context, String text, String voiceType){
        tts = new TextToSpeech(context, status -> {
            if(status != ERROR){
                SharedPreferences sharedPreferences = context.getSharedPreferences("volume_value", 0);
                float volume = (float) (sharedPreferences.getInt("volume_value", 0) * 0.01);

                if(voiceType.equals("male 1")){
                    Voice v = new Voice("en-gb-x-gbd-network", new Locale("en", "us"), 400, 200, false, null);
                    tts.setVoice(v);
                }else{
                    Voice v = new Voice(" en-us-x-tpf-network", new Locale("en", "us"), 400, 200, false, null);
                    tts.setVoice(v);
                }

                Bundle tts_bundle = new Bundle();
                tts_bundle.putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, volume);
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, tts_bundle, null);
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
        });
    }
}

