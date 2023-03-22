// IServiceStateCallback.aidl
package com.tistory.oysu.aidl_server;

// Declare any non-default types here with import statements

interface IServiceStateCallback {
    void onServiceStateChangedWithCode(int statue);
}