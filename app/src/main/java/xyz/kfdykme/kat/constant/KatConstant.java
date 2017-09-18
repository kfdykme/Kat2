package xyz.kfdykme.kat.constant;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/7 22:55.
 * Last Edit on 2017/9/7 22:55
 * 修改备注：
 */

public class KatConstant {


    //other constant
    public static final String PREFERENCE_NAME = "kat.sp";

    public static final String ALL_MESSAGE = "all_message";

    public static final String SEND_MESSAGE = "send_message";

    public static final String GET_MESSAGE = "get_message";


    //Sence ids
    public static final int UNIT_SENCE_MOVIE = 5419;

    public static final int UNIT_SENCE_KAT = 10579;

    //KAT SENCE KEY

    public  static final String KEY_KAT_NOTE_FINISH = "kw_note_finish";

    public static final String KEY_KAT_NOTE_WRITE = "kw_note_write";


    // KAT NOTE status

    public static final int STATUS_KAT_NOTE_UNSTART = 0;

    public static final int STATUS_KAT_NOTE_WRITING = 1;

    public static final int STATUS_KAT_PHONE_WAIT_NUMBER = 2;


    //Intent
    //
    public static final String INTENT_KAT_NOTE_CALL = "KAT_NOTE_CALL";

    public static final String INTENT_KAT_NOTE_FINISH = "KAT_NOTE_FINISH";

    public static final String INTENT_KAT_NOTE_SEE_AF_WRITE = "KAT_NOTE_SEE_AF_WRITE";

    public static final String INTENT_KAT_NOTE_W = "KAT_NOTE_W";


    //File Helper
    //创建文件时的相对路径
    public static final String reDir = "note";

    public static final String NOTE_FILE_TYPE = "kn";


    //Error
    public static final String ERROR_CREATE_FILE_FAILE = "创建文件失败";

    //words
    //UNIT词槽名称
    public static final String WORD_SYSTEM_TIME = "sys_time";
}
