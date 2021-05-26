package com.irlangomes.melisearchable.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Product implements Serializable {
    public String code;
    public String id;
    public String title;
    private String price;
    public String thumbnail;
    public List<Picture> pictures;
    public String rating;

    public String getPrice() {
        String format = NumberFormat
                .getCurrencyInstance(new Locale("pt","BR"))
                .format(Float.parseFloat(price));
        return format;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
