package com.techadive.movie.ui.search.recentsearch

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.techadive.common.R
import com.techadive.common.models.Keyword
import com.techadive.designsystem.theme.Movies2cTheme
import com.techadive.movie.viewmodels.search.RecentSearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchQueryView(
    recentSearchViewModel: RecentSearchViewModel,
    showRecentSearches: () -> Unit,
    backClicked: () -> Unit,
    showResults: (String?) -> Unit,
) {
    val searchUIStateValues = recentSearchViewModel.recentSearchUIState.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        recentSearchViewModel.onEvent(
            RecentSearchViewModel.RecentSearchEvent.GetRecentSearchHistory
        )
    }
    AnimatedVisibility(
        visible = true,
        enter = slideInHorizontally(
            initialOffsetX = { fullWidth -> fullWidth }, // Slide in from right
            animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
        )
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .statusBarsPadding(),
            topBar = {
                Surface(
                    tonalElevation = 4.dp,
                    shadowElevation = 4.dp,
                    color = Color.Transparent
                ) {
                    TopAppBar(
                        title = {
                            SearchFieldView(
                                query = searchUIStateValues.query.orEmpty(),
                                back = {
                                    backClicked()
                                },
                                onValueChange = { query ->
                                    recentSearchViewModel.onEvent(
                                        RecentSearchViewModel.RecentSearchEvent.SearchKeyword(
                                            query
                                        )
                                    )
                                }
                            ) { query ->
                                showResults(query)
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Movies2cTheme.colors.background
                        )
                    )
                }
            },
            containerColor = Movies2cTheme.colors.background
        ) { innerPadding ->

            LazyColumn(
                modifier = Modifier.padding(
                    top = innerPadding.calculateTopPadding() + 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                if (searchUIStateValues.query.isNullOrEmpty() && searchUIStateValues.recentKeywords?.results?.isNotEmpty() == true) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            androidx.compose.material.Text(
                                modifier = Modifier.alignByBaseline(),
                                text = stringResource(R.string.recent),
                                style = Movies2cTheme.typography.h4,
                                color = Movies2cTheme.colors.onBackground
                            )

                            TextButton(
                                modifier = Modifier.alignByBaseline(),
                                onClick = {
                                    showRecentSearches()
                                }
                            ) {
                                androidx.compose.material.Text(
                                    text = stringResource(R.string.see_all),
                                    style = Movies2cTheme.typography.body3,
                                    color = Movies2cTheme.colors.primary,
                                )
                            }
                        }
                    }


                    items(searchUIStateValues.recentKeywords.results.take(5)) { recentSearch ->
                        RecentSearchItemView(recentSearch) {
                            showResults(recentSearch.name)
                        }
                    }
                }
                if (!searchUIStateValues.query.isNullOrEmpty() && searchUIStateValues.keywordsList != null) {
                    items(searchUIStateValues.keywordsList.results) { recentSearch ->
                        RecentSearchItemView(recentSearch) {
                            showResults(recentSearch.name)
                        }
                    }

                    item {
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(),
                            thickness = 0.5.dp,
                            color = Movies2cTheme.colors.onBackground
                        )
                    }

                    item {
                        TextButton(
                            enabled = searchUIStateValues.query?.isNotEmpty() == true,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                showResults(searchUIStateValues.query)
                            }
                        ) {
                            androidx.compose.material.Text(
                                text = stringResource(R.string.show_results),
                                style = Movies2cTheme.typography.label,
                                color = Movies2cTheme.colors.primary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecentSearchItemView(keyword: Keyword, showResults: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                showResults()
            },
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material.Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "",
            tint = Movies2cTheme.colors.onBackground,
        )

        Text(
            text = keyword.name,
            style = Movies2cTheme.typography.h6,
            color = Movies2cTheme.colors.onBackground,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
private fun SearchFieldView(
    query: String,
    back: () -> Unit,
    onValueChange: (String) -> Unit,
    showResults: (String) -> Unit,
) {

    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextField(
            modifier = Modifier.weight(1f),
            value = query,
            onValueChange = {
                onValueChange(it)
            },
            label = {
                Text(
                    text = stringResource(R.string.search),
                    style = Movies2cTheme.typography.body4
                )
            },
            shape = Movies2cTheme.shapes.medium,
            colors = TextFieldDefaults.colors(
                focusedTextColor = Movies2cTheme.colors.onBackground,
                unfocusedTextColor = Movies2cTheme.colors.onBackground,
                cursorColor = Movies2cTheme.colors.onBackground,
                errorLabelColor = Movies2cTheme.colors.error,
                focusedLabelColor = Movies2cTheme.colors.primary,
                unfocusedLabelColor = Movies2cTheme.colors.onSurface,
                errorIndicatorColor = Movies2cTheme.colors.error,
                focusedIndicatorColor = Movies2cTheme.colors.background,
                unfocusedIndicatorColor = Movies2cTheme.colors.background,
                focusedContainerColor = Movies2cTheme.colors.background,
                unfocusedContainerColor = Movies2cTheme.colors.background,
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "",
                    tint = Movies2cTheme.colors.onBackground,
                    modifier = Modifier.clickable {
                        back()
                    }
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "",
                    tint = Movies2cTheme.colors.primary,
                )
            },
            keyboardActions = KeyboardActions(
                onSearch = {
                    showResults(query)
                }
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        )
    }
}