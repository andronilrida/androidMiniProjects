package com.andronil.logapp.view

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import com.andronil.logapp.R
import com.andronil.logapp.data.LogModel
import com.andronil.logapp.data.LogRepo
import com.andronil.logapp.data.LogType
import com.andronil.logapp.data.IntentOwner
import com.andronil.logapp.domain.LogViewModel
import com.andronil.logapp.data.PreferenceOwner
import com.andronil.logapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var notificationReqCode: Int = 0
    private val logDomain: LogViewModel by lazy {
        LogViewModel(LogRepo(PreferenceOwner(this)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view interaction
        val binding = ActivityMainBinding.inflate(layoutInflater).let { binding ->
            setContentView(binding.root)
            binding.btnSave.setOnClickListener {
                handleSaveClick(binding.etKey.text.toString(), binding.etValue.text.toString())
            }
            binding
        }
        // event - log CURD operation
        logDomain.logLiveData.observe(this) {
            onLogSaved(this, it)
        }
        // event - log input validation
        logDomain.logValidationLivedata.observe(this) {
            val error = it.validator.second
            when (it.validator.first) {
                LogType.Key -> {
                    binding.etKey.error = error
                    showToast(this, "Key is missing")
                }

                LogType.Value -> {
                    binding.etValue.error = error
                    showToast(this, "Message is missing")
                }

                LogType.Success -> logDomain.saveLog(it.logModel)
            }
        }
    }

    /**
     * on click save first validate the log input*/
    private fun handleSaveClick(name: String, value: String) {
        logDomain.validateLog(LogModel(name, value))
    }

    /**
     * show notification on successful log saved
     * */
    private fun onLogSaved(context: Context, log: LogModel) {
        showToast(context, "saved successfully")
        showNotification(context, log)
    }

    private fun showNotification(context: Context, log: LogModel) {

        createNotificationChannel(
            context,
            "log"
        )
            NotificationManagerCompat.from(context).notify(0, buildNotification(context,
            log.name,
            log.message,
            R.drawable.ic_launcher_background,
            PendingIntent.getActivity(context,
                notificationReqCode,
                IntentOwner.write(context, DetailActivity::class.java).also {
                    it.putLogName(log.name)
                    it.putLogDetails(log.message)
                }.build() , PendingIntent.FLAG_UPDATE_CURRENT))
        )

    }
}