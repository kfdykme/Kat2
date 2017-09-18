package xyz.kfdykme.kat.service.presenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;

import xyz.kfdykme.kat.aip.unit.APIService;
import xyz.kfdykme.kat.aip.unit.exception.UnitError;
import xyz.kfdykme.kat.aip.unit.listener.OnResultListener;
import xyz.kfdykme.kat.aip.unit.model.CommunicateResponse;
import xyz.kfdykme.kat.constant.SpeechConstant;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import xyz.kfdykme.kat.constant.KatConstant;
import xyz.kfdykme.kat.KatKeyWord;
import xyz.kfdykme.kat.basic.KatPresenter;
import xyz.kfdykme.kat.model.Bot_merged_slots;
import xyz.kfdykme.kat.model.Hint;
import xyz.kfdykme.kat.model.KatNote;
import xyz.kfdykme.kat.model.UNITResult;
import xyz.kfdykme.kat.note.NoteActivity;
import xyz.kfdykme.kat.note.NoteDialog;
import xyz.kfdykme.kat.notelist.NoteListDialog;
import xyz.kfdykme.kat.service.KatServiceEventListener;
import xyz.kfdykme.kat.service.KatService;
import xyz.kfdykme.kat.service.model.ServiceModel;
import xyz.kfdykme.kat.service.view.ServiceView;
import xyz.kfdykme.kat.util.PhoneUtil;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/4 10:45.
 * Last Edit on 2017/9/4 10:45
 * 修改备注：
 */

public class ServicePresenter extends KatPresenter<ServiceModel, ServiceView> implements IServicePresenter {

    private EventManager asr;

    private boolean logTime = true;

    private boolean enableOffline = false;

    private String currentQueryIntent = "";

    private int currentStatus = KatConstant.STATUS_KAT_NOTE_UNSTART;


    public ServicePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onAttach() {
        getView().setEventListener(mEventListener);


        getView().show();
        APIService.getInstance().init(getContext());

        asr = EventManagerFactory.create(getContext(), "asr");
        asr.registerListener(new com.baidu.speech.EventListener() {

            @Override
            public void onEvent(String name, String params, byte[] data, int offset, int length) {
                mEventListener.onSpeechEvent(name, params, data, offset, length);
            }
        });

        start();
        getModel().initAccessToken();
        getModel().setSenceId(KatConstant.UNIT_SENCE_KAT);

    }

    @Override
    protected void onDetach() {
//        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
    }

    private void start() {
        /**
         * 测试参数填在这里
         */
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        String event = null;
        event = SpeechConstant.ASR_START;

        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        String json = "{\"accept-audio-data\":false,\"disable-punctuation\":false,\"accept-audio-volume\":true}"; //可以替换成自己的json
        asr.send(event, json, null, 0, 0);
    }

    private void stop() {
        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0); //
    }

