package com.sks.amago;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class DataFetch extends AsyncTask<String, String, String> {

    String baseURL = "http://192.168.0.102:8000/";

    String jsonData = "";

    @Override
    protected String doInBackground(String ... Strings) {

        try {
            URL url = new URL(baseURL + "transactions/");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                jsonData = jsonData + line;
            }
            try {
                JSONArray JA = new JSONArray(jsonData);
                for (int i = 0; i < JA.length(); i++) {
                    JSONObject JO = (JSONObject) JA.get(i);

                    //DEBUG
//                    details =  JO.get("transId").toString();
//                    IsExpense = JO.get("isExpence").toString();
//                    amnt = JO.get("amount").toString();
//                    dateTaken =  JO.get("date").toString();
//                    amnt = JO.get("amount").toString();
//                    Log.d("Async",details+amnt+dateTaken);

//                    KotoItem item = new KotoItem(details,Float.parseFloat(amnt));
//                    item.setDateTaken(dateTaken);
//                    item.isExpense(Boolean.parseBoolean(IsExpense));
//                    MainActivity.kotoItems.add(item); //DEBUG

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(String aVoid){
        super.onPostExecute(aVoid);

//        MainActivity.adapter.notifyDataSetChanged();
    }

}
