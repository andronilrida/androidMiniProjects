package com.andronil.logapp.data

import android.content.Context

class PreferenceOwner(context: Context) : KeyStoreOwner() {

    private val preference = context.getSharedPreferences("AppPreference",0)

    private val editor = preference.edit()

    fun save(){
        editor.apply()
    }

    override fun putLogName(name: String?) {
        editor.putString(kLogName, name)
    }

    override fun putLogDetails(details: String?) {
        editor.putString(kLogDetails, details)
    }

    override fun getLogName(): String? {
        return preference.getString(kLogName, null)
    }

    override fun getLogDetail(): String? {
        return preference.getString(kLogDetails, null)
    }

}