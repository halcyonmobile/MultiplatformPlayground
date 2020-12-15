package com.halcyonmobile.multiplatformplayground.ui

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.lazy.LazyRowFor
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.model.ui.UploadApplicationUiModel
import com.halcyonmobile.multiplatformplayground.model.ui.UploadApplicationUiModelChangeListener
import com.halcyonmobile.multiplatformplayground.shared.util.ImageFile
import com.halcyonmobile.multiplatformplayground.shared.util.toImageFile
import com.halcyonmobile.multiplatformplayground.ui.theme.lightGray
import com.halcyonmobile.multiplatformplayground.util.registerForActivityResult
import com.halcyonmobile.multiplatformplayground.util.composables.BackBar
import com.halcyonmobile.multiplatformplayground.viewmodel.UploadApplicationViewModel
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.navigationBarsPadding

@Composable
fun UploadApplication(initialCategoryId: Long, upPress: () -> Unit) {
    val viewModel = remember(initialCategoryId) {
        UploadApplicationViewModel(initialCategoryId)
    }
    val uploadApplicationUiModel by viewModel.uploadApplicationUiModel.collectAsState(
        UploadApplicationUiModel(categoryId = initialCategoryId)
    )

    val getIcon = registerForGalleryResult(viewModel::onIconChanged)
    val getScreenshot = registerForGalleryResult(viewModel::onAddScreenshot)

    Scaffold(
        topBar = { BackBar(upPress = upPress) },
        bodyContent = {
            ScrollableColumn(contentPadding = PaddingValues(16.dp)) {
                Card(
                    modifier = Modifier.preferredSize(88.dp).align(Alignment.CenterHorizontally),
                    shape = CircleShape,
                    backgroundColor = lightGray
                ) {
                    Box(Modifier.clickable { getIcon.launchAsImageResult() }) {
                        if (uploadApplicationUiModel.icon == null) {
                            Image(
                                imageVector = vectorResource(id = R.drawable.ic_add_image),
                                colorFilter = ColorFilter.tint(Color.DarkGray),
                                modifier = Modifier.wrapContentSize().align(Alignment.Center)
                            )
                        } else {
                            CoilImage(
                                data = uploadApplicationUiModel.icon!!.uri,
                                modifier = Modifier.matchParentSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
                Screenshots(
                    screenshots = uploadApplicationUiModel.screenshots,
                    onAddScreenshot = { getScreenshot.launchAsImageResult() }
                )
                ApplicationDetails(uploadApplicationUiModel, viewModel)
            }
        }
    )
}

private fun ActivityResultLauncher<String>.launchAsImageResult() = launch("image/*")

@Composable
private fun registerForGalleryResult(callback: (ImageFile) -> Unit) =
    (AmbientContext.current as AppCompatActivity).contentResolver.let { contentResolver ->
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.toImageFile(contentResolver)?.let(callback)
        }
    }

@Composable
private fun Screenshots(screenshots: List<ImageFile>, onAddScreenshot: () -> Unit) {
    Column {
        Text(
            text = stringResource(id = R.string.screenshots),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(16.dp)
        )
        LazyRowFor(items = screenshots + null, modifier = Modifier.padding(8.dp)) {
            if (it != null) {
                CoilImage(
                    data = it.uri,
                    modifier = Modifier.preferredSize(88.dp).padding(8.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Card(
                    modifier = Modifier.preferredSize(88.dp).padding(8.dp),
                    shape = RectangleShape,
                    backgroundColor = lightGray,
                    border = BorderStroke(1.dp, Color.LightGray)
                ) {
                    Box(Modifier.clickable(onClick = onAddScreenshot)) {
                        Image(
                            imageVector = vectorResource(id = R.drawable.ic_add_image),
                            colorFilter = ColorFilter.tint(Color.DarkGray),
                            modifier = Modifier.wrapContentSize().align(Alignment.Center)
                        )
                    }

                }
            }
        }
    }
}

@Composable
private fun ApplicationDetails(
    uploadApplicationUiModel: UploadApplicationUiModel,
    changeListener: UploadApplicationUiModelChangeListener
) {
    Column(modifier = Modifier.padding(16.dp)) {
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
                    imageVector = vectorResource(id = R.drawable.ic_label),
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
                    imageVector = vectorResource(id = R.drawable.ic_developer),
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
                    imageVector = vectorResource(id = R.drawable.ic_description),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary)
                )
            })
        Row(modifier = Modifier.navigationBarsPadding().fillMaxWidth().padding(top = 8.dp)) {
            TextField(
                value = uploadApplicationUiModel.downloads,
                onValueChange = changeListener::onDownloadsChanged,
                modifier = Modifier.weight(1f).padding(end = 4.dp),
                placeholder = { Text(text = stringResource(id = R.string.downloads)) },
                leadingIcon = {
                    Image(
                        imageVector = vectorResource(id = R.drawable.ic_downloads),
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
                        imageVector = vectorResource(id = R.drawable.ic_rating),
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary)
                    )
                })
        }
    }
}

@Composable
fun UploadApplicationPreview() {
    Surface(Modifier.fillMaxSize()) {
        UploadApplication(initialCategoryId = 1, upPress = {})
    }
}