package xyz.kfdykme.kat.basic;

/**
 * Project Name: Kat
 * Class Description:
 * Created by kf on 2017/9/3 20:09.
 * Last Edit on 2017/9/3 20:09
 * 修改备注：
 */

public interface IView<E extends  EventListener> {
    void setEventListener(E eventListener);
}
