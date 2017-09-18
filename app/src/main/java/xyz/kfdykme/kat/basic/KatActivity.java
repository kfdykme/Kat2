package xyz.kfdykme.kat.basic;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

/**
 * Project Name: Kat
 * Class Description:
 * Created by kf on 2017/9/3 20:03.
 * Last Edit on 2017/9/3 20:03
 * 修改备注：
 */

public class KatActivity extends AppCompatActivity {

    Toolbar mToolbar;

    public void showToast(String toast){
        Toast.makeText(this,toast,Toast.LENGTH_SHORT).show();
    }

    public void showToastLong(String toast){
        Toast.makeText(this,toast,Toast.LENGTH_LONG).show();
    }

    public KatActivity getActivity(){
        return this;
    }

    public void initToolbar(Toolbar toolbar){
        mToolbar = toolbar;
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(android.R.drawable.btn_dropdown);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
