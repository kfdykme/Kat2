package xyz.kfdykme.kat.note.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import xyz.kfdykme.kat.R;
import xyz.kfdykme.kat.basic.KatActivityWithEventBus;
import xyz.kfdykme.kat.basic.KatView;
import xyz.kfdykme.kat.model.KatNote;
import xyz.kfdykme.kat.note.listener.NoteEventListener;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/15 09:14.
 * Last Edit on 2017/9/15 09:14
 * 修改备注：
 */

public class NoteView extends KatView<NoteEventListener> implements INoteView {
    View rootView;

    public TextView tv_title;

    public TextView tv_time;

    public TextView tv_content;
    
    public EditText et_title;

    public EditText et_time;

    public EditText et_content;

    public TextView tv_cancel;

    public TextView tv_edit;
    
    public RelativeLayout rl_read;
    
    public RelativeLayout rl_edit;
    
    

    public NoteView(Context context) {
        super(context);

    }

    private void initView(){
        //rootView = LayoutInflater.from(getContext()).inflate(R.layout.view_note,null);
        tv_title = rootView.findViewById(R.id.tv_title);
        tv_time = rootView.findViewById(R.id.tv_time);
        tv_content = rootView.findViewById(R.id.tv_content);
        tv_cancel = rootView.findViewById(R.id.tv_cancel);
        tv_edit = rootView.findViewById(R.id.tv_edit);
        et_title = rootView.findViewById(R.id.et_title);
        et_time = rootView.findViewById(R.id.et_time);
        et_content = rootView.findViewById(R.id.et_content);
        tv_title.setOnClickListener(mOnClicnListener);
        tv_time.setOnClickListener(mOnClicnListener);
        tv_content.setOnClickListener(mOnClicnListener);
        tv_cancel.setOnClickListener(mOnClicnListener);
        tv_edit.setOnClickListener(mOnClicnListener);

        rl_edit =rootView.findViewById(R.id.rl_edit);
        rl_read = rootView.findViewById(R.id.rl_read);

    }

    private View.OnClickListener mOnClicnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(getEventListener()!=null)getEventListener().onClick(view);
        }
    };


    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void upData(KatNote note) {
        if(tv_content==null)initView();
        tv_title.setText(note.getTitle());
        tv_time.setText(note.getTime());
        tv_content.setText(note.getContent());

    }

    @Override
    public KatActivityWithEventBus getActivity(){
        return (KatActivityWithEventBus) getContext();
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }
}
