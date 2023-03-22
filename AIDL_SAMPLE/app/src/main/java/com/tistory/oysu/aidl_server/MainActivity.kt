package com.tistory.oysu.aidl_server

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.widget.CompoundButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

const val KEY_SERVICE_STATE = "service_state_boolean"

class MainActivity : Activity(), CompoundButton.OnCheckedChangeListener {

    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get SharedPreference
        pref = PreferenceManager.getDefaultSharedPreferences(this)

        btnBroadcast.setOnClickListener {
            // It will broadcast current state to Clients after 10 seconds.
            Handler().postDelayed({
                val text = etCode.text
                if (text.isNullOrEmpty()) {
                    Toast.makeText(this@MainActivity, "Text is null!", Toast.LENGTH_SHORT).show()
                } else {
                    IMyAidlInterfaceImpl.broadcastToCurrentStateToClients(text.toString().toInt())
                }
            }, 10 * 1000)
        }

        MyData.boolState = getServiceState()

        swSwitch.isChecked = getServiceState()
        swSwitch.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        saveServiceState(isChecked)
    }

    private fun saveServiceState(state: Boolean) {
        MyData.boolState = state
        pref.edit().putBoolean(KEY_SERVICE_STATE, state).apply()
    }

    private fun getServiceState(): Boolean {
        return pref.getBoolean(KEY_SERVICE_STATE, false)
    }
}
