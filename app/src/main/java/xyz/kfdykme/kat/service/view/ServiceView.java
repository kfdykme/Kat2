package xyz.kfdykme.kat.service.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import xyz.kfdykme.kat.R;
import xyz.kfdykme.kat.basic.KatView;
import xyz.kfdykme.kat.service.KatServiceEventListener;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/4 10:45.
 * Last Edit on 2017/9/4 10:45
 * 修改备注：
 */

public class ServiceView extends KatView<KatServiceEventListener> implements IServiceView {

    View rootView;

    WindowManager mWindowManager;

    WindowManager.LayoutParams mParams;

    AnimationDrawable catRun ;
    AnimationDrawable catStand2 ;
    AnimationDrawable catStand3 ;
    Drawable catStand;
    Drawable catHide;
    AnimationDrawable catSleep;

    ImageView mIv;

    TextView mTv;

    public ServiceView(Context service) {
        super(service);

        catRun = (AnimationDrawable) service.getResources().getDrawable(R.drawable.cat_run);
        catStand2 = (AnimationDrawable) service.getResources().getDrawable(R.drawable.cat_stand2);
        catStand = service.getResources().getDrawable(R.drawable.cat_stand);
        catHide = service.getResources().getDrawable(R.drawable.cat_hide);
        catSleep = (AnimationDrawable)service.getResources().getDrawable(R.drawable.cat_sleep);
        catStand3 = (AnimationDrawable) service.getResources().getDrawable(R.drawable.cat_in_mid_au);



    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void show() {
        LayoutInflater inflater = LayoutInflater.from(getContext().getApplicationContext());

        rootView = inflater.inflate(R.layout.service_view,null);
        mIv = rootView.findViewById(R.id.iv);
        mTv = rootView.findViewById(R.id.tv_message);
        mIv.setImageDrawable(catStand);

        //set listener
        //voiceRecognizer.init(getContext(), mIv);


        setAllListener(rootView);
        setAllListener(mTv);
        setAllListener(mIv);




        initWindow();
        Log.i("ServiceView","--------Window inited---");

        mWindowManager.addView(rootView,mParams);


    }

    private void setAllListener(View view){

        view.setOnClickListener(new View.OnClickListener() {
            Handler handler = new Handler();
            Boolean isDbClick = false;
            Runnable r = null;
            @Override
            public void onClick(View view) {
                if (r == null ) {
                    final View innerView = view;
                    r = new Runnable() {
                        @Override
                        public void run() {
                            isDbClick = false;

                            if(getEventListener()!=null)getEventListener().onClick(innerView);
                        }
                    };
                }
                if (isDbClick) {
                    isDbClick = false;

                    if(getEventListener()!=null)getEventListener().onLongClick(view);
                    handler.removeCallbacks(r);
                } else {
                    isDbClick = true;
                    handler.postDelayed(r, 500);
                }
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(getEventListener()!=null)getEventListener().onLongClick(view);
                return false;
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(getEventListener()!= null)getEventListener().onTouch(view,motionEvent);
                return false;
            }
        });
    }

    @Override
    public void stand(MotionEvent motionEvent) {

//        Glide.clear(mIv);
//        Glide.with(getContext()).load(R.drawable.cat_sleep).asGif().into(mIv);
        mIv.setImageDrawable(catStand3);
        catStand3.start();
    }

    @Override
    public void move(MotionEvent motionEvent) {
//        Glide.clear(mIv);
//        Glide.with(getContext()).load(R.drawable.cat_walk).asGif().into(mIv);
        mIv.setImageDrawable(catRun);
        catRun.start();
    }

    @Override
    public void hide(MotionEvent motionEvent,int type) {
        mIv.setImageDrawable(catHide);
        // 0 left 1 right
        if(type == 0){
            rootView.setScaleX(1);
            mTv.setScaleX(1);
        }else{
            mTv.setScaleX(-1);
            rootView.setScaleX(-1);

        }
    }

    @Override
    public void listen() {
        mIv.setImageDrawable(catSleep);
        catSleep.start();
    }

    public View getRootView() {
        return rootView;
    }

    @Override
    public void initWindow(){

        mWindowManager = (WindowManager) getContext().getSystemService(getContext().WINDOW_SERVICE);
        mParams = new WindowManager.LayoutParams();

        mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        //mParams.
        mParams.format = PixelFormat.RGBA_8888;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        mParams.gravity =Gravity.LEFT|Gravity.TOP;
        mParams.x = 0;
        mParams.y = 0;

        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

    }

    @Override
    public void unShow() {
        mWindowManager.removeView(rootView);
    }

    public WindowManager getWindowManager() {
        return mWindowManager;
    }


    public WindowManager.LayoutParams getParams() {
        return mParams;
    }

    @Override
    public void showSendMessage(String text) {
        Toast t = new Toast(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.toast_send_message,null);
        TextView tv = view.findViewById(R.id.tv_message);
        tv.setText(text);
        t.setView(view);
        t.setDuration(Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER,0,0);

        t.show();




    }

    @Override
    public void showTextView(final String text){

        if(mTv.getVisibility() == View.VISIBLE){
            ObjectAnimator.ofFloat(mTv,"translationX",0,-1 * mIv.getWidth()).setDuration(777).start();
            ObjectAnimator.ofFloat(mTv,"translationY",0, mIv.getHeight()).setDuration(777).start();
            ObjectAnimator o = ObjectAnimator.ofFloat(mTv,"alpha",1,0);
                    o.setDuration(777)
                    .addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            mTv.setText(text);
                            ObjectAnimator.ofFloat(mTv,"translationX",-1 * mIv.getWidth(),0).setDuration(777).start();
                            ObjectAnimator.ofFloat(mTv,"translationY", mIv.getHeight(),0).setDuration(777).start();
                            ObjectAnimator.ofFloat(mTv,"alpha",0,1).setDuration(777).start();

                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
            o.start();

        } else {
            ObjectAnimator.ofFloat(mTv, "translationX", -1 * mIv.getWidth(), 0).setDuration(777).start();
            ObjectAnimator.ofFloat(mTv, "translationY", mIv.getHeight(), 0).setDuration(777).start();
            ObjectAnimator.ofFloat(mTv, "alpha", 0, 1).setDuration(777).start();
            mTv.setText(text);
            mTv.setVisibility(View.VISIBLE);
            ObjectAnimator o = ObjectAnimator.ofFloat(mTv, "alpha", 1, 0);
            o.setStartDelay(777);
            o.setDuration(2000);
            o.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    mTv.setVisibility(View.GONE);
                    mTv.setText("");
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            o.start();

        }
    }

    public ImageView getIv() {
        return mIv;
    }

    public TextView getTv(){
        return mTv;
    }
}
