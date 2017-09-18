package xyz.kfdykme.kat.basic;

import android.content.Context;

/**
 * Project Name: Kat
 * Class Description:
 * Created by kf on 2017/9/3 20:14.
 * Last Edit on 2017/9/3 20:14
 * 修改备注：
 */

public abstract class KatView<E extends EventListener> implements IView<E>{

    private Context context;

    private E mEventListener;

    public KatView(Context context) {
        this.context = context;
    }

    @Override
    public void setEventListener(E eventListener) {
        this.mEventListener = eventListener;
    }

    public E getEventListener() {
        return mEventListener;
    }

    public Context getContext() {
        return context;
    }


}
