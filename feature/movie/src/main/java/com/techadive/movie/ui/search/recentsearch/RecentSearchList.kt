package com.techadive.movie.ui.search.recentsearch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.techadive.designsystem.components.ToolbarView
import com.techadive.designsystem.theme.Movies2cTheme
import com.techadive.movie.viewmodels.search.RecentSearchViewModel

@Composable
fun RecentSearchList(
    navController: NavController,
    showResults: (String) -> Unit,
) {
    val recentSearchViewModel: RecentSearchViewModel = hiltViewModel()

    val recentSearches =
        recentSearchViewModel.recentSearchUIState.collectAsState().value.recentKeywords?.results
    Scaffold(
        topBar = {
            ToolbarView(
                title = stringResource(com.techadive.common.R.string.recent),
                startIconDescription = stringResource(com.techadive.common.R.string.back),
                startIconAction = {
                    navController.navigateUp()
                },
                endContent = {
                    TextButton(onClick = {
                        recentSearchViewModel.onEvent(RecentSearchViewModel.RecentSearchEvent.DeleteAllRecentSearchHistory)
                    }) {
                        Text(
                            text = stringResource(com.techadive.common.R.string.clear),
                            style = Movies2cTheme.typography.label,
                            color = Movies2cTheme.colors.onBackground
                        )
                    }
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding(),
        containerColor = Movies2cTheme.colors.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(
                top = padding.calculateTopPadding() + 16.dp,
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if (recentSearches != null) {
                items(recentSearches) { query ->
                    RecentSearchItemView(query) {
                        showResults(query.name)
                    }
                }
            }
        }
    }
}