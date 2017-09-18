package xyz.kfdykme.kat.note;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import xyz.kfdykme.kat.R;
import xyz.kfdykme.kat.constant.KatConstant;
import xyz.kfdykme.kat.model.KatNote;
import xyz.kfdykme.kat.note.model.NoteModel;
import xyz.kfdykme.kat.note.presenter.NotePresenter;
import xyz.kfdykme.kat.note.view.NoteView;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/15 17:08.
 * Last Edit on 2017/9/15 17:08
 * 修改备注：
 */

public class NoteDialog extends Dialog {

    public NotePresenter mPresenter;

    NoteView mView;

    public boolean isNew = false;

    public interface  OnDismissListener{
        void onDismiss();
    }

    Context context;
    public NoteDialog(KatNote note,Context context) {
        super(context);
        mPresenter = new NotePresenter(this);
        mView = new NoteView(getContext());
        mPresenter.addView(mView);
        mPresenter.setMode(new NoteModel(note));
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        WindowManager.LayoutParams a = getWindow().getAttributes();
        a.height = WindowManager.LayoutParams.WRAP_CONTENT;

        getWindow().setAttributes(a);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_note,null);
        mView.setRootView(view);
        setContentView(view);
        mPresenter.attach();
        setTitle("-Note");
        if(isNew)mPresenter.doEdit();
    }



}
