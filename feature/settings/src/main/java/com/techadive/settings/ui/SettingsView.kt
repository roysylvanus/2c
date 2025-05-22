package com.techadive.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.techadive.data.stores.settings.AppTheme
import com.techadive.designsystem.theme.Movies2cTheme

@Composable
fun SettingsView(
    padding: PaddingValues,
    appThemeState: State<AppTheme>,
    updateTheme: (AppTheme) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = padding.calculateTopPadding() + 16.dp,
                bottom = padding.calculateBottomPadding()
            )
            .padding(horizontal = 16.dp),
    ) {
        val currentTheme = appThemeState.value

        Text(
            text = stringResource(com.techadive.common.R.string.app_theme),
            style = Movies2cTheme.typography.h3,
            color = Movies2cTheme.colors.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(com.techadive.common.R.string.select_app_theme),
            style = Movies2cTheme.typography.h6,
            color = Movies2cTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))

        AppThemSettingsOption(
            com.techadive.common.R.string.auto,
            currentTheme, AppTheme.MODE_AUTO, updateTheme
        )

        Spacer(modifier = Modifier.height(24.dp))

        AppThemSettingsOption(
            com.techadive.common.R.string.light,
            currentTheme, AppTheme.MODE_LIGHT, updateTheme
        )

        Spacer(modifier = Modifier.height(24.dp))

        AppThemSettingsOption(
            com.techadive.common.R.string.dark,
            currentTheme, AppTheme.MODE_DARK, updateTheme
        )
    }
}

@Composable
private fun AppThemSettingsOption(
    titleResource: Int,
    currentTheme: AppTheme,
    appTheme: AppTheme,
    updateTheme: (AppTheme) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = appTheme == currentTheme,
            onClick = {
                updateTheme(appTheme)
            },
            colors = RadioButtonDefaults.colors(
                selectedColor = Movies2cTheme.colors.primary,
                unselectedColor = Movies2cTheme.colors.onBackground
            )
        )

        Text(
            text = stringResource(titleResource),
            style = Movies2cTheme.typography.body3,
            color = Movies2cTheme.colors.onBackground
        )
    }
}