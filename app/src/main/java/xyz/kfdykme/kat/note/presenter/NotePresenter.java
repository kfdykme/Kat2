package xyz.kfdykme.kat.note.presenter;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import xyz.kfdykme.kat.R;
import xyz.kfdykme.kat.basic.KatActivityWithEventBus;
import xyz.kfdykme.kat.basic.KatPresenter;
import xyz.kfdykme.kat.constant.KatConstant;
import xyz.kfdykme.kat.note.NoteDialog;
import xyz.kfdykme.kat.note.listener.NoteEventListener;
import xyz.kfdykme.kat.note.model.NoteModel;
import xyz.kfdykme.kat.note.view.NoteView;
import xyz.kfdykme.kat.util.FileHelper;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/15 09:16.
 * Last Edit on 2017/9/15 09:16
 * 修改备注：
 */

public class NotePresenter extends KatPresenter<NoteModel,NoteView> {
    private NoteDialog dialog;

    public NotePresenter(NoteDialog dialog) {
        super(dialog.getContext());
        this.dialog = dialog;
    }


    @Override
    protected void onAttach() {
        getView().setEventListener(mNoteEventListener);
        getView().upData(getModel().getNote());
//        ((KatActivityWithEventBus)getContext()).setContentView(getView().getRootView());
//        ((KatActivityWithEventBus)getContext()).initToolbar((Toolbar) getView().getRootView().findViewById(R.id.toolbar));
    }

    @Override
    protected void onDetach() {

    }

    private NoteEventListener mNoteEventListener = new NoteEventListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_title:
                    break;
                case R.id.tv_content:
                    break;
                case R.id.tv_time:
                    break;
                case R.id.tv_cancel:
                    dialog.dismiss();
                    break;
                case R.id.tv_edit:
                    {

                        TextView tv = (TextView) view;
                        String text = tv.getText().toString();
                        if(text.equals("edit")){
                            doEdit();
                        }else {
                            doSave();
                        }

                    }
                    break;

            }
        }
    };

    public void doEdit(){

        getView().rl_read.setVisibility(View.GONE);
        getView().rl_edit.setVisibility(View.VISIBLE);
        getView().et_content.setText(getView().tv_content.getText().toString());
        getView().et_time.setText(getView().tv_time.getText().toString());
        getView().et_title.setText(getView().tv_title.getText().toString());
        getView().tv_edit.setText(getView().tv_edit.getText().toString().equals("edit")?"save":"edit");
    }

    public void doSave(){

        getView().rl_edit.setVisibility(View.GONE);
        getView().rl_read.setVisibility(View.VISIBLE);

        try {
            FileHelper.findFileByName(KatConstant.reDir,getView().tv_title.getText().toString()).delete();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getView().tv_content.setText(getView().et_content.getText().toString());
        getView().tv_time.setText(getView().et_time.getText().toString());
        getView().tv_title.setText(getView().et_title.getText().toString());
        getView().tv_edit.setText(getView().tv_edit.getText().toString().equals("edit")?"save":"edit");


        getModel().getNote().setContent(getView().tv_content.getText().toString());
        getModel().getNote().setTime(getView().tv_time.getText().toString());
        getModel().getNote().setTitle(getView().tv_title.getText().toString());

        try {
            FileHelper.createNote(getModel().getNote());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
