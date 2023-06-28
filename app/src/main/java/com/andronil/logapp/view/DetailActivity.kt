package com.andronil.logapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andronil.logapp.data.IntentOwner
import com.andronil.logapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private var binding: ActivityDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        readFromIntent(intent)
    }

    private fun readFromIntent(intent: Intent?) {
        intent?.let {
            updateLogs(
                IntentOwner.read(it, true).getLogName(),
                IntentOwner.read(it, true).getLogDetail()
            )
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        readFromIntent(intent)
    }

    private fun updateLogs(key: String?, value: String?) {
        // bind logs in view
        binding?.tvKey?.text = "Event name : $key"
        binding?.tvValue?.text = "Event details : $value"
    }
}