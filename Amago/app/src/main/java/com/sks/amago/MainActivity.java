package com.sks.amago;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textName;
    AlertDialog dialogHarvest;

    SharedPreferences sharedPrefs;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Paper.init(this);
//        String language = Paper.book().read("language");
//        if(language == null)
//            Paper.book().write("language", "bn");
//        updateView((String)Paper.book().read("language"));
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textName = findViewById(R.id.textView_username);
        sharedPrefs = getSharedPreferences("com.sks.amago.userprefs", MODE_PRIVATE);
        username = sharedPrefs.getString("amagoFullname", "Rahim Khondokor");
        textName.setText(username);
    }

    String prod, amnt;
    public void GotoHarvest(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] products = getResources().getStringArray(R.array.Produce);
        builder.setTitle(getResources().getString(R.string.pickharvest)).setItems(products, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                prod = products[i].toString();
//                Toast.makeText(MainActivity.this, prod, Toast.LENGTH_SHORT).show();
            }
        });
        dialogHarvest = builder.create();

        AlertDialog.Builder dialogHarvestnum = new AlertDialog.Builder(this);
        dialogHarvestnum.setTitle(R.string.muchharvest);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setRawInputType(Configuration.KEYBOARD_12KEY);
        dialogHarvestnum.setView(input);
        dialogHarvestnum.setPositiveButton((R.string.alright), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                amnt = input.getText().toString();
                Toast.makeText(MainActivity.this, amnt + "kg of " + prod, Toast.LENGTH_SHORT).show();
            }
        });
        dialogHarvestnum.setNegativeButton((R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Toast.makeText(MainActivity.this, R.string.cancel, Toast.LENGTH_SHORT).show();
            }
        });

        dialogHarvestnum.show();
        dialogHarvest.show();
    }
}
