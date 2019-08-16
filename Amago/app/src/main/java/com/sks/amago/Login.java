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
import com.sks.amago.Retrofit.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    TextView amagotext;

    EditText editTextPhone;
    EditText editTextPIN;

    Button buttonLogin;
    Button buttonSignin;

    String userpin, userphone, utoken, userid, username;
    Boolean firsttime;
    SharedPreferences sharedPrefs;

    String tokencheck = " ";
    int tokenresponsecode = 0;
    String tokencheckresp = " ";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPrefs = getSharedPreferences("com.sks.amago.userprefs", MODE_PRIVATE);
        firsttime = sharedPrefs.getBoolean("firsttime", true);

        if(firsttime){
            Intent intent = new Intent(Login.this, LanguageSelect.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        userphone = sharedPrefs.getString("amagoPhone", "01711499499");
        userpin = sharedPrefs.getString("amagoPIN", "0499");
        utoken = sharedPrefs.getString("utoken", "tokennotsetpleaselogin");
        userid = sharedPrefs.getString("utoken", "tokennotsetpleaselogin");
        username = sharedPrefs.getString("utoken", "tokennotsetpleaselogin");

        CheckCurrentToken(utoken);

        amagotext = findViewById(R.id.textView);
        editTextPhone = findViewById(R.id.editText_phone);
        editTextPIN = findViewById(R.id.editText_PIN);
        buttonLogin = findViewById(R.id.button_login);
        buttonSignin = findViewById(R.id.button_signup);
    }
    public void GotoMain(View view) {

//        userphone = sharedPrefs.getString("amagoPhone", "01711499499");
//        userpin = sharedPrefs.getString("amagoPIN", "0499");

        Log.i("CheatLoginCreds",userphone + " " + userpin);
        Log.i("CheatLoginCreds",editTextPhone.getText().toString() + " " + editTextPIN.getText().toString());


        String getphone = editTextPhone.getText().toString();
        String getpin = editTextPIN.getText().toString();

        Call<ResponseBody> apicall = RetrofitClient.getRetrofitInstance()
                .getAPICalls().Login(getphone, getpin);
        apicall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    tokencheck = response.body().string();
                    tokenresponsecode = response.code();
                    Toast.makeText(Login.this, tokencheck + " - " + tokenresponsecode, Toast.LENGTH_LONG).show();
                    Log.i("LOGIN GET TOKEN",tokencheck + " - " + tokenresponsecode);

                    JSONObject tokenrespjson = new JSONObject(tokencheck);
                    String tokencheck = tokenrespjson.getString("token");
                    Log.i("JSONOBJECT TOKEN","" + tokencheck);

                    SharedPreferences sharedPrefs = getSharedPreferences("com.sks.amago.userprefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString("utoken", tokencheck);
                    editor.apply();

                    Toast.makeText(Login.this, "Token Saved to Local: "+tokencheck, Toast.LENGTH_LONG).show();
                    Log.i("LOGIN SAVE TOKEN","Token Saved to Local: "+tokencheck);

                    CheckCurrentToken(tokencheck);

                }
                catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Login.this, t.getMessage() +" "+ getString(R.string.crednotmatch), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void CheckCurrentToken(String utoken){
        tokencheck = utoken;
        Call<ResponseBody> apicall2 = RetrofitClient.getRetrofitInstance()
                .getAPICalls().CheckToken(tokencheck);
        apicall2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    tokenresponsecode = response.code();
                    if(tokenresponsecode == 200){
                        tokencheckresp = response.body().string();

                        JSONObject tokenlogindeets = new JSONObject(tokencheckresp);
                        tokenlogindeets = tokenlogindeets.getJSONObject("authData");
                        userid = tokenlogindeets.getString("id");
                        username = tokenlogindeets.getString("username");

                        Toast.makeText(Login.this, "Ok: "+tokencheckresp +" - "+tokenresponsecode
                                + "/n"+userid+" - "+username, Toast.LENGTH_LONG).show();
                        Log.i("RESPONSE TOKEN OK",""+tokencheckresp+" - "+tokenresponsecode+ "\n"+userid+" - "+username);

                        SharedPreferences sharedPrefs = getSharedPreferences("com.sks.amago.userprefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.putString("userid", userid);
                        editor.putString("username", username);
                        editor.apply();


                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    }
                    else {
                        Toast.makeText(Login.this, "Fail: "+tokenresponsecode+" "+getString(R.string.crednotmatch), Toast.LENGTH_LONG).show();
                        Log.i("RESPONSE TOKEN FAIL",tokencheck+" "+tokenresponsecode);
                    }
                }
                catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void GotoRegister(View view) {
        startActivity(new Intent(Login.this, Register.class));
    }

}
