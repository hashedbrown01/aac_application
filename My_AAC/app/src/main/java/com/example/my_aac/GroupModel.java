package com.example.my_aac;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class GroupModel implements Parcelable {
    private int id;
    String name;
    String imagePath;

    public GroupModel(int id, String name, String imagePath) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(imagePath);
    }
    protected GroupModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        imagePath = in.readString();
    }
    public static final Creator<GroupModel> CREATOR = new Creator<GroupModel>() {
        @Override
        public GroupModel createFromParcel(Parcel in) {
            return new GroupModel(in);
        }

        @Override
        public GroupModel[] newArray(int size) {
            return new GroupModel[size];
        }
    };
}
