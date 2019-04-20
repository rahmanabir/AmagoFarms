package com.sks.amago;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sks.amago.Helper.IntroSliderAdapter;

public class IntroSlider extends AppCompatActivity {

    ViewPager viewPager;
    IntroSliderAdapter introSliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);

        viewPager = findViewById(R.id.viewpagerintro);
        introSliderAdapter = new IntroSliderAdapter(this);
        viewPager.setAdapter(introSliderAdapter);

        Button okbutton = findViewById(R.id.slidebutton);
        okbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(IntroSlider.this, Login.class));
            }
        });

//        Paper.init(this);
//        String language = Paper.book().read("language");
//        if(language == null)
//            Paper.book().write("language", "bn");
    }

    public void GotoLogin(View view) {
        startActivity(new Intent(IntroSlider.this, Login.class));
    }
}
