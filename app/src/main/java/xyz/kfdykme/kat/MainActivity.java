package xyz.kfdykme.kat;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;

import xyz.kfdykme.kat.basic.KatActivity;
import xyz.kfdykme.kat.constant.KatConstant;
import xyz.kfdykme.kat.model.KatNote;
import xyz.kfdykme.kat.note.NoteActivity;
import xyz.kfdykme.kat.note.NoteDialog;
import xyz.kfdykme.kat.notelist.NoteListDialog;
import xyz.kfdykme.kat.notelist.adapter.NoteListAdapter;
import xyz.kfdykme.kat.service.KatService;
import xyz.kfdykme.kat.talk.TalkActivity;
import xyz.kfdykme.kat.util.FileHelper;


public class MainActivity extends KatActivity {


    ImageView iv_view ;
    AnimationDrawable catRun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPermission();


         iv_view = (ImageView) findViewById(R.id.iv);
        View iv_intro = findViewById(R.id.iv_intro);
        AnimationDrawable catWalk = (AnimationDrawable) getResources().getDrawable(R.drawable.cat_walk);
        iv_view.setImageDrawable(catWalk);
         catRun = (AnimationDrawable) getResources().getDrawable(R.drawable.cat_run);

        catWalk.start();
//        Glide.with(getActivity()).load(R.drawable.cat_walk).asGif().into(iv_view);
        iv_view.setOnClickListener(mOnClickListener);
        iv_intro.setOnClickListener(mOnClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        try {
//            final NoteListAdapter adapter = new NoteListAdapter(
//                    FileHelper.readFileList(KatConstant.reDir),getActivity());
//            adapter.setOnItemClickListener(new NoteListAdapter.OnItemClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String name = ((TextView)view).getText().toString();
//                    try {
//                        String content = FileHelper.readFileByName(KatConstant.reDir,name+".kn");
//                        KatNote note = new Gson().fromJson(content,KatNote.class);
//                        new NoteDialog(note,getActivity()).show();
//                        //getActivity().startActivity(new Intent(getActivity(), NoteActivity.class));
//                        //EventBus.getDefault().postSticky(note);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onLongCick(View view) {
//                    try {
//                        FileHelper.findFileByName(KatConstant.reDir,((TextView)view).getText().toString()).delete();
//                        adapter.notifyDataSetChanged();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            rv.setAdapter(adapter);
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(getActivity(),"can't load files",Toast.LENGTH_SHORT).show();
//        }


    }

    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS


        };
        ArrayList toApplyList = new ArrayList();

        for (String perm :permissions){

            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.

            }

        }

        String tmpList[] = new String[toApplyList.size()];

        if (!toApplyList.isEmpty()){

            ActivityCompat.requestPermissions(this, (String[]) toApplyList.toArray(tmpList), 123);

        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.iv:
                    iv_view.setImageDrawable(catRun);
                    catRun.start();
                    ObjectAnimator animator = new ObjectAnimator().ofFloat(iv_view,"x",iv_view.getX(),-400);
                    animator.setDuration(1000);
                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            onCall();
                            iv_view.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });

                    animator.start();
                    break;
                case R.id.iv_intro:
                    openIntroActivity();
                    break;
            }
        }
    };

    private void openIntroActivity() {
        startActivity(new Intent(this,IntroduceActivity.class));
    }

    private void onCall(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!Settings.canDrawOverlays(this))
            {
                showToast("当前无权限，请授权");
                Intent i = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:"+getPackageName()));
                startActivityForResult(i,1234);
            }else

             startService(new Intent(this,KatService.class));
        }

        startService(new Intent(this,KatService.class));




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if(requestCode == 1234){
                if(!Settings.canDrawOverlays(this)){
                    showToast("权限请求失败");
                }else{
                    showToast("权限请求成功");


                    startService(new Intent(this, KatService.class));

                }
            }
        }

    }



}
