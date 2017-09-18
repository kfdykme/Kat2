package xyz.kfdykme.kat.service.model;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xyz.kfdykme.kat.aip.unit.APIService;
import xyz.kfdykme.kat.aip.unit.exception.UnitError;
import xyz.kfdykme.kat.aip.unit.listener.OnResultListener;
import xyz.kfdykme.kat.aip.unit.model.AccessToken;
import xyz.kfdykme.kat.constant.KatConstant;
import xyz.kfdykme.kat.KatKeyWord;
import xyz.kfdykme.kat.basic.KatModel;
import xyz.kfdykme.kat.model.KatNote;
import xyz.kfdykme.kat.model.UNITResult;
import xyz.kfdykme.kat.util.FileHelper;
import xyz.kfdykme.kat.util.SharedUtil;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/4 10:46.
 * Last Edit on 2017/9/4 10:46
 * 修改备注：
 */


//{
//        "action_list": [
//        {
//        "action_id": "kat_note_w_satisfy",
//        "action_type": {
//        "act_target": "",
//        "act_target_detail": "",
//        "act_type": "satisfy",
//        "act_type_detail": ""
//        },
//        "arg_list": [],
//        "code_actions": [],
//        "confidence": 1,
//        "exe_status": [],
//        "hint_list": [],
//        "main_exe": "katNoteWrite",
//        "say": ""
//        }
//        ],
//        "log_id": "unitweb_log_be172ce0-97c9-11e7-aaeb-45e0a84e1ba2_10579",
//        "msg": "ok",
//        "qu_res": {
//        "intent_candidates": [
//        {
//        "extra_info": null,
//        "from_who": "smart_qu",
//        "func_slot": "",
//        "intent": "KAT_NOTE_W",
//        "intent_confidence": 100,
//        "intent_need_clarify": false,
//        "match_info": "[D:kw_note_w][W:0-999]",
//        "slots": []
//        }
//        ],
//        "log_id": "unitweb_log_be172ce0-97c9-11e7-aaeb-45e0a84e1ba2_10579",
//        "raw_query": "kw_note_write 开始吧",
//        "status": 0,
//        "timestamp": 1
//        },
//        "schema": {
//        "bot_merged_slots": [
//        {
//        "begin": 0,
//        "confidence": 0,
//        "length": 0,
//        "merge_method": "update",
//        "normalized_word": "写笔记",
//        "original_word": "写笔记",
//        "session_offset": 3,
//        "type": "user_note_call",
//        "word_type": ""
//        },
//        {
//        "begin": 0,
//        "confidence": 0,
//        "length": 0,
//        "merge_method": "update",
//        "normalized_word": "想",
//        "original_word": "想",
//        "session_offset": 3,
//        "type": "user_want",
//        "word_type": ""
//        }
//        ],
//        "current_qu_intent": "KAT_NOTE_W",
//        "intent_confidence": 100
//        },
//        "status": 0,
//        "msg_type": "bot",
//        "bot_type": "userbot"
//        }
public class ServiceModel extends KatModel implements IServiceModel {

    private String accessToken;

    public static final String  AK = "UOGbuTOVciIGW7WDy6QihTZB";

    public static final String SK = "PVDBLdUs2i1mZrbSSHeFHItpvlsOxBn2";

    private int senceId = 0;

    private String sessionId = "";

    private String tempNote = "";

    private String tempTime = "";

    private KatKeyWord mKatKeyWord = new KatKeyWord();

    private KatNote lastNote;
    @Override
    public void initAccessToken() {

        APIService.getInstance().initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                accessToken = result.getAccessToken();
                Log.i("MainActivity", "AccessToken->" + result.getAccessToken());
                if (!TextUtils.isEmpty(accessToken)) {
                    //resendWaitList();
                }

            }

            @Override
            public void onError(UnitError error) {
                Log.i("wtf", "AccessToken->" + error.getErrorMessage());
            }

            @Override
            public void onUnitResult(UNITResult result) {

            }
        }, AK, SK);
    }

    @Override
    public void saveSendMessage(Context context, String text) {
        saveStringByShared(context, KatConstant.ALL_MESSAGE,text);
        saveStringByShared(context, KatConstant.SEND_MESSAGE,text);
        
        
    }

    @Override
    public void saveGetMessage(Context context, String text) {
        saveStringByShared(context, KatConstant.ALL_MESSAGE,text);
        saveStringByShared(context, KatConstant.GET_MESSAGE,text);
    }

    @Override
    public String getMessage(Context context, String key) {
        return SharedUtil.getString(context,key);
    }

    @Override
    public int getSenceId() {
        return senceId;
    }

    @Override
    public void setSenceId(int senceId) {
        this.senceId = senceId;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void createNote() throws IOException {
        String title = "   ";
        if(tempNote!=null && tempNote.length()> KatConstant.KEY_KAT_NOTE_WRITE.length())
            title = tempNote.substring(0,5 <= tempNote.length() ? 5:tempNote.length());
        String content = "error";

         lastNote = new KatNote(title,tempNote,tempTime);
        content = new Gson().toJson(lastNote);
        FileHelper.updateFile(KatConstant.reDir,content,title.replace('\n',' ').trim(), KatConstant.NOTE_FILE_TYPE);
        tempNote = "";
        tempTime = "";
        Log("create =====> "+tempNote);
    }

    @Override
    public void addNotes(String news) {
        tempNote = tempNote+news+"\n\n";
        Log("add ------> node"+ news);
    }

    @Override
    public void addTime(String time) {
        tempTime = time;
        Log("add ------> time"+ time);
    }

    @Override
    public KatKeyWord getKeyWord() {
        return mKatKeyWord;
    }

    private void saveStringByShared(Context context, String key, String text){
        String string = SharedUtil.getString(context,key);
        StringList sL;
        if(string!=null) {
            sL = new Gson().fromJson(string, StringList.class);
            sL.getData().add(text);
        }else
        {
            sL = new StringList();
            List<String> s = new ArrayList<String>();
            s.add(text);
            sL.setData(s);
        }

        string = new Gson().toJson(sL);
        SharedUtil.saveString(context, key,string);
    }

    private void Log(String t){
        Log.i("ServiceModel",t);
    }
    
    public class StringList {
        List<String> data;

        public StringList() {
        }

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }
    }

    @Override
    public KatNote getLastNote() {
        return lastNote;
    }
}
