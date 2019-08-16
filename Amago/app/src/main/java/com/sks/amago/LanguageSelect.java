package com.sks.amago;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sks.amago.Helper.LocaleHelper;

import java.util.Locale;

public class LanguageSelect extends AppCompatActivity {

    private RadioGroup radioLangGroup;
    private RadioButton radioLangButton;
    private Button btnOK;
    private TextView textLang;
    boolean firsttime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_select);

        radioLangGroup = findViewById(R.id.radiogroupLang);
        btnOK = findViewById(R.id.button_langok);
        textLang = findViewById(R.id.textView2);

//        Paper.init(this);
//        String language = Paper.book().read("language");
//        if(language == null)
//            Paper.book().write("language", "bn");
//        updateView((String)Paper.book().read("language"));
    }

    public void LanguagePicked(View view) {
        int selectedId = radioLangGroup.getCheckedRadioButtonId();
        radioLangButton = (RadioButton) findViewById(selectedId);
        String lang = radioLangButton.getText().toString();

        SharedPreferences sharedPrefs = getSharedPreferences("com.sks.amago.userprefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean("firsttime", false);
        editor.apply();

        Toast.makeText(this,
                lang+" Selected", Toast.LENGTH_SHORT).show();
        if (lang.equalsIgnoreCase(" English")) {
            updateView("en");
//            LocaleHelper.changeLocale(this.getResources(),"en");
            Locale locale = new Locale("en");
            Configuration config = getBaseContext().getResources().getConfiguration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
        else {
            updateView("bn");
//            LocaleHelper.changeLocale(this.getResources(),"bn");
            Locale locale = new Locale("bn");
            Configuration config = getBaseContext().getResources().getConfiguration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
        startActivity(new Intent(LanguageSelect.this, IntroSlider.class));
    }

    private void updateView(String language) {
        Context context = LocaleHelper.setLocale(this, language);
        Resources resources = context.getResources();
        btnOK.setText(resources.getString(R.string.alright));
        textLang.setText(resources.getString(R.string.whatlang));
    }
}
