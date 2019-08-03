package com.sks.amago;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textName;

    SharedPreferences sharedPrefs;
    public static String username, userID;

    public static ArrayList<AmagoItem> amagoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textName = findViewById(R.id.textView_username);
        sharedPrefs = getSharedPreferences("com.sks.amago.userprefs", MODE_PRIVATE);
        username = sharedPrefs.getString("username", "Rahim Khondokor");
        userID = sharedPrefs.getString("userid", "99");
        textName.setText(username);

        LoadItems();
        CreateCards();
//        SaveItems();
    }


    private String prod, amnt, price;
    EditText input, input2;
    public void GotoHarvest(View view) {
        AlertDialog.Builder dialogHarvestItem;
        final AlertDialog.Builder dialogHarvestnum;
        final AlertDialog.Builder dialogHarvestprice;


        dialogHarvestprice = new AlertDialog.Builder(this);
        dialogHarvestprice.setTitle(R.string.costharvest);
        input2 = new EditText(this);
        input2.setInputType(InputType.TYPE_CLASS_NUMBER);
        input2.setRawInputType(Configuration.KEYBOARD_12KEY);
        dialogHarvestprice.setView(input2);
        dialogHarvestprice.setPositiveButton((R.string.alright), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                price = input2.getText().toString();
                Toast.makeText(MainActivity.this, "At a price of " + price + "tk", Toast.LENGTH_LONG).show();
                InsertItem(prod, Float.parseFloat(amnt), Float.parseFloat(price));
            }
        });
        dialogHarvestprice.setNegativeButton((R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Toast.makeText(MainActivity.this, R.string.cancel, Toast.LENGTH_SHORT).show();
            }
        });
        ////////////////////////////////////////////////////////
        dialogHarvestnum = new AlertDialog.Builder(this);
        dialogHarvestnum.setTitle(R.string.muchharvest);
        input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setRawInputType(Configuration.KEYBOARD_12KEY);
        dialogHarvestnum.setView(input);
        dialogHarvestnum.setPositiveButton((R.string.alright), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                amnt = input.getText().toString();
                Toast.makeText(MainActivity.this, amnt + "kg of " + prod, Toast.LENGTH_SHORT).show();
                dialogHarvestprice.show();
            }
        });
        dialogHarvestnum.setNegativeButton((R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Toast.makeText(MainActivity.this, R.string.cancel, Toast.LENGTH_SHORT).show();
            }
        });
        ////////////////////////////////////////////////////////
        dialogHarvestItem = new AlertDialog.Builder(this);
        final String[] products = getResources().getStringArray(R.array.Produce);
        dialogHarvestItem.setTitle(getResources().getString(R.string.pickharvest)).setItems(products, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                prod = products[i].toString();
                dialogHarvestnum.show();
//                Toast.makeText(MainActivity.this, prod, Toast.LENGTH_SHORT).show();
            }
        });

        dialogHarvestItem.show();
    }


    public void InsertItem(String n, float w, float p) {
        amagoItems.add(new AmagoItem(n,w,p));
//        SaveItems();
    }
    public void RemoveItem(View view) {
//        int position = Integer.parseInt(editTextRemove.getText().toString());
        amagoItems.remove(0);
//        adapter.notifyItemRemoved(position);
    }
    public void SaveItems(View view) {
        SharedPreferences sharedPrefs = getSharedPreferences("com.sks.amago.userprefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(amagoItems);
        Log.i("MainActivity","\nJSON of Transaction Objects:\n" +json+ "\n");
        editor.putString("kotoitems", json);
        editor.apply();
        MakeToast("List was Saved in Local Storage. Chill");

        finish();
        startActivity(getIntent());
    }
    public void LoadItems(){
        SharedPreferences sharedPrefs = getSharedPreferences("com.sks.amago.userprefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("kotoitems", null);
        Type type =new TypeToken<ArrayList<AmagoItem>>(){}.getType();
        amagoItems = gson.fromJson(json, type);
        if(amagoItems == null){
            amagoItems = new ArrayList<>();
        }
    }

    public void MakeToast(String ts){
        Toast.makeText(this, ts, Toast.LENGTH_SHORT).show();
    }

    private Context mContext;
    LinearLayout mLinearLayout;
    protected void CreateCards() {
        mContext = getApplicationContext();
        mLinearLayout = (LinearLayout) findViewById(R.id.mainLinearLayout);

        for (int i=0; i<amagoItems.size(); i++) {
            Space space = new Space(mContext);
            LinearLayout.LayoutParams paramsS = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    40
            );
            space.setLayoutParams(paramsS);
            mLinearLayout.addView(space);

            CardView card = new CardView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            card.setLayoutParams(params);
            card.setRadius(16);
            card.setContentPadding(15, 15, 15, 15);
            card.setCardBackgroundColor(Color.WHITE);
            card.setMaxCardElevation(15);
            card.setCardElevation(9);

            LinearLayout cardlinear = new LinearLayout(mContext);
            cardlinear.setLayoutParams(params);
            cardlinear.setOrientation(LinearLayout.VERTICAL);
            cardlinear.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.START);

            TextView tv = new TextView(mContext);
            tv.setLayoutParams(params);
            String s = amagoItems.get(i).getItemAmount()+ "kg of "+amagoItems.get(i).getItemName() + " @ "+amagoItems.get(i).getItemPrice()+"tk";
            tv.setText(s);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv.setTextColor(Color.GRAY);
            cardlinear.addView(tv);

            Space cardspace = new Space(mContext);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    15
            );
            cardspace.setLayoutParams(params2);
            cardlinear.addView(cardspace);

            Button btn = new Button(mContext);
            btn.setText(getString(R.string.sell));
            btn.setTextColor(Color.WHITE);
            btn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            btn.setBackground(getDrawable(R.drawable.rounded_button));
//            btn.setBackgroundColor(Color.GREEN);
            cardlinear.addView(btn);

            card.addView(cardlinear);
            mLinearLayout.addView(card);
        }
    }
}
