package com.halcyonmobile.multiplatformplayground.ui

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.model.ui.UploadApplicationUiModel
import com.halcyonmobile.multiplatformplayground.model.ui.UploadApplicationUiModelChangeListener
import com.halcyonmobile.multiplatformplayground.ui.theme.lightGray
import com.halcyonmobile.multiplatformplayground.viewmodel.UploadApplicationViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun UploadApplication(initialCategoryId: Long, upPress: () -> Unit) {
    val viewModel = getViewModel<UploadApplicationViewModel> { parametersOf(initialCategoryId) }
    val uploadApplicationUiModel by viewModel.uploadApplicationUiModel.collectAsState(
        UploadApplicationUiModel(categoryId = initialCategoryId)
    )
    val category by viewModel.category.collectAsState(null)

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
            ScrollableColumn(modifier = Modifier.padding(16.dp)) {
                Card(
                    modifier = Modifier.preferredSize(88.dp).align(Alignment.CenterHorizontally),
                    shape = CircleShape,
                    backgroundColor = lightGray
                ) {
                    Image(
                        asset = vectorResource(id = R.drawable.ic_add_image),
                        colorFilter = ColorFilter.tint(Color.DarkGray),
                        modifier = Modifier.wrapContentSize()
                    )
                }
                Screenshots(screenshots = emptyList()) // TODO update screenshot list
                ApplicationDetails(uploadApplicationUiModel, viewModel)
            }
        }
    )
}

@Composable
private fun Screenshots(screenshots: List<Uri>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.screenshots),
            style = MaterialTheme.typography.h6
        )
        Row(modifier = Modifier.padding(top = 16.dp)) {
            Card(
                modifier = Modifier.preferredSize(88.dp),
                shape = RectangleShape,
                backgroundColor = lightGray,
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                Image(
                    asset = vectorResource(id = R.drawable.ic_add_image),
                    colorFilter = ColorFilter.tint(Color.DarkGray),
                    modifier = Modifier.wrapContentSize()
                )
            }
        }
    }
}

@Composable
private fun ApplicationDetails(
    uploadApplicationUiModel: UploadApplicationUiModel,
    changeListener: UploadApplicationUiModelChangeListener
) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Text(
            text = stringResource(id = R.string.application_details),
            style = MaterialTheme.typography.h6
        )
        // TODO add category
        TextField(
            value = uploadApplicationUiModel.name,
            onValueChange = changeListener::onNameChanged,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            placeholder = { Text(text = stringResource(id = R.string.application_name)) },
            leadingIcon = {
                Image(
                    asset = vectorResource(id = R.drawable.ic_label),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary)
                )
            })
        TextField(
            value = uploadApplicationUiModel.developer,
            onValueChange = changeListener::onDeveloperChanged,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            placeholder = { Text(text = stringResource(id = R.string.developer)) },
            leadingIcon = {
                Image(
                    asset = vectorResource(id = R.drawable.ic_developer),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary)
                )
            })
        TextField(
            value = uploadApplicationUiModel.description,
            onValueChange = changeListener::onDescriptionChanged,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            placeholder = { Text(text = stringResource(id = R.string.description)) },
            leadingIcon = {
                Image(
                    asset = vectorResource(id = R.drawable.ic_description),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary)
                )
            })
        Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            TextField(
                value = uploadApplicationUiModel.downloads,
                onValueChange = changeListener::onDownloadsChanged,
                modifier = Modifier.weight(1f).padding(end = 4.dp),
                placeholder = { Text(text = stringResource(id = R.string.downloads)) },
                leadingIcon = {
                    Image(
                        asset = vectorResource(id = R.drawable.ic_downloads),
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary)
                    )
                })
            TextField(
                value = uploadApplicationUiModel.rating?.toString() ?: "",
                onValueChange = changeListener::onRatingChanged,
                modifier = Modifier.weight(1f).padding(start = 4.dp),
                placeholder = { Text(text = stringResource(id = R.string.rating)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = {
                    Image(
                        asset = vectorResource(id = R.drawable.ic_rating),
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary)
                    )
                })
        }
    }
}

@Preview("UploadApplication")
@Composable
fun UploadApplicationPreview() {
    Surface(Modifier.fillMaxSize()) {
        UploadApplication(initialCategoryId = 1, upPress = {})
    }
}