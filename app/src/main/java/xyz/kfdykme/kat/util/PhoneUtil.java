package xyz.kfdykme.kat.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/16 21:02.
 * Last Edit on 2017/9/16 21:02
 * 修改备注：
 */

public class PhoneUtil {

    public static final String CONTACT = "contact";
    public static final String NUMBER = "number";
    public static final String ERROR_CANT_FOUND = "找不到联系人";


    public static void callByNumber(Context context, String number){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Log.i("ServicePresneter","can't call phone because permission");
            return;
        }
        context.startActivity(intent);
    }

    public static List<Map<String,String>> getContacts(Context context){
        String[] cols = {ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                cols, null, null, null);
        List<Map<String,String>> maps = new ArrayList<>();
        Map<String,String> map  =new HashMap<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            // 取得联系人名字
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            int numberFieldColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String name = cursor.getString(nameFieldColumnIndex);
            String number = cursor.getString(numberFieldColumnIndex);
            map.put(CONTACT,name);
            map.put(NUMBER,number);
            maps.add(map);
            Toast.makeText(context, name + " " + number, Toast.LENGTH_SHORT).show();
        }
        return maps;
    }

    public static void callByName(Context context,String name){

        List<String> numbers = new ArrayList<>();
        for(Map<String,String> map:getContacts(context)){
            if(map.get(CONTACT).equals(name)){
                numbers.add(map.get(NUMBER));
            }
        }
        if(numbers.size() == 1){
            callByNumber(context,numbers.get(0));
        }else if (numbers.size() == 0){
            Toast.makeText(context,ERROR_CANT_FOUND + " "+name ,Toast.LENGTH_SHORT).show();
        }else if(numbers.size() > 1){
            Toast.makeText(context,"+++++++",Toast.LENGTH_SHORT).show();
        }
    }
}
