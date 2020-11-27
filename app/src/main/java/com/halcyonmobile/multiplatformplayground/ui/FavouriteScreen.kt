package com.halcyonmobile.multiplatformplayground.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.model.ui.ApplicationUiModel
import com.halcyonmobile.multiplatformplayground.viewmodel.FavouritesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun FavouriteScreen(onApplicationClicked: (ApplicationUiModel.App) -> Unit) {
    val viewModel = getViewModel<FavouritesViewModel>()
    val favourites by viewModel.favourites.collectAsState()

    Column {
        Text(
            text = stringResource(id = R.string.favourites),
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier.padding(16.dp)
        )
        Applications(items = favourites, onApplicationClicked = onApplicationClicked)
    }
}