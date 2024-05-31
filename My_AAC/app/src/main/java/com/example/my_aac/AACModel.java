package com.example.my_aac;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

//AAC의 정보를 저장하는 모델 클래스
public class AACModel implements Parcelable {
    //AAC를 구분하는 ID
    private int id;

    //해당 AAC를 포함하는 Group의 ID
    private int parent_id;
    private String filePath;
    //AAC의 제목
    private String aacTitle;

    //AAC의 설명
    private String aacDescription;
    //AAC의 Voice Type
    private String voiceType;

    public AACModel(){

    }
    public AACModel(int id, int parent_id, String filePath, String aacTitle, String aacDescription, String voiceType) {
        this.id = id;
        this.parent_id = parent_id;
        this.filePath = filePath;
        this.aacTitle = aacTitle;
        this.aacDescription = aacDescription;
        this.voiceType = voiceType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getAacTitle() {
        return aacTitle;
    }

    public void setAacTitle(String aacTitle) {
        this.aacTitle = aacTitle;
    }

    public String getAacDescription() {
        return aacDescription;
    }

    public void setAacDescription(String aacDescription) {
        this.aacDescription = aacDescription;
    }

    public String getVoiceType() {
        return voiceType;
    }

    public void setVoiceType(String voiceType) {
        this.voiceType = voiceType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(parent_id);
        dest.writeString(filePath);
        dest.writeString(aacTitle);
        dest.writeString(aacDescription);
        dest.writeString(voiceType);

    }
    protected AACModel(Parcel in) {
        id = in.readInt();
        parent_id = in.readInt();
        filePath = in.readString();
        aacTitle = in.readString();
        aacDescription = in.readString();
        voiceType = in.readString();
    }
    public static final Creator<AACModel> CREATOR = new Creator<AACModel>() {
        @Override
        public AACModel createFromParcel(Parcel in) {
            return new AACModel(in);
        }

        @Override
        public AACModel[] newArray(int size) {
            return new AACModel[size];
        }
    };
}

