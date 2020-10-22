package com.halcyonmobile.multiplatformplayground.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.viewmodel.ApplicationDetailViewModel
import dev.chrisbanes.accompanist.coil.CoilImage
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ApplicationDetail(applicationId: Long, upPress: () -> Unit) {
    val viewModel = getViewModel<ApplicationDetailViewModel> { parametersOf(applicationId) }
    val applicationWithDetail by viewModel.applicationDetailUiModel.collectAsState(null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    Icon(
                        asset = vectorResource(id = R.drawable.ic_back),
                        modifier = Modifier.clickable(onClick = upPress)
                    )
                },
            )
        },
        bodyContent = {
            applicationWithDetail?.let {
                ScrollableColumn(modifier = Modifier.padding(16.dp)) {
                    Header(
                        imageUrl = it.icon,
                        name = it.name,
                        developer = it.developer
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = 16.dp)
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
                    // TODO add screenshots
                }
            }
        }, floatingActionButton = {
            val iconRes = if (applicationWithDetail?.favourite == true)
                R.drawable.ic_favourites else R.drawable.ic_favourites_empty
            FloatingActionButton(
                onClick = viewModel::updateFavourite,
                icon = { Icon(asset = vectorResource(id = iconRes)) },
                modifier = Modifier.padding(16.dp)
            )
        })
}

@Composable
fun Header(imageUrl: String, name: String, developer: String, category: String = "Update this") {
    Row {
        Surface(shape = RoundedCornerShape(8.dp)) {
            CoilImage(data = imageUrl, modifier = Modifier.size(80.dp))
        }
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = name, style = MaterialTheme.typography.h6)
            Text(text = developer, style = MaterialTheme.typography.caption)
            Text(
                text = category,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun Property(name: String, value: String, @DrawableRes iconRes: Int) {
    Row {
        Image(
            asset = vectorResource(id = iconRes),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary),
            modifier = Modifier.align(Alignment.CenterVertically).padding(end = 8.dp)
        )
        Column {
            Text(text = value, style = MaterialTheme.typography.h5)
            Text(text = name, style = MaterialTheme.typography.caption)
        }
    }
}

@Composable
private fun Description(description: String) {
    val (showMore, updateShowMore) = remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(top = 16.dp).fillMaxWidth()) {
        Text(style = MaterialTheme.typography.h6, text = stringResource(id = R.string.description))
        Text(
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.caption,
            maxLines = if (showMore) Int.MAX_VALUE else 3,
            text = description
        )
        OutlinedButton(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp),
            onClick = { updateShowMore(!showMore) }) {
            Text(stringResource(id = if (!showMore) R.string.show_more else R.string.show_less))
        }
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