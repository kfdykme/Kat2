package xyz.kfdykme.kat.talk;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import xyz.kfdykme.kat.R;
import xyz.kfdykme.kat.basic.KatActivity;
import xyz.kfdykme.kat.basic.KatActivityWithEventBus;
import xyz.kfdykme.kat.model.UNITResult;
import xyz.kfdykme.kat.service.KatService;

public class TalkActivity extends KatActivityWithEventBus {

    EditText et_talk;

    AppCompatTextView tv_talk;

    Messenger mAcMessenger;

    Messenger mSeMessenger;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0x12){
                addText2TextView(msg.arg1+"");
            }
        }
    };

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mSeMessenger = new Messenger(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);

        tv_talk = (AppCompatTextView) findViewById(R.id.tv_talk);
        et_talk = (EditText) findViewById(R.id.et_talk);

        TextView tv_send = (TextView) findViewById(R.id.tv_send);
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = et_talk.getText().toString();
                et_talk.setText("");
                postText(text);
            }
        });




    }

    private void postText(String text){
        addText2TextView(text);
        EventBus.getDefault().postSticky(text);
    }

    private void addText2TextView(String text){
        tv_talk.setText(tv_talk.getText().toString()+"\n\n"+text);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getResult(UNITResult result){
        addText2TextView(new Gson().toJson(result));
    }
}
