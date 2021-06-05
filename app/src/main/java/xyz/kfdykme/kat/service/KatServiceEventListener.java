package xyz.kfdykme.kat.service;

import android.view.MotionEvent;
import android.view.View;

import xyz.kfdykme.kat.aip.unit.exception.UnitError;
import xyz.kfdykme.kat.model.UNITResult;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/4 08:02.
 * Last Edit on 2017/9/4 08:02
 * 修改备注：
 */

public interface KatServiceEventListener extends xyz.kfdykme.kat.basic.EventListener{
    void onClick(View view);

    void onDbClick(View view);

    void onTriClick(View view);

    void onLongClick(View view);

    void onTouch(View view, MotionEvent motionEvent);

//    void onVoiceRecognizerCallback(String text);

    void toHide(MotionEvent e);

    void dealWithIntent(String currentQueryIntent);

    void onSpeechEvent(String name, String params, byte[] data, int offset, int length);

    void onSpeechCallback(String text);

    void onUnitResult(UNITResult result);

    void onUnitError(UnitError error);

    void unitCommunicate(String text);


}
