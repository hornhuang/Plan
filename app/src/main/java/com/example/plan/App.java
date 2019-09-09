package com.example.plan;

import android.app.Application;

import cn.bmob.v3.Bmob;

public class App extends Application {

    private final String AppId = "d369c2a5c17ae102877252dbc047b1f0";

    @Override
    public void onCreate() {
        super.onCreate();

        Bmob.initialize(this, AppId);
    }
}
