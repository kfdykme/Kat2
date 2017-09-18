package xyz.kfdykme.kat.util;

import android.content.Context;
import android.content.SharedPreferences;

import xyz.kfdykme.kat.constant.KatConstant;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/7 22:49.
 * Last Edit on 2017/9/7 22:49
 * 修改备注：
 */

public class SharedUtil {


    public static void saveString(Context context, String key, String value){
        SharedPreferences sp = context.getSharedPreferences(KatConstant.PREFERENCE_NAME,context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putString(key,value);
        et.commit();
    }

    public static String getString(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(KatConstant.PREFERENCE_NAME,context.MODE_PRIVATE);
        return sp.getString(key,null);
    }
}
