package com.tistory.oysu.aidl_server

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log


class MyService : Service() {

    companion object {
        private const val TAG = "Server app service"
    }

    var iMyAidlInterface: IMyAidlInterfaceImpl? = null

    override fun onCreate() {
        super.onCreate()
        iMyAidlInterface = IMyAidlInterfaceImpl
        Log.d(TAG, "onCreate")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return iMyAidlInterface
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

}