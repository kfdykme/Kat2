package xyz.kfdykme.kat.basic;

import android.os.Bundle;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/14 22:18.
 * Last Edit on 2017/9/14 22:18
 * 修改备注：
 */

public class KatActivityWithEventBus extends KatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }
}
