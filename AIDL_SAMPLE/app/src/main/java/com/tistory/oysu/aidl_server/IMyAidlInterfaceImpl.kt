package com.tistory.oysu.aidl_server

import android.os.RemoteCallbackList
import android.util.Log


/*   IMyAidlInterfaceImpl Created on: 2019-09-09
     Author: Infobank, Yuseok Oh[iroiro]      */

object IMyAidlInterfaceImpl : IMyAidlInterface.Stub() {

    private const val TAG = "TAG"
    private val callbacks = RemoteCallbackList<IServiceStateCallback>()

    override fun isAvailable(): Boolean {
        Log.d(TAG, "isAvailable requested.")
        return MyData.boolState
    }

    override fun registerCallback(callback: IServiceStateCallback?): Boolean {
        val ret = callbacks.register(callback)
        Log.d(TAG, "registerCallback: $ret")
        return ret
    }

    override fun unregisterCallback(callback: IServiceStateCallback?): Boolean {
        val ret = callbacks.unregister(callback)
        Log.d(TAG, "unregisterCallback: $ret")
        return ret
    }

    fun broadcastToCurrentStateToClients(state: Int) {
        val n = callbacks.beginBroadcast()
        Log.d(TAG, "broadcast size:$n")

        for (i in 0 until n) {
            callbacks.getBroadcastItem(i)?.onServiceStateChangedWithCode(state)
        }
        callbacks.finishBroadcast()
    }
}