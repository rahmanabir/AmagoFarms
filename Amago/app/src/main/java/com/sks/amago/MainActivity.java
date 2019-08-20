package com.sks.amago;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.sks.amago.Retrofit.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView textName;

    SharedPreferences sharedPrefs;
    public static String username, userID;

    public static ArrayList<AmagoItem> amagoItems;

    private Context mContext;
    LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textName = findViewById(R.id.textView_username);
        sharedPrefs = getSharedPreferences("com.sks.amago.userprefs", MODE_PRIVATE);
        username = sharedPrefs.getString("username", "Rahim Khondokor");
        userID = sharedPrefs.getString("userid", "99");
        textName.setText(username);

        if(amagoItems == null) amagoItems = new ArrayList<>();
        LoadItems();
    }

    private String prod, amnt, price;
    EditText input, input2;

    public void ClickNewHarvest(View view) {
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
//                InsertItem(prod, Float.parseFloat(amnt), Float.parseFloat(price));
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
//                dialogHarvestprice.show();
                InsertItem(0, prod, Float.parseFloat(amnt), 0);
                PostNewHarvest(prod, Integer.parseInt(amnt));

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
                prod = products[i];
                dialogHarvestnum.show();
//                Toast.makeText(MainActivity.this, prod, Toast.LENGTH_SHORT).show();
            }
        });

        dialogHarvestItem.show();
    }

    public void PostNewHarvest(String productname, int weight){
        String[] productnames = getResources().getStringArray(R.array.Produce);
        int itemtypeint = 0;
        for(int i=0; i<productnames.length; i++){
            if(productname.equals(productnames[i])) itemtypeint = i;
        }

        Call<ResponseBody> apicall2 = RetrofitClient.getRetrofitInstance()
                .getAPICalls().PostHarvest(userID, itemtypeint, weight);

        apicall2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String harvestjson = "";
                    if(response.code() == 200){
                        harvestjson = response.body() != null ? response.body().string() : null;
//                        JSONArray array = new JSONArray(harvestjson);
                        MakeToast("Harvest Posted: "+ harvestjson);
                        Log.wtf("Post Harvest",harvestjson);

                    }
                    else {
                        Log.wtf("HarvestFail",harvestjson);
                    }
                    LoadItems();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void LoadItems(){
        final Gson gson = new Gson();
        final Type type =new TypeToken<ArrayList<AmagoItem>>(){}.getType();

        Call<ResponseBody> apicall2 = RetrofitClient.getRetrofitInstance()
                .getAPICalls().GetHarvest(userID);

        apicall2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String harvestjson = "";
                    if(response.code() == 200){
                        harvestjson = response.body().string();
                        JSONArray array = new JSONArray(harvestjson);
                        for(int i=0; i<array.length();i++) {
                            JSONObject jobj = array.getJSONObject(i);
                            InsertItem(jobj.getInt("id")
                                    ,getResources().getStringArray(R.array.Produce)[jobj.getInt("itemType")],
                                    jobj.getInt("amount"), 0);
                        }

                        Log.wtf("Harvest-List",harvestjson);
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.putString("amagoitems", harvestjson);
                        editor.apply();
                        MakeToast("List was Saved in Local Storage. Chill");

                    }
                    else {
                        Log.wtf("HarvestFail",harvestjson);
                    }
                    CreateCards();
                }
                catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        // reloads activity
//        Intent intent = getIntent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        finish();
//        startActivity(intent);
    }

    public void ReloadPage(View view) {
//        SharedPreferences sharedPrefs = getSharedPreferences("com.sks.amago.userprefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(amagoItems);
//        Log.wtf("MainActivity","\nJSON of Transaction Objects:\n" +json+ "\n");
//        editor.putString("amagoitems", json);
//        editor.apply();
        MakeToast("List Reloaded");

        // reloads activity
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }
    public void InsertItem(int id, String n, float w, float p) {
        for(int i=0; i<amagoItems.size(); i++) if( amagoItems.get(i).getUniqueID() == id) return;
        amagoItems.add(new AmagoItem(id,n,w,p));
//        SaveItems();
    }

    public void RemoveItem(View view) {
//        int position = Integer.parseInt(editTextRemove.getText().toString());
        amagoItems.remove(0);
//        adapter.notifyItemRemoved(position);
    }

    public void MakeToast(String ts){
        Toast.makeText(this, ts, Toast.LENGTH_SHORT).show();
    }
    protected void CreateCards() {
        mContext = getApplicationContext();
        mLinearLayout = findViewById(R.id.mainLinearLayout);
        Log.wtf("Cards","Create Function");

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
            String s = amagoItems.get(i).getItemName()+" of "+amagoItems.get(i).getItemAmount()+ "kg  "+" @ "+amagoItems.get(i).getItemPrice()+"tk";
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
            mLinearLayout.invalidate();
//            getWindow().getDecorView().findViewById(android.R.id.content).invalidate();
//            mLinearLayout.setVisibility(View.GONE);
//            mLinearLayout.setVisibility(View.VISIBLE);
            Log.wtf("Cards", "After Invalidate");
        }
    }
}
