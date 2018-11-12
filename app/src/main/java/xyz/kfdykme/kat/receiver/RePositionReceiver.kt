package xyz.kfdykme.kat.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import org.greenrobot.eventbus.EventBus
import xyz.kfdykme.kat.service.KatService
import xyz.kfdykme.kat.service.ServiceEvent

class RePositionReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        EventBus.getDefault().post(ServiceEvent())
    }

}