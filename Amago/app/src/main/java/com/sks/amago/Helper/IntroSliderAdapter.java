package com.sks.amago.Helper;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sks.amago.R;

/**
 * Coded by JAKfromSpace on 03-Apr-19 for SKS.
 */
public class IntroSliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;

    public int[] imagesArray = {R.drawable.im1, R.drawable.im2, R.drawable.im3, R.drawable.im4};
    public String[] titles = {"ফসল","উৎপাদন ","আপলোড","অর্পণ"};

    // list of descriptions
    public String[] descriptions = {"আপনার ফসল হবে।",
            "সেটা চাষ করবেন।",
            "তারপর আপলোড দিবেন।",
            "আমরা ডেলিভার করে দিবো!"};

    // list of background colors
    public int[]  backgroundcolors = {
            Color.rgb(255,255,125),
            Color.rgb(255,255,125),
            Color.rgb(255,255,125),
            Color.rgb(255,255,125)
    };


    public IntroSliderAdapter(Context context){this.context = context;}

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view==o);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide,container,false);

        LinearLayout layoutslide = view.findViewById(R.id.slidelinlayout);
        ImageView imgslide = view.findViewById(R.id.slideimg);
        TextView txttitle= view.findViewById(R.id.slidetitle);
        TextView description = view.findViewById(R.id.slidedesc);
        layoutslide.setBackgroundColor(backgroundcolors[position]);
        imgslide.setImageResource(imagesArray[position]);
        txttitle.setText(titles[position]);
        description.setText(descriptions[position]);
        container.addView(view);
        return view;
    }
}
