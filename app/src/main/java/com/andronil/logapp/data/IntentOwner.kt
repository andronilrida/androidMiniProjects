package com.andronil.logapp.data

import android.content.Context
import android.content.Intent
import android.os.Bundle

object IntentOwner : KeyStoreOwner(){
    private var intent : Intent? = null
    private var isFromBundle: Boolean = false

    fun write(context: Context, dest: Class<*>): IntentOwner {
        intent = Intent(context, dest)
        return this
    }

    fun read(intent: Intent, hasExtra : Boolean = false): IntentOwner {
        IntentOwner.intent = intent
        isFromBundle = hasExtra
        return this
    }

    fun build() = intent

    override fun putLogName(name: String?){
        intent?.putExtra(kLogName, name)
    }

    override fun putLogDetails(details: String?){
        intent?.putExtra(kLogDetails, details)
    }

    override fun getLogName(): String? {
        return if (isFromBundle){
            intent?.extras?.getString(kLogName)
        }else{
            intent?.getStringExtra(kLogName)
        }
    }

    override fun getLogDetail(): String? {
        return if (isFromBundle){
            intent?.extras?.getString(kLogDetails)
        }else{
            intent?.getStringExtra(kLogDetails)
        }
    }
}