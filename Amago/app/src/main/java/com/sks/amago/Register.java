package com.sks.amago;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sks.amago.Retrofit.RetrofitClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Register extends AppCompatActivity {

    EditText editTextFullnamereg;
//    EditText editTextAddressreg;
//    EditText editTextDateofBirthreg;
    EditText editTextPhoneNumreg;
    EditText editTextPINreg;
    Button buttonSignup;
//    final Calendar myCalendar = Calendar.getInstance();
//    Spinner spinnerGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextFullnamereg = findViewById(R.id.editText_fullnamereg);
        editTextPhoneNumreg = findViewById(R.id.editText_phonereg);
        editTextPINreg = findViewById(R.id.editText_PINreg);
        buttonSignup = findViewById(R.id.button_signupreg);

    }

    public void GotoRegisterDone(View view) {
        String fullname = editTextFullnamereg.getText().toString().trim(),
                phonenum = editTextPhoneNumreg.getText().toString().trim(),
                pinnumber = editTextPINreg.getText().toString().trim();

        if (!fullname.isEmpty()) {
            if (phonenum.length() == 11) {
                if (pinnumber.length() >= 4) {
                    SharedPreferences sharedPrefs = getSharedPreferences("com.sks.amago.userprefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString("amagoPIN", pinnumber);
                    editor.putString("amagoPhone", phonenum);
                    editor.putString("amagoFullname", fullname);
                    editor.apply();
                    Toast.makeText(this, "Saved!\n" + phonenum + " "
                            + pinnumber, Toast.LENGTH_LONG).show();

                    Call<ResponseBody> apicall = RetrofitClient.getRetrofitInstance()
                            .getAPICalls().Register(fullname, pinnumber, phonenum);
                    apicall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String s = response.body().string();
                                Toast.makeText(Register.this, s, Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                    this.finish();
                } else editTextPINreg.setError("! " + getString(R.string.needpin) + " !");
            } else editTextPhoneNumreg.setError("! " + getString(R.string.needphnum) + " !");
        } else editTextFullnamereg.setError("! " + getString(R.string.needname) + " !");
    }
}
