package com.irlangomes.melisearchable.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.irlangomes.melisearchable.R;

import java.util.List;

public class ProductDetailAdapter extends PagerAdapter {

    private Context context;
    private List<String> imageDetails;
    private int[] mImageIds = new int[]{
            R.drawable.mot1,
            R.drawable.mot2,
    };


    public ProductDetailAdapter(Context context) {
        this.context = context;
//        this.imageDetails = imageDetails;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(mImageIds[position]);
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
