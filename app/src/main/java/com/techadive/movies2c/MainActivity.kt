package com.techadive.movies2c

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.techadive.designsystem.theme.Movies2cTheme
import com.techadive.network.utils.ApiUtils
import com.techadive.settings.viewmodels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Movies2cTheme(
                appTheme = settingsViewModel.appTheme.collectAsState().value
            ) {
                MainNavHost(
                    ::shareUrl
                )
            }
        }
    }

    private fun shareUrl(movieTitle: String, posterPath: String?) {
        if (posterPath != null) {
            val url = ApiUtils.IMAGE_URL + posterPath

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = resources.getString(com.techadive.common.R.string.mime_type_text)
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    resources.getString(com.techadive.common.R.string.check_out_the_movie)
                )
                putExtra(Intent.EXTRA_TEXT, "$movieTitle \n${url}")
            }
            startActivity(
                Intent.createChooser(
                    shareIntent,
                    resources.getString(com.techadive.common.R.string.share_via)
                )
            )
        } else {
            showToast(com.techadive.common.R.string.error_missing_url)
        }
    }

    private fun showToast(messageResource: Int) {
        Toast.makeText(
            this,
            resources.getString(messageResource),
            Toast.LENGTH_SHORT
        ).show()
    }
}