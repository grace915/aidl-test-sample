package com.tistory.oysu.aidl_sample_client;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tistory.oysu.aidl_server.IMyAidlInterface;
import com.tistory.oysu.aidl_server.IServiceStateCallback;

public class MainActivity extends Activity {

    private static String TAG = "RemoteMainActivity";

    private TextView tvState, tvStateFromCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRemoteService!=null) {
                    try {
                        tvState.setText(String.valueOf(mRemoteService.isAvailable()));

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Service not connected..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvState = findViewById(R.id.tvState);
        tvStateFromCallback = findViewById(R.id.tvStateFromCallback);

    }

    @Override
    protected void onResume() {
        super.onResume();
        connectService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        disconnectService();
    }

    private void connectService() {
        Intent i = new Intent();
        i.setAction("oysu.server.service");
        i.setPackage("com.tistory.oysu.aidl_server");
        bindService(i, mConnection, BIND_AUTO_CREATE);
    }

    private void disconnectService(){
        unRegisterCallback();
        unbindService(mConnection);
    }

    private void registerCallback() {
        if(mRemoteService!=null){
            try {
                mRemoteService.registerCallback(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void unRegisterCallback(){
        if(mRemoteService!=null){
            try {
                mRemoteService.unregisterCallback(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private IMyAidlInterface mRemoteService;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            mRemoteService = IMyAidlInterface.Stub.asInterface(service);
            registerCallback();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
            mRemoteService = null;
        }
    };

    private IServiceStateCallback mCallback = new IServiceStateCallback.Stub() {
        @Override
        public void onServiceStateChangedWithCode(int statue) {
            tvStateFromCallback.setText(String.valueOf(statue));
        }
    };
}
