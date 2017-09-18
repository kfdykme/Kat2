package xyz.kfdykme.kat.service.model;

import android.content.Context;

import java.io.IOException;

import xyz.kfdykme.kat.KatKeyWord;
import xyz.kfdykme.kat.basic.IModel;
import xyz.kfdykme.kat.model.KatNote;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/4 10:38.
 * Last Edit on 2017/9/4 10:38
 * 修改备注：
 */

public interface IServiceModel extends IModel {

    void initAccessToken();

    void saveSendMessage(Context context,String text);

    void saveGetMessage(Context context, String text);

    String getMessage(Context context,String key);

    int getSenceId();

    void setSenceId(int id);

    String getSessionId();

    void setSessionId(String id);

    void createNote() throws IOException;

    void addNotes(String news);

    void addTime(String time);

    KatKeyWord getKeyWord();

    KatNote getLastNote();
}
