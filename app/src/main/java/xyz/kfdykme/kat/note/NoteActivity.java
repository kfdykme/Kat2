package xyz.kfdykme.kat.note;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import xyz.kfdykme.kat.R;
import xyz.kfdykme.kat.basic.KatActivityWithEventBus;
import xyz.kfdykme.kat.model.KatNote;
import xyz.kfdykme.kat.note.model.NoteModel;
import xyz.kfdykme.kat.note.presenter.NotePresenter;
import xyz.kfdykme.kat.note.view.NoteView;

public class NoteActivity extends KatActivityWithEventBus {

    NotePresenter mPresenter;

    NoteView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_note);

    }


    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public  void onCreateView(KatNote note){
//        mPresenter = new NotePresenter(this);
//        mView = new NoteView(this);
//        mPresenter.addView(mView);
//        mPresenter.setMode(new NoteModel(note));
//        mPresenter.attach();
    }
}
