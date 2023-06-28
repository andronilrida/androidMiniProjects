package com.andronil.logapp.data

data class LogModel(
    val name : String,
    val message : String?
){

    fun isNameMissing() = name.isBlank() || name.isEmpty()

    fun isMessageMissing() = message?.isBlank() == true || message?.isEmpty() == true
}


data class LogValidation(
    val validator : Pair<LogType, String?>,
    val logModel: LogModel
)

sealed interface LogType{
    object Key : LogType
    object Value : LogType
    object Success : LogType
}