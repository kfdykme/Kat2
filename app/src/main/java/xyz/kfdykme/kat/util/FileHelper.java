package xyz.kfdykme.kat.util;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import xyz.kfdykme.kat.constant.KatConstant;
import xyz.kfdykme.kat.model.KatNote;

/**
 * Created by kf on 2017/6/30.
 */

public class FileHelper {

    public static String DEFAULT_ROOT_DIR = "/kat/";

    public static String rootDIr = DEFAULT_ROOT_DIR;

    public static String NOT_FIND = "null";

    /*
     * TODO: 创建or更新文件
     */
    public static void updateFile(@NonNull String reDir,@NonNull String content,@NonNull String name ,@NonNull String fileType) throws IOException {
        File root = Environment.getExternalStorageDirectory();

        File dir = new File(root.getCanonicalPath()+rootDIr+reDir);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File targetFile = new File(dir.getCanonicalPath()+"/"+name+"."+fileType);
        if(targetFile.exists())
        targetFile.delete();
        RandomAccessFile raf = new RandomAccessFile(targetFile,"rw");
        raf.write(content.getBytes());
        raf.close();
        Log.i("FileHelper","creat/edit file "+name+" successfully");
    }

    /*
     * TODO: 获取文件，将文件内容以String形式获取
     */
    public static String readFile(@NonNull String reDir,@NonNull String name,@NonNull String fileType) throws IOException {
        File root = Environment.getExternalStorageDirectory();

        File dir = new File(root.getCanonicalPath()+rootDIr+reDir);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File targetFile = new File(dir.getCanonicalPath()+"/"+name+"."+fileType);

        String content = "";
        if(targetFile.isFile() && targetFile.exists()){

            InputStreamReader read = null;

                read = new InputStreamReader(new FileInputStream(targetFile),"utf8");

            BufferedReader bufferedReader = new BufferedReader(read);
                String line = null;

                while((line = bufferedReader.readLine()) != null) content+=line;


        }else{
            content = NOT_FIND;
        }
        return content;
    }

    public static String readFileByName(@NonNull String reDir,@NonNull String name) throws IOException {
        File root = Environment.getExternalStorageDirectory();

        File dir = new File(root.getCanonicalPath()+rootDIr+reDir);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File targetFile = new File(dir.getCanonicalPath()+"/"+name);

        String content = "";
        if(targetFile.isFile() && targetFile.exists()){

            InputStreamReader read = null;

            read = new InputStreamReader(new FileInputStream(targetFile),"utf8");

            BufferedReader bufferedReader = new BufferedReader(read);
            String line = null;

            while((line = bufferedReader.readLine()) != null) content+=line;


        }else{
            content = NOT_FIND;
        }
        return content;
    }

    public static File findFileByName(@NonNull String reDir,@NonNull String name) throws IOException {
        File root = Environment.getExternalStorageDirectory();

        File dir = new File(root.getCanonicalPath()+rootDIr+reDir);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File targetFile = new File(dir.getCanonicalPath()+"/"+name);
        return targetFile;
    }

    public static List<File> readFileList(@NonNull String reDir) throws IOException {
        List<File> files = new ArrayList<>();

        File root = Environment.getExternalStorageDirectory();

        File dir = new File(root.getCanonicalPath()+rootDIr+reDir);
        if(!dir.exists()) return null;

        File[] filearray = dir.listFiles();
        for(int i =0; i< filearray.length;i++){
            if(!filearray[i].isDirectory())
            {
                files.add(filearray[i]);
            }
        }

        return files;
    }
    
    public static void createNote(KatNote note) throws IOException {

        String content = "error";

        content = new Gson().toJson(note);
        FileHelper.updateFile(KatConstant.reDir,content,note.getTitle(), KatConstant.NOTE_FILE_TYPE);

        Log.i("FileHelper","create =====> "+note.getContent());
    }
}