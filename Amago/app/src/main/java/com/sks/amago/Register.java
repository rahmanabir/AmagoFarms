package com.sks.amago;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Register extends AppCompatActivity {

    EditText editTextFullnamereg;
    EditText editTextAddressreg;
    EditText editTextDateofBirthreg;
    EditText editTextPhoneNumreg;
    EditText editTextPINreg;
    Button buttonSignup;
    final Calendar myCalendar = Calendar.getInstance();
    Spinner spinnerGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextFullnamereg = findViewById(R.id.editText_fullnamereg);
        editTextAddressreg = findViewById(R.id.editText_addressreg);
        editTextDateofBirthreg = findViewById(R.id.editText_dobreg);
        editTextPhoneNumreg = findViewById(R.id.editText_phonereg);
        editTextPINreg = findViewById(R.id.editText_PINreg);
        spinnerGender = findViewById(R.id.spinner_genderreg);
        buttonSignup = findViewById(R.id.button_signupreg);

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(Register.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Genders));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(spinnerAdapter);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        editTextDateofBirthreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Register.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }



    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        editTextDateofBirthreg.setText(sdf.format(myCalendar.getTime()));
    }


    public void GotoRegisterDone(View view) {
        if(editTextPhoneNumreg.getText().length() == 11 && editTextPINreg.getText().length() >= 4){
            SharedPreferences sharedPrefs = getSharedPreferences("com.sks.amago.userprefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("amagoPIN", editTextPINreg.getText().toString());
            editor.putString("amagoPhone", editTextPhoneNumreg.getText().toString());
            editor.putString("amagoFullname", editTextFullnamereg.getText().toString());
            editor.putString("amagoAddress", editTextAddressreg.getText().toString());
            editor.putString("amagoDOB", editTextDateofBirthreg.getText().toString());
            editor.apply();
            Toast.makeText(this, "Saved!\n"+editTextPhoneNumreg.getText().toString()+" "+editTextPINreg.getText().toString(), Toast.LENGTH_LONG).show();
            this.finish();
        }
    }
}
