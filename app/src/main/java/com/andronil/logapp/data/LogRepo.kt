package com.andronil.logapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LogRepo(private val preferenceOwner: PreferenceOwner) {
    private val logStore = hashMapOf<String,String?>()

    /**
     * persisting the log for future access
     * */
    suspend fun saveLog(key : String, value : String?){
        withContext(Dispatchers.IO){
            logStore[key] = value
            preferenceOwner.putLogName(key)
            preferenceOwner.putLogDetails(value)
            preferenceOwner.save()
        }
    }
}