package xyz.kfdykme.kat.service;


import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;

import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;

import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import xyz.kfdykme.kat.MainActivity;
import xyz.kfdykme.kat.R;
import xyz.kfdykme.kat.receiver.RePositionReceiver;
import xyz.kfdykme.kat.service.model.ServiceModel;
import xyz.kfdykme.kat.service.presenter.IServicePresenter;
import xyz.kfdykme.kat.service.presenter.ServicePresenter;
import xyz.kfdykme.kat.service.view.ServiceView;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/4 07:57.
 * Last Edit on 2017/9/4 07:57
 * 修改备注：
 */


public class KatService extends Service {


    Context context;

    private ServiceView mServiceView;
    private ServicePresenter mServicePresenter;

    private Messenger mAcMessenger;

    private Handler handler;

    private Messenger mSeMessenger;

    public KatService() {
        context  =this;
    }

    public static final String TAG = "KatService";



    public IBinder onBind(Intent intent) {
       // return null;

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {

        showNitification();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        EventBus.getDefault().register(this);

        mServiceView = new ServiceView(getApplicationContext());
        mServicePresenter = new ServicePresenter(getApplicationContext());
        mServicePresenter.addView(mServiceView);
        mServicePresenter.setMode(new ServiceModel());
        mServicePresenter.attach();

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        registerReceiver(new RePositionReceiver(),filter);

    }



    private void showToast(String toast){
        Toast.makeText(context,toast,Toast.LENGTH_SHORT).show();
    }

    private void showNitification(){
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,new Intent(this, MainActivity.class),0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

                builder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Kat")
                .setContentText("   is running")
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                ;

        startForeground(0x111,builder.build());
    }




    @Override
    public void onDestroy() {

        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){

            EventBus.getDefault().unregister(this);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public  void getPostText(String text){
        // if(text.substring(0,3).equals("post"))
        showToast(text);
        mServicePresenter.mEventListener.unitCommunicate(text);
    }


    /**
     * @method onServiceEvent
     * @desc call this method when screen rotate
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onServiceEvent(ServiceEvent e){
        mServicePresenter.toRun(IServicePresenter.FLAG_RIGHT);
    }

    public static boolean isRunning(Context context){
        //TODO: check if katservice is running and change main_view
        ActivityManager am = (ActivityManager)(context.getSystemService(ACTIVITY_SERVICE));

        for(ActivityManager.RunningServiceInfo serviceInfo : am.getRunningServices(Integer.MAX_VALUE)){
            if("xyz.kfdykme.kat.service.KatService".equals(serviceInfo.service.getClassName()))
                return true;
        }

        return false;
    }



}

