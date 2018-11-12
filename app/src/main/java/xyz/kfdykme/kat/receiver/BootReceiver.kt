package xyz.kfdykme.kat.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import xyz.kfdykme.kat.service.KatService

class BootReceiver:BroadcastReceiver{
    constructor()

    override fun onReceive(context: Context?, intent: Intent?) {

        intent?.let {
            if(it.action?.equals("android.intent.action.BOOT_COMPLETED")!!){
               context?.startService(Intent(context, KatService::class.java))
            }
        }
    }
}