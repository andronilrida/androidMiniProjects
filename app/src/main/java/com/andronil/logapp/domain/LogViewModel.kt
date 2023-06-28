package com.andronil.logapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andronil.logapp.data.LogModel
import com.andronil.logapp.data.LogRepo
import com.andronil.logapp.data.LogType
import com.andronil.logapp.data.LogValidation
import kotlinx.coroutines.launch

class LogViewModel(private val logRepo: LogRepo) : ViewModel() {

    private val _logLiveData = MutableLiveData<LogModel>()
    val logLiveData: LiveData<LogModel> = _logLiveData

    private val _logLoadedLiveData = MutableLiveData<LogModel>()
    val logLoadedLiveData: LiveData<LogModel> = _logLiveData

    private val _logValidationLivedata = MutableLiveData<LogValidation>()
    val logValidationLivedata: LiveData<LogValidation> = _logValidationLivedata

    private fun hasSucceedValidation(log : LogModel) = validate(log).validator.first == LogType.Success

    /**
     * First validates the input and then save
     * */
    fun saveLog(log: LogModel) {
        viewModelScope.launch {
            logRepo.saveLog(log.name, log.message)
            _logLiveData.value = log
        }
    }

    /**
     * validating before insertion
     * */
    fun validateLog(log: LogModel) {
        viewModelScope.launch {
            _logValidationLivedata.value = validate(log)
        }
    }

    /**
     * check for error in the logs
     * */
    private fun validate(log: LogModel) = LogValidation( when {
        log.isNameMissing() -> Pair(LogType.Key, "*Key Required")
        log.isMessageMissing() -> Pair(LogType.Value, "*Message Required")
        else -> Pair(LogType.Success, null)
    }, log)

    /**
     * request on load
     * */
    fun checkLogOnLoad(logName: String?, logDetail: String?) {
        viewModelScope.launch {
            logName?.let {
                LogModel(logName, logDetail).also {
                    if (hasSucceedValidation(it)) {
                        _logLoadedLiveData.value = it
                    }
                }
            }
        }
    }

}