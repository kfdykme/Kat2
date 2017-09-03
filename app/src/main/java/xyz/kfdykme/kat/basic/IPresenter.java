package xyz.kfdykme.kat.basic;

/**
 * Project Name: Kat
 * Class Description:
 * Created by kf on 2017/9/3 20:12.
 * Last Edit on 2017/9/3 20:12
 * 修改备注：
 */

public interface IPresenter<M extends IModel,V extends  IView >{
    void setMode(M m);
    void addView(V v);
    void attach();
    void detach();
}
