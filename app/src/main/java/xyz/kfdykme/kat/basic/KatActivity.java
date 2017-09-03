package xyz.kfdykme.kat.basic;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Project Name: Kat
 * Class Description:
 * Created by kf on 2017/9/3 20:03.
 * Last Edit on 2017/9/3 20:03
 * 修改备注：
 */

public class KatActivity extends AppCompatActivity {

    public void showToast(String toast){
        Toast.makeText(this,toast,Toast.LENGTH_SHORT).show();
    }

    public void showToastLong(String toast){
        Toast.makeText(this,toast,Toast.LENGTH_LONG).show();
    }

    public KatActivity getActivity(){
        return this;
    }

}
