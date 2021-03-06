package com.halcyonmobile.multiplatformplayground.ui

import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.google.accompanist.coil.CoilImage
import com.google.accompanist.insets.navigationBarsPadding
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.shared.util.toImageFile
import com.halcyonmobile.multiplatformplayground.ui.shared.Screenshots
import com.halcyonmobile.multiplatformplayground.ui.theme.AppTheme
import com.halcyonmobile.multiplatformplayground.util.composables.BackBar
import com.halcyonmobile.multiplatformplayground.viewmodel.ApplicationDetailViewModel

@Composable
fun ApplicationDetail(applicationId: Long, upPress: () -> Unit) {
    val viewModel =
        remember(applicationId) { ApplicationDetailViewModel(applicationId) }
    val applicationWithDetail by viewModel.applicationDetailUiModel.collectAsState()
    val state by viewModel.state.collectAsState()
    val contentResolver = (LocalContext.current as AppCompatActivity).contentResolver

    Scaffold(
        topBar = { BackBar(upPress = upPress) },
        floatingActionButton = {
            val iconRes = if (applicationWithDetail?.favourite == true)
                R.drawable.ic_favourites else R.drawable.ic_favourites_empty

            if (state == ApplicationDetailViewModel.State.NORMAL) {
                FloatingActionButton(
                    onClick = viewModel::updateFavourite,
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = null
                    )
                }
            }
        }) {
        when (state) {
            ApplicationDetailViewModel.State.LOADING -> Box(
                Modifier.fillMaxSize(),
                Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            ApplicationDetailViewModel.State.NORMAL -> applicationWithDetail?.let {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Header(
                        imageUrl = it.icon,
                        name = it.name,
                        developer = it.developer
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = 16.dp)
                    ) {
                        Property(
                            name = stringResource(id = R.string.rating),
                            value = it.rating.toString(),
                            iconRes = R.drawable.ic_rating
                        )
                        Property(
                            name = stringResource(id = R.string.downloads),
                            value = it.downloads,
                            iconRes = R.drawable.ic_downloads
                        )
                    }
                    Description(it.description)
                    Screenshots(screenshots = it.screenshots.map { screenshot ->
                        screenshot.image.toUri().toImageFile(contentResolver)
                    })
                }
            }
            ApplicationDetailViewModel.State.ERROR -> Column(
                Modifier
                    .wrapContentSize(Alignment.Center)
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.general_error),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = AppTheme.typography.body1
                )
                Button(onClick = { viewModel.loadDetail() }) {
                    Text(text = stringResource(id = R.string.retry))
                }
            }
        }
    }
}

@Composable
fun Header(imageUrl: String, name: String, developer: String, category: String = "Update this") {
    // TODO update category
    Row {
        Surface(shape = RoundedCornerShape(8.dp)) {
            CoilImage(data = imageUrl, modifier = Modifier.size(80.dp), contentDescription = null)
        }
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = name, style = AppTheme.typography.h6)
            Text(text = developer, style = AppTheme.typography.body2)
            Text(
                text = category,
                style = AppTheme.typography.body2,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun Property(name: String, value: String, @DrawableRes iconRes: Int) {
    Row {
        Image(
            painter = painterResource(id = iconRes),
            colorFilter = ColorFilter.tint(AppTheme.colors.secondary),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 8.dp),
            contentDescription = null
        )
        Column {
            Text(text = value, style = AppTheme.typography.h6)
            Text(text = name, style = AppTheme.typography.caption)
        }
    }
}

@Composable
private fun Description(description: String) {
    val (showMore, updateShowMore) = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        Text(style = AppTheme.typography.h6, text = stringResource(id = R.string.description))
        Text(
            modifier = Modifier.padding(top = 8.dp),
            style = AppTheme.typography.body2,
            maxLines = if (showMore) Int.MAX_VALUE else 3,
            text = description
        )
        OutlinedButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            onClick = { updateShowMore(!showMore) }) {
            Text(stringResource(id = if (!showMore) R.string.show_more else R.string.show_less))
        }
        Spacer(modifier = Modifier.navigationBarsPadding())
    }
}

@Preview("description")
@Composable
fun DescriptionPreview() {
    Surface {
        Description(
            "Your next job is only one download away. \n" +
                    "From registration to employment, the RightStaff app helps you through the entire hiring process. We’ll guide you to a new medical job in just a few simple taps. All you will need to do is download the app, register your skills, fill in your availability, choose your area of work, update your location and you’re halfway to reciving your first shift."
        )
    }
}

@Preview("property")
@Composable
fun PropertyPreview() {
    Surface {
        Property("Rating", "5.0", R.drawable.ic_rating)
    }
}