package com.halcyonmobile.multiplatformplayground.util

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onDispose
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import androidx.compose.ui.platform.AmbientContext
import java.util.UUID

// See https://issuetracker.google.com/issues/172690553
@Composable
fun <I, O> registerForActivityResult(
    contract: ActivityResultContract<I, O>,
    onResult: (O) -> Unit
): ActivityResultLauncher<I> {
    // First, find the ActivityResultRegistry by casting the Context
    // (which is actually a ComponentActivity) to ActivityResultRegistryOwner
    val owner = AmbientContext.current as ActivityResultRegistryOwner
    val activityResultRegistry = owner.activityResultRegistry

    // Keep track of the current onResult listener
    val currentOnResult = rememberUpdatedState(onResult)

    // It doesn't really matter what the key is, just that it is unique
    // and consistent across configuration changes
    val key = rememberSavedInstanceState { UUID.randomUUID().toString() }

    // TODO a working layer of indirection would be great
    val realLauncher = remember<ActivityResultLauncher<I>> {
        activityResultRegistry.register(key, contract) {
            currentOnResult.value(it)
        }
    }

    onDispose {
        realLauncher.unregister()
    }

    return realLauncher
}
