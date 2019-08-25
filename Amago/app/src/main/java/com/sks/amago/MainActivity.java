package com.sks.amago;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
    ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mContext = this.getApplicationContext();

        textName = findViewById(R.id.textView_username);
        sharedPrefs = getSharedPreferences("com.sks.amago.userprefs", MODE_PRIVATE);
        username = sharedPrefs.getString("username", "Rahim Khondokor");
        userID = sharedPrefs.getString("userid", "99");
        textName.setText(username);

        if(amagoItems == null) amagoItems = new ArrayList<>();
        LoadItems();
    }

    private String prod, amnt;
    EditText input;
    public void ClickNewHarvest(View view) {
        AlertDialog.Builder dialogHarvestItem;
        final AlertDialog.Builder dialogHarvestnum;
//        final AlertDialog.Builder dialogHarvestprice;


        ////////////////////////////////////////////////////////// PRICE TK  xx ////
//        dialogHarvestprice = new AlertDialog.Builder(this);
//        dialogHarvestprice.setTitle(R.string.costharvest);
//        input2 = new EditText(this);
//        input2.setInputType(InputType.TYPE_CLASS_NUMBER);
//        input2.setRawInputType(Configuration.KEYBOARD_12KEY);
//        dialogHarvestprice.setView(input2);
//        dialogHarvestprice.setPositiveButton((R.string.alright), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                price = input2.getText().toString();
//                Toast.makeText(MainActivity.this, "At a price of " + price + "tk", Toast.LENGTH_LONG).show();
////                InsertItem(prod, Float.parseFloat(amnt), Float.parseFloat(price));
//            }
//        });
//        dialogHarvestprice.setNegativeButton((R.string.cancel), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                Toast.makeText(MainActivity.this, R.string.cancel, Toast.LENGTH_SHORT).show();
//            }
//        });
        //////////////////////////////////////////////////////// AMOUNT KG 2
        dialogHarvestnum = new AlertDialog.Builder(this);
        dialogHarvestnum.setTitle(R.string.muchharvest);
        input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setRawInputType(Configuration.KEYBOARD_12KEY);
        dialogHarvestnum.setPositiveButton((R.string.alright), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                amnt = input.getText().toString();
                //Toast.makeText(MainActivity.this, amnt + "kg of " + prod, Toast.LENGTH_SHORT).show();
                //dialogHarvestprice.show();
                if(!amnt.isEmpty()) PostNewHarvest(Integer.parseInt(prod), Integer.parseInt(amnt));
                else MakeToast(getResources().getString(R.string.cantbeempty));

            }
        });
        dialogHarvestnum.setView(input);
        dialogHarvestnum.setNegativeButton((R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Toast.makeText(MainActivity.this, R.string.cancel, Toast.LENGTH_SHORT).show();
            }
        });
        ///////////////////////////////////////////////////////// ITEM NAME 1
        dialogHarvestItem = new AlertDialog.Builder(this);
        final String[] products = getResources().getStringArray(R.array.Produce);
        dialogHarvestItem.setTitle(getResources().getString(R.string.pickharvest)).setItems(products, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                prod = i + "";
                dialogHarvestnum.show();
//                Toast.makeText(MainActivity.this, prod, Toast.LENGTH_SHORT).show();
            }
        });

        dialogHarvestItem.show();
    }

    private int sellindex, price;
    EditText pinput;
    String[] realsellarray;
    public void ClickSell(View view) {
        AlertDialog.Builder dialogSellItems;
        final AlertDialog.Builder dialogSellprice;
        ////////////////////////////////////////////////////////// PRICE TK 2 ////
        dialogSellprice = new AlertDialog.Builder(this);
        dialogSellprice.setTitle(R.string.costharvest);
        pinput = new EditText(this);
        pinput.setInputType(InputType.TYPE_CLASS_NUMBER);
        pinput.setRawInputType(Configuration.KEYBOARD_12KEY);
        dialogSellprice.setView(pinput);
        dialogSellprice.setPositiveButton((R.string.alright), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String pricestr = pinput.getText().toString();
                if(!pricestr.isEmpty()) {
                    price = Integer.parseInt(pinput.getText().toString());
                    PostSell(sellindex, 2, price);
                }
                else MakeToast(getResources().getString(R.string.cantbeempty));
//                Toast.makeText(MainActivity.this, "At a price of " + price + "tk", Toast.LENGTH_LONG).show();
//                InsertItem(prod, Float.parseFloat(amnt), Float.parseFloat(price));
            }
        }).setNegativeButton((R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Toast.makeText(MainActivity.this, R.string.cancel, Toast.LENGTH_SHORT).show();
            }
        });

        ///////////////////////////////////////////////////////// ITEM NAME 1

        dialogSellItems = new AlertDialog.Builder(this);
        int sellarraysize = amagoItems.size();
        String[] sellableItems = new String[sellarraysize];
        int arraypointer = 0;
        for(int j=0; j<sellarraysize; j++){
            if(amagoItems.get(j).getItemStatus()==1) {
                sellableItems[arraypointer] = getResources().getStringArray(R.array.Produce)[amagoItems.get(j).getItemType()] + " #" + amagoItems.get(j).getUniqueID();
                arraypointer++;
            }
        }
        realsellarray = new String[arraypointer];
        for(int j=0; j<arraypointer; j++){
            realsellarray[j]=sellableItems[j];
        }
        dialogSellItems.setTitle(getResources().getString(R.string.whatsell)).setItems(realsellarray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sellindex = Integer.parseInt(realsellarray[i].split(" #")[1]);
                dialogSellprice.show();
//                Toast.makeText(MainActivity.this, prod, Toast.LENGTH_SHORT).show();
            }
        });
        dialogSellItems.show();
    }

    //////////////////////////////////////////// API
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
                        amagoItems = new ArrayList<>();
                        for(int i=0; i<array.length();i++) {
                            JSONObject jobj = array.getJSONObject(i);
                            InsertItem(jobj.getInt("id")
                                    ,jobj.getInt("itemType"),
                                    jobj.getInt("amount"),
                                    jobj.optInt("price"),
                                    jobj.optString("sellerName"),
                                    jobj.getInt("status"));
                        }

                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.putString("amagoitems", harvestjson);
                        editor.apply();
                        MakeToast(getResources().getString(R.string.harvestupdated));
                        String s = "\n......";
                        for(int j=0; j<amagoItems.size(); j++){
                            s += amagoItems.get(j).showString();
                        }
                    }
                    else {
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
    public void PostNewHarvest(int itemtype, int weight){
        Call<ResponseBody> apicall2 = RetrofitClient.getRetrofitInstance().getAPICalls()
                .PostHarvest(userID, itemtype, weight);

        apicall2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String harvestjson = "";
                    if(response.code() == 200){
                        harvestjson = response.body() != null ? response.body().string() : null;
                        //JSONArray array = new JSONArray(harvestjson);
                        //MakeToast("Harvest Posted: "+ harvestjson);
                        ReloadPage(null);
                    }
                    else {
                    }
                    mScrollView = findViewById(R.id.mainscrollview);
                    //mScrollView.scrollTo(0, mScrollView.getBottom());
                    mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
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
    public void PostSell(int id, int st, int pr){
        Call<ResponseBody> apicall2 = RetrofitClient.getRetrofitInstance().getAPICalls()
                .SellHarvest(id, st, pr);

        apicall2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String harvestjson = "";
                    if(response.code() == 200){
                        harvestjson = response.body() != null ? response.body().string() : null;
                        //JSONArray array = new JSONArray(harvestjson);
                        //MakeToast("Sell Posted: "+ harvestjson);
                        ReloadPage(null);
                    }
                    else {
                        MakeToast("SellFail: "+harvestjson);
                    }
//                    mScrollView = findViewById(R.id.mainscrollview);
//                    mScrollView.scrollTo(0, mScrollView.getBottom());
//                    mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
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

    //////////////////////////////////////////// LIST
    public void InsertItem(int id, int n, int w, int p, String sn, int s) {
        for(int i=0; i<amagoItems.size(); i++) if(amagoItems.get(i).getUniqueID() == id) return;
        amagoItems.add(new AmagoItem(id,n,w,p,sn,s));
    }
    public void ReloadPage(View view) {
//        SharedPreferences sharedPrefs = getSharedPreferences("com.sks.amago.userprefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(amagoItems);
//        Log.wtf("MainActivity","\nJSON of Transaction Objects:\n" +json+ "\n");
//        editor.putString("amagoitems", json);
//        editor.apply();
        //MakeToast("List Reloaded");

        // reloads activity
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }
    public void RemoveItem(View view) {
//        int position = Integer.parseInt(editTextRemove.getText().toString());
        amagoItems.remove(0);
//        adapter.notifyItemRemoved(position);
    }

    //////////////////////////////////////////// PAGE
    public void MakeToast(String ts){
        Toast.makeText(this, ts, Toast.LENGTH_LONG).show();
    }

    int c;
    protected void CreateCards() {
        mContext = getApplicationContext();
        mLinearLayout = findViewById(R.id.mainLinearLayout);
        for (c=0; c<amagoItems.size(); c++) {
            Space space = new Space(mContext);
            LinearLayout.LayoutParams paramsS = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    40
            );
            space.setLayoutParams(paramsS);
            mLinearLayout.addView(space);

            /// CARD
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
            card.setCardElevation(15);

            /// LINEAR
            LinearLayout cardlinear = new LinearLayout(mContext);
            cardlinear.setLayoutParams(params);
            cardlinear.setOrientation(LinearLayout.VERTICAL);
            cardlinear.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.START);

            /// TEXT
            TextView tvi = new TextView(mContext);
            tvi.setLayoutParams(params);
            tvi.setText("#"+amagoItems.get(c).getUniqueID()+"-"+c); //# item unique id - item card index
            tvi.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tvi.setTextColor(Color.LTGRAY);
            cardlinear.addView(tvi);

            TextView tv = new TextView(mContext);
            tv.setLayoutParams(params);
            String s = getResources().getStringArray(R.array.Produce)[amagoItems.get(c).getItemType()] + "  " + amagoItems.get(c).getItemAmount() + "kg";
            tv.setText(s);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv.setTextColor(Color.GRAY);
            cardlinear.addView(tv);

            /// STATUS IF-ELSE
            int itemstat = amagoItems.get(c).getItemStatus();
            if (itemstat > 1) {
                TextView tvp = new TextView(mContext);                          // 1 harvest
                tvp.setLayoutParams(params);                                    // 2 sellreq
                tvp.setText("@ " + amagoItems.get(c).getItemPrice() + "টাকা");    // 3 sending
                tvp.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);          // 4 delivok
                tvp.setTextColor(Color.GREEN);
                cardlinear.addView(tvp);
                if (itemstat > 2) {
                    TextView tvs = new TextView(mContext);
                    tvs.setLayoutParams(params);
                    String delivstatus = itemstat==4?getResources().getString(R.string.deliveredto):getResources().getString(R.string.delivering);
                    tvs.setText(delivstatus+" " + amagoItems.get(c).getSellerName());
                    tvs.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    tvs.setTextColor(Color.GREEN);
                    cardlinear.addView(tvs);
                }
            }
//            else {
//                /// SPACE
//                Space cardspace = new Space(mContext);
//                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.MATCH_PARENT,
//                        10);
//                cardspace.setLayoutParams(params2);
//                cardlinear.addView(cardspace);
//                /// BUTTON - SELL
//                Button btn = new Button(mContext);
//                btn.setText(getString(R.string.sell));
//                btn.setTextColor(Color.WHITE);
//                btn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
//                btn.setBackground(getDrawable(R.drawable.rounded_button));
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        MakeToast("Sell "+c);
//                        final AlertDialog.Builder dialogHarvestprice;
//                        dialogHarvestprice = new AlertDialog.Builder(mContext);
//                        dialogHarvestprice.setTitle(R.string.costharvest);
//                        final EditText inputb = new EditText(mContext);
//                        inputb.setInputType(InputType.TYPE_CLASS_NUMBER);
//                        inputb.setRawInputType(Configuration.KEYBOARD_12KEY);
//                        dialogHarvestprice.setView(inputb);
//                        dialogHarvestprice.setPositiveButton((R.string.alright), new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int whichButton) {
//                                MakeToast("Sellinner "+c);
//                                price = inputb.getText().toString();
//                                Toast.makeText(MainActivity.this, "At a price of " + price + "tk", Toast.LENGTH_LONG).show();
//                                PostSell(amagoItems.get(c).getItemType(),amagoItems.get(c).getItemAmount(), Integer.parseInt(price));
//                //                InsertItem(prod, Float.parseFloat(amnt), Float.parseFloat(price));
//                            }
//                        });
//                        dialogHarvestprice.setNegativeButton((R.string.cancel), new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int whichButton) {
//                                Toast.makeText(MainActivity.this, R.string.cancel, Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                    }
//                });
//                //btn.setBackgroundColor(Color.GREEN);
//                cardlinear.addView(btn);
//            }

            card.addView(cardlinear);
            mLinearLayout.addView(card);
        }
        if(amagoItems.size()==0){
            Space space = new Space(mContext);
            LinearLayout.LayoutParams paramsS = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    40
            );
            space.setLayoutParams(paramsS);
            mLinearLayout.addView(space);

            /// CARD
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
            card.setCardElevation(15);

            /// LINEAR
            LinearLayout cardlinear = new LinearLayout(mContext);
            cardlinear.setLayoutParams(params);
            cardlinear.setOrientation(LinearLayout.VERTICAL);
            cardlinear.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.START);

            /// TEXT
            TextView tvi = new TextView(mContext);
            tvi.setLayoutParams(params);
            tvi.setText(getResources().getString(R.string.emptypleaseharvest)); //# item unique id - item card index
            tvi.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tvi.setTextColor(Color.GREEN);
            cardlinear.addView(tvi);

            card.addView(cardlinear);
            mLinearLayout.addView(card);
        }
//            getWindow().getDecorView().findViewById(android.R.id.content).invalidate();
//            mLinearLayout.setVisibility(View.GONE);
//            mLinearLayout.setVisibility(View.VISIBLE);
        mLinearLayout.invalidate();
        mScrollView = findViewById(R.id.mainscrollview);
        mScrollView.scrollTo(0, mScrollView.getBottom());
//        mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }
}
