package com.sks.amago;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sks.amago.Helper.LocaleHelper;

import io.paperdb.Paper;

public class Login extends AppCompatActivity {

    TextView amagotext;

    EditText editTextPhone;
    EditText editTextPIN;

    Button buttonLogin;
    Button buttonSignin;

    String userpin, userphone;
    SharedPreferences sharedPrefs;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPrefs = getSharedPreferences("com.sks.amago.userprefs", MODE_PRIVATE);
        userphone = sharedPrefs.getString("amagoPhone", "01711499499");
        userpin = sharedPrefs.getString("amagoPIN", "0499");

        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean("firsttime?", true);
        editor.apply();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        amagotext = findViewById(R.id.textView);
        editTextPhone = findViewById(R.id.editText_phone);
        editTextPIN = findViewById(R.id.editText_PIN);
        buttonLogin = findViewById(R.id.button_login);
        buttonSignin = findViewById(R.id.button_signup);

        //Language switching
//        Paper.init(this);
//        String language = Paper.book().read("language");
//        if(language == null)
//            Paper.book().write("language", "bn");
//        updateView((String)Paper.book().read("language"));
    }

    private void updateView(String language) {
        Context context = LocaleHelper.setLocale(this, language);
        Resources resources = context.getResources();
        editTextPIN.setHint(resources.getString(R.string.pin_number));
        editTextPhone.setHint(resources.getString(R.string.mobile_phone_num));
        amagotext.setText(resources.getString(R.string.app_name));
        buttonLogin.setText(resources.getString(R.string.title_activity_login));
        buttonSignin.setText(resources.getString(R.string.signup));
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

    public void GotoMain(View view) {
        userphone = sharedPrefs.getString("amagoPhone", "01711499499");
        userpin = sharedPrefs.getString("amagoPIN", "0499");
        Log.i("LoginCreds",userphone + " " + userpin);
        Log.i("LoginCreds",editTextPhone.getText().toString() + " " + editTextPIN.getText().toString());
        if (userphone.equals(editTextPhone.getText().toString()) && userpin.equals(editTextPIN.getText().toString()))
            startActivity(new Intent(Login.this, MainActivity.class));
        else MakeToast(getString(R.string.crednotmatch));
    }

    public void GotoRegister(View view) {
        startActivity(new Intent(Login.this, Register.class));
    }

    void MakeToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}
