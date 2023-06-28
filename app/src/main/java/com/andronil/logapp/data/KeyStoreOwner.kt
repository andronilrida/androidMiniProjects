package com.andronil.logapp.data

abstract class KeyStoreOwner {

    protected val kLogName by lazy { "k_log_name" }
    protected val kLogDetails by lazy { "k_log_details" }


    abstract fun putLogName(name: String?)

    abstract fun putLogDetails(details: String?)

    abstract fun getLogName(): String?

    abstract fun getLogDetail(): String?
}