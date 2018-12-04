package com.fei.zzqghelper;

import android.app.Application;

/**
 * @author fei
 * @date on 2018/12/4 0004
 * @describe TODO :
 **/
public class MyApplication extends Application {

    private static MyApplication app;

    @Override
    public void onCreate() {
        app = this;
        super.onCreate();
    }


    public static MyApplication getApp(){
        return app;
    }
}
