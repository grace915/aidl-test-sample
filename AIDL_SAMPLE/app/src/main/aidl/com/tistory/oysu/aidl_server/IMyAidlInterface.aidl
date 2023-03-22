// IMyAidlInterface.aidl
package com.tistory.oysu.aidl_server;

import com.tistory.oysu.aidl_server.IServiceStateCallback;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    boolean isAvailable();
    boolean registerCallback(IServiceStateCallback callback);
    boolean unregisterCallback(IServiceStateCallback callback);
}