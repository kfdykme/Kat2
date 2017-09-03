package xyz.kfdykme.kat.basic;

/**
 * Project Name: Kat
 * Class Description:
 * Created by kf on 2017/9/3 20:14.
 * Last Edit on 2017/9/3 20:14
 * 修改备注：
 */

public abstract class KatView<E extends EventListener> implements IView<E> {

    private KatActivity mActivity;

    private E mEventListener;

    public KatView(KatActivity activity) {
        this.mActivity = activity;
    }

    @Override
    public void setEventListener(E eventListener) {
        this.mEventListener = eventListener;
    }

    public KatActivity getActivity() {
        return mActivity;
    }


}
