package xyz.kfdykme.kat.note.model;

import xyz.kfdykme.kat.basic.KatModel;
import xyz.kfdykme.kat.model.KatNote;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/15 09:13.
 * Last Edit on 2017/9/15 09:13
 * 修改备注：
 */

public class NoteModel extends KatModel implements INoteModel {

    KatNote note;

    public NoteModel(KatNote note){
        this.note = note;
    }

    public KatNote getNote() {
        return note;
    }
}
