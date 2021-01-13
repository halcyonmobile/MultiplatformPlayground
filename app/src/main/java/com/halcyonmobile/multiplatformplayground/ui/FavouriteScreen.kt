package com.halcyonmobile.multiplatformplayground.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.model.ui.ApplicationUiModel
import com.halcyonmobile.multiplatformplayground.ui.theme.AppTheme
import com.halcyonmobile.multiplatformplayground.viewmodel.FavouritesViewModel
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@Composable
fun FavouriteScreen(onApplicationClicked: (ApplicationUiModel.App) -> Unit) {
    val viewModel = remember {
        FavouritesViewModel()
    }
    val favourites by viewModel.favourites.collectAsState()
    val state by viewModel.state.collectAsState()

    Column(Modifier.fillMaxSize()) {
        Text(
            text = stringResource(id = R.string.favourites),
            style = AppTheme.typography.h4,
            modifier = Modifier.statusBarsPadding().padding(16.dp)
        )
        when (state) {
            FavouritesViewModel.State.LOADING -> Box(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            FavouritesViewModel.State.NORMAL ->
                Applications(
                    items = favourites,
                    onApplicationClicked = onApplicationClicked
                )
            // TODO extract error to separate composable
            FavouritesViewModel.State.ERROR -> Column(
                modifier = Modifier.fillMaxWidth().weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.general_error),
                    style = AppTheme.typography.body1
                )
                Button(
                    onClick = { viewModel.loadFavourites() },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(stringResource(id = R.string.retry))
                }
            }
            FavouritesViewModel.State.EMPTY -> Box(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.favourites_empty_message),
                    modifier = Modifier.align(Alignment.Center),
                    style = AppTheme.typography.h6,
                    textAlign = TextAlign.Center
                )

            }
        }
    }
}