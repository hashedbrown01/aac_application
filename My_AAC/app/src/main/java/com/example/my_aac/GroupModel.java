package com.example.my_aac;

public class GroupModel {
    private int id;
    String imageId;

    public GroupModel(int id, String imageId) {
        this.id = id;
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }


}
