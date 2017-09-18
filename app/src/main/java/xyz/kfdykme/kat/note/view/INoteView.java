package xyz.kfdykme.kat.note.view;

import android.view.View;

import xyz.kfdykme.kat.basic.IView;
import xyz.kfdykme.kat.basic.KatActivityWithEventBus;
import xyz.kfdykme.kat.model.KatNote;
import xyz.kfdykme.kat.note.listener.NoteEventListener;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/15 08:53.
 * Last Edit on 2017/9/15 08:53
 * 修改备注：
 */

public interface INoteView extends IView<NoteEventListener> {

    View getRootView();

    void upData(KatNote note);

    KatActivityWithEventBus getActivity();

}
