package xyz.kfdykme.kat.model;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/14 22:50.
 * Last Edit on 2017/9/14 22:50
 * 修改备注：
 */

public class KatNote {
    String title;
    String content;
    String time;

    public KatNote() {
    }

    public KatNote(String title, String content, String time) {
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
