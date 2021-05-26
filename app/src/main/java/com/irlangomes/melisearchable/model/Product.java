package com.irlangomes.melisearchable.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Product implements Serializable, Parcelable {
    public String code;
    public String id;
    public String title;
    private String price;
    public String thumbnail;
    public List<Picture> pictures;
    public String rating;

    public Product() {
    }

    public String getPrice() {
        String format = NumberFormat
                .getCurrencyInstance(new Locale("pt","BR"))
                .format(Float.parseFloat(price));
        return format;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.title);
        parcel.writeString(this.price);
        parcel.writeString(this.thumbnail);
        parcel.writeList(this.pictures);
    }

    protected Product(Parcel parcel){
        this.id = parcel.readString();
        this.title = parcel.readString();
        this.thumbnail = parcel.readString();
        pictures = new ArrayList<>();
        parcel.readList(pictures, Picture.class.getClassLoader());
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
