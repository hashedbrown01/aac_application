package com.example.my_aac;

//AAC의 정보를 저장하는 모델 클래스
public class AACModel {
    //AAC를 구분하는 ID
    private int id;

    //해당 AAC를 포함하는 Group의 ID
    private int parent_id;

    private String imageId;
    //AAC의 제목
    private String aacTitle;

    //AAC의 설명
    private String aacDescription;

    //AAC의 Voice Type
    private String voiceType;

    public AACModel(int id, int parent_id, String imageId, String aacTitle, String aacDescription, String voiceType) {
        this.id = id;
        this.parent_id = parent_id;
        this.imageId = imageId;
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

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
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
}

