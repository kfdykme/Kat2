package xyz.kfdykme.kat.service.view;

import android.view.MotionEvent;

import xyz.kfdykme.kat.basic.IView;
import xyz.kfdykme.kat.service.KatServiceEventListener;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/4 08:01.
 * Last Edit on 2017/9/4 08:01
 * 修改备注：
 */

public interface IServiceView extends IView<KatServiceEventListener> {

    void show();

    void stand(MotionEvent motionEvent);

    void move(MotionEvent motionEvent);

    void hide(MotionEvent motionEvent, int type);

    void listen();

    void initWindow();

    void showSendMessage(String text);

    void showTextView(String text);

    void unShow();

}
