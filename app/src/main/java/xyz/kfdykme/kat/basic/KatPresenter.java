package xyz.kfdykme.kat.basic;

import android.content.Context;

import xyz.kfdykme.kat.service.KatService;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/4 10:50.
 * Last Edit on 2017/9/4 10:50
 * 修改备注：
 */

public abstract class KatPresenter<M extends KatModel,V extends KatView>  implements IPresenter<M,V>{

    private M model;

    private V view;

    private Context context;

    public KatPresenter(Context context){
        this.context = context;
    }

    @Override
    public void addView(V v) {
        this.view = v;
    }

    @Override
    public void setMode(M m) {
        this.model = m;
    }

    public M getModel() {
        return model;
    }

    public V getView() {
        return view;
    }

    @Override
    public void attach() {
        onAttach();
    }

    @Override
    public void detach() {
        if(model != null)
            model = null;
        if(view != null)
            view = null;


        onDetach();
    }

    public Context getContext() {
        return context;
    }

    protected abstract void onAttach();

    protected abstract void onDetach();
}
