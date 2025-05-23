package com.techadive.movie.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.techadive.common.R
import com.techadive.common.models.MovieCardData
import com.techadive.designsystem.components.LoadingView

@Composable
fun MovieCardList(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isError: Boolean = false,
    innerPadding: PaddingValues,
    listState: LazyGridState,
    movieCards: List<MovieCardData>,
    showDetails: (Int) -> Unit
) {
    val context = LocalContext.current

    LazyVerticalGrid(
        state = listState,
        columns = GridCells.Fixed(2),
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(innerPadding),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movieCards) { movie ->
            MovieCard(movie, showDetails)
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            if (isLoading && !isError) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingView(
                        modifier = Modifier.size(48.dp), // or use your preferred size
                        paddingValues = PaddingValues()
                    )
                }
            }
        }
    }

    if (isError) {
        Toast
            .makeText(context, R.string.something_went_wrong, Toast.LENGTH_SHORT)
            .show()

    }
}