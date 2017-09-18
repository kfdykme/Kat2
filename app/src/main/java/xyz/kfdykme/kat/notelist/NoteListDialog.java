package xyz.kfdykme.kat.notelist;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import xyz.kfdykme.kat.R;
import xyz.kfdykme.kat.constant.KatConstant;
import xyz.kfdykme.kat.model.KatNote;
import xyz.kfdykme.kat.note.NoteDialog;
import xyz.kfdykme.kat.notelist.adapter.NoteListAdapter;
import xyz.kfdykme.kat.util.FileHelper;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/15 21:26.
 * Last Edit on 2017/9/15 21:26
 * 修改备注：
 */

public class NoteListDialog extends Dialog {

    RecyclerView rv;
    NoteListAdapter adapter;
    TextView tv_cancel;
    TextView tv_edit;
    public NoteListDialog(@NonNull Context context) {
        super(context);
        setTitle("Note List");
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        WindowManager.LayoutParams a = getWindow().getAttributes();
        a.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(a);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_notelist,null);
        setContentView(view);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        try {
            adapter= new NoteListAdapter(
                    FileHelper.readFileList(KatConstant.reDir),
                    getContext());
            adapter.setOnItemClickListener(new NoteListAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view) {
                    String name = ((TextView)view).getText().toString();
                    try {
                        String content = FileHelper.readFileByName(KatConstant.reDir,name+".kn");
                        KatNote note = new Gson().fromJson(content,KatNote.class);
                        new NoteDialog(note,getContext()).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onLongCick(View view,int pos,File file) {
                    adapter.getFiles().remove(pos);
                    file.delete();
                    adapter.notifyDataSetChanged();
                }
            });
            rv.setAdapter(adapter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        rv.setItemAnimator(new DefaultItemAnimator());
        tv_cancel = view.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tv_edit = view.findViewById(R.id.tv_edit);
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               NoteDialog dialog =  new NoteDialog(new KatNote("","",""),getContext());
                dialog.isNew = true;
                dialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        try {
                            adapter.setFiles(FileHelper.readFileList(KatConstant.reDir));
                            adapter.notifyDataSetChanged();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialog.show();
            }
        });
    }


}
