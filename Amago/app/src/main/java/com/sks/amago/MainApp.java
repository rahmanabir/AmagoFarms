package com.sks.amago;

import android.app.Application;
import android.content.Context;

import com.sks.amago.LangHelper.LocaleHelper;

/**
 * Coded by JAKfromSpace on 07-Mar-19 for SKS.
 */
public class MainApp extends Application {

    @Override
    protected void attachBaseContext(Context base){
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }
}
