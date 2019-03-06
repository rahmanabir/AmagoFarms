package com.sks.amago;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.sks.amago.LangHelper.LocaleHelper;
import io.paperdb.Paper;

public class Login extends AppCompatActivity {

    EditText editTextPhone;
    EditText editTextPIN;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextPhone = findViewById(R.id.editText_phone);
        editTextPIN = findViewById(R.id.editText_PIN);

        //Language switching
        Paper.init(this);
        String language = Paper.book().read("language");
        if(language == null)
            Paper.book().write("language", "en");
        updateView((String)Paper.book().read("language"));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void updateView(String language) {
        Context context = LocaleHelper.setLocale(this, language);
        Resources resources = context.getResources();
        editTextPIN.setHint(resources.getString(R.string.pin_number));
        editTextPhone.setHint(resources.getString(R.string.mobile_phone_num));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lang_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_setlang_en){
            Paper.book().write("language", "en");
            updateView((String)Paper.book().read("language"));
        }
        else if(item.getItemId() == R.id.action_setlang_bn){
            Paper.book().write("language", "bn");
            updateView((String)Paper.book().read("language"));
        }

        return true;
    }
}
