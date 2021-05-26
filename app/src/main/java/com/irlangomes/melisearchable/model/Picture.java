package com.irlangomes.melisearchable.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Picture implements Parcelable {
    public String id;
    public String url;

    public Picture() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.url);
    }

    protected Picture(Parcel parcel) {
        this.id = parcel.readString();
        this.url = parcel.readString();
    }

    public static final Parcelable.Creator<Picture> CREATOR = new Parcelable.Creator<Picture>() {
        public Picture createFromParcel(Parcel source) {
            return new Picture(source);
        }

        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };
}
