package com.fei.zzqghelper;

import android.util.DisplayMetrics;

/**
 * @author fei
 * @date on 2018/12/4 0004
 * @describe TODO :
 **/
public class ScreenUtil {

    public static int getScreenWidth(){
        DisplayMetrics dm = MyApplication.getApp().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHight(){
        DisplayMetrics dm = MyApplication.getApp().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

}
