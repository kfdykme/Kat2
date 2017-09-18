package xyz.kfdykme.kat;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/9 16:04.
 * Last Edit on 2017/9/9 16:04
 * 修改备注：
 */

public class KatKeyWord {

    public final String DEFAULT_FINISH = "关闭程序";

    public final String DEFAULT_NOTE_FINISH = "笔记完成";

    private String key_finish ;

    public KatKeyWord(){
        initData();
    }

    void initData(){
        key_finish = DEFAULT_FINISH;
    }

    public String getNoteFinish() {
        return key_finish;
    }
}
