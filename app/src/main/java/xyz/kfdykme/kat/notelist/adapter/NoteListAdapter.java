package xyz.kfdykme.kat.notelist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.util.List;

import xyz.kfdykme.kat.R;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/15 13:04.
 * Last Edit on 2017/9/15 13:04
 * 修改备注：
 */

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    List<File> files;

    Context context;

    LayoutInflater inflater;

    public NoteListAdapter(List<File> files, Context context) {
        this.files = files;
        this.context = context;
        inflater =LayoutInflater.from(context);
    }

    @Override
    public NoteListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_note_list,null,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(NoteListAdapter.ViewHolder holder, final int position) {
        holder.tv_fileName.setText(files.get(position).getName().substring(0,files.get(position).getName().length()-3));

        holder.tv_fileName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (getOnItemClickListener() != null) getOnItemClickListener().onLongCick(view,position,files.get(position) );
                return  false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_fileName;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_fileName = itemView.findViewById(R.id.tv_file_name);
            tv_fileName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getOnItemClickListener()!= null)getOnItemClickListener().onClick(view);
                }
            });

        }
    }

    public interface  OnItemClickListener{
        void onClick(View view);
        void onLongCick(View view,int pos,File file);
    }

    private OnItemClickListener onItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