//
//    private void loadOfflineEngine() {
//        Map<String, Object> params = new LinkedHashMap<String, Object>();
//        params.put(SpeechConstant.DECODER, 2);
//        params.put(SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH, "assets://baidu_speech_grammar.bsg");
//        asr.send(SpeechConstant.ASR_KWS_LOAD_ENGINE, new JSONObject(params).toString(), null, 0, 0);
//    }
//
//    private void unloadOfflineEngine() {
//        asr.send(SpeechConstant.ASR_KWS_UNLOAD_ENGINE, null, null, 0, 0); //
//    }


    //基本的事件监听
    public KatServiceEventListener mEventListener = new KatServiceEventListener() {

        boolean isLongClicked = false;

        float lastX = 0;
        float lastY = 0;

        int hWidth;
        int hHeight;

        @Override
        public void onClick(View view) {
            Log.i("Service", "Click");
            toHide(null);
            new NoteListDialog(getContext()).show();
            //
//            APIService.getInstance().communicate(new OnResultListener<CommunicateResponse>() {
//                @Override
//                public void onResult(CommunicateResponse result) {
//
//                    Toast.makeText(getContext(),result.getJsonRes(),Toast.LENGTH_SHORT);
//
//                    Log.i("Api",result.toString());
//                    Log.i("Api",result.getJsonRes());
//                    Log.i("Api",result.jsonRes);
//                }
//
//                @Override
//                public void onError(UnitError error) {
//                    if(error != null) {
//                        Log.i("Api", error.getErrorMessage());
//                    } else{
//                        Log.i("Api","eror is null");
//                    }
//                }
//            }, 5419, "我想看周星驰的电影", null);

        }

        @Override
        public void onLongClick(View view) {

            isLongClicked = true;
            getView().move(null);
        }

        @Override
        public void onTouch(View view, MotionEvent motionEvent) {

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = motionEvent.getRawX();
                    lastY = motionEvent.getRawY();
                    hWidth = getView().getWindowManager().getDefaultDisplay().getWidth() / 2;
                    hHeight = getView().getWindowManager().getDefaultDisplay().getHeight() / 2;

                    break;
                case MotionEvent.ACTION_MOVE:
                    if (isLongClicked)
                        toRun(motionEvent);

                    break;
                case MotionEvent.ACTION_UP:
                    isLongClicked = false;
                    toHide(motionEvent);
                    break;
            }

            getView().getWindowManager().updateViewLayout(getView().getRootView(), getView().getParams());
            lastX = motionEvent.getRawX();
            lastY = motionEvent.getRawY();
        }

        @Override
        public void dealWithIntent(String intent) {

            Log.e("xx", intent);
            //switch current query Intent
            currentQueryIntent = intent;

            //open writing note mode
            if (intent.equals(KatConstant.INTENT_KAT_NOTE_CALL)) {

                currentStatus = KatConstant.STATUS_KAT_NOTE_WRITING;
            }

            //finish write one note and reset session
            if (intent.equals(KatConstant.INTENT_KAT_NOTE_SEE_AF_WRITE)) {
                getModel().setSessionId("");
            }
        }

        private void toRun(MotionEvent e) {
            if (lastX < e.getRawX()) {
                getView().getRootView().setScaleX(-1);
                getView().getTv().setScaleX(-1);
            } else {
                getView().getRootView().setScaleX(1);

                getView().getTv().setScaleX(1);
            }
            //
            getView().getParams().x += (e.getRawX() - lastX);
            getView().getParams().y += (e.getRawY() - lastY);

        }

        @Override
        public void toHide(MotionEvent e) {
            int left = (int) (hWidth * 0.6);
            int right = (int) (hWidth * 1.4);
            if (getView().getParams().x + getView().getIv().getWidth() / 2 < left) {
                getView().hide(e, 0);
                getView().getParams().x = 0;
            } else if (getView().getParams().x + getView().getIv().getWidth() / 2 + getView().getTv().getWidth() > right) {
                getView().hide(e, 1);
                getView().getParams().x = getView().getWindowManager().getDefaultDisplay().getWidth();
            } else {
                getView().stand(e);
            }
        }

        @Override
        public void onSpeechEvent(String name, String params, byte[] data, int offset, int length) {
            String logTxt = "name: " + name;


            if (params != null && !params.isEmpty()) {
                logTxt += " ;params :" + params;
            }

            //TODO : when parital
            if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {

                if (params.contains("\"nlu_result\"")) {
                    if (length > 0 && data.length > 0) {
                        logTxt += ", 语义解析结果：" + new String(data, offset, length);
//                    showToast(new String(data, offset, length));
                    }
                }
            } else if (data != null) {
                logTxt += " ;data length=" + data.length;
            }


            //TODO : when exit
            if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_EXIT)) {
                start();
                Log.i(getClass().toString(), "------> Restart");
            }

            //TODO : when finish
            if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_FINISH)) {
                try {
                    JSONObject jO = new JSONObject(params);
                    String s = jO.getString("results_recognition");
                    s = s.substring(2, s.length() - 2);
                    onSpeechCallback(s);
                    //showToast();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            //TODO : when ready
            if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
                getView().listen();
            }
            //TODO : when end
            if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_END)) {

                //听语音结束后判断
                if (getView() != null)
                    mEventListener.toHide(null);
            }


        }

        @Override
        public void onSpeechCallback(String text) {
            //展示用户声音识别情况
            getView().showSendMessage(text);

            if (text.equals("查看"))
                new NoteListDialog(getContext()).show();
            if (currentStatus == KatConstant.STATUS_KAT_PHONE_WAIT_NUMBER) {

                currentStatus = KatConstant.STATUS_KAT_NOTE_UNSTART;
                return;
            }
            if (text.equals("打电话")) {
                if (currentStatus == KatConstant.STATUS_KAT_NOTE_UNSTART)
                    currentStatus = KatConstant.STATUS_KAT_PHONE_WAIT_NUMBER;

                PhoneUtil.getContacts(getContext());

            }
            //1.根据关键字关闭程序
            if(text.equals(new KatKeyWord().getNoteFinish()))
            {
                getView().showTextView("拜拜");

                stop();
                //会导致重开service时服务出错
                Timer a = new Timer();
                a.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        getView().unShow();
                        getContext().stopService(new Intent(getContext(),KatService.class));
                        detach();
                    }
                },1000);


            } else{
                //请求UNIT平台



                //如果是写完笔记，就.....
                if(text.equals("笔记完成") && currentStatus == KatConstant.STATUS_KAT_NOTE_WRITING) {
                    Log.d("xx","finish");
                    try {
                        getModel().createNote();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), KatConstant.ERROR_CREATE_FILE_FAILE + " : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    text = KatConstant.KEY_KAT_NOTE_FINISH;
                    currentStatus = KatConstant.STATUS_KAT_NOTE_UNSTART;
                } else

                    //如果当前正在写笔记 那么在text前加常量识别
                    if(currentStatus == KatConstant.STATUS_KAT_NOTE_WRITING) {
                        getModel().addNotes(text);
                        text = KatConstant.KEY_KAT_NOTE_WRITE + text;
                    }

                unitCommunicate(text);

            }

            getModel().saveSendMessage(getContext(),text);

        }

        @Override
        public void onUnitResult(UNITResult result) {


            String say = result.getResult().getAction_list().get(0).getSay();
            String hint_query = "";
            for(Hint hint :result.getResult().getAction_list().get(0).getHint_list()){
                hint_query =  hint_query + "\n" + hint.getHint_query();
            }
            if( hint_query !=null && !hint_query.isEmpty())
                getView().showTextView(say +hint_query);
            else
                getView().showTextView(say);

            EventBus.getDefault().postSticky(result);
            getModel().setSessionId(result.getResult().getSession_id());


            if(result.getResult().getSchema().getCurrent_qu_intent().equals(KatConstant.INTENT_KAT_NOTE_SEE_AF_WRITE))
                seeNoteAfWrite();
                //Toast.makeText(getContext(),KatConstant.INTENT_KAT_NOTE_SEE_AF_WRITE,Toast.LENGTH_SHORT).show();

            if(currentStatus == KatConstant.STATUS_KAT_NOTE_WRITING)
            {
              for(Bot_merged_slots bot : result.getResult().getSchema().getBot_merged_slots()){
                  if(bot.getType().equals(KatConstant.WORD_SYSTEM_TIME)){
                      getModel().addTime(bot.getNormalized_word());
                  }
              }
            }

            if(say.equals("那么我开始记笔记囖"))
                currentStatus = KatConstant.STATUS_KAT_NOTE_WRITING;
        }

        @Override
        public void onUnitError(UnitError error) {
            if(error!=null && error.getErrorMessage()!=null)
                getView().showTextView("error : " + error.getErrorMessage());
        }

        @Override
        public void unitCommunicate(String text) {
            APIService.getInstance()
                    .communicate(
                            mOnResultListener,
                            getModel().getSenceId(),
                            text,
                            getModel().getSessionId());

        }
    };


    //Unit返回监听
    private OnResultListener mOnResultListener = new OnResultListener<CommunicateResponse>(){
        @Override
        public void onResult(CommunicateResponse result) {


        }


        @Override
        public void onUnitResult(UNITResult result) {
            mEventListener.onUnitResult(result);

        }

        @Override
        public void onError(UnitError error) {
            mEventListener.onUnitError(error);
        }
    };


    @Override
    public void seeNoteAfWrite() {
        KatNote note = getModel().getLastNote();
        //getContext().startActivity(new Intent(getContext(), NoteActivity.class));
        //EventBus.getDefault().postSticky(note);
        new NoteDialog(note,getContext()).show();
        //reset
        getModel().setSessionId("");
    }
}
