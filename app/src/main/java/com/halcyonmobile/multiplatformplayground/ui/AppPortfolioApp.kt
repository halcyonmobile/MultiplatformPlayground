package com.halcyonmobile.multiplatformplayground.ui

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import com.halcyonmobile.multiplatformplayground.Actions
import com.halcyonmobile.multiplatformplayground.Destination
import com.halcyonmobile.multiplatformplayground.util.AmbientBackDispatcher
import com.halcyonmobile.multiplatformplayground.util.Navigator

@Composable
fun AppPortfolioApp(backDispatcher: OnBackPressedDispatcher) {
    @Suppress("RemoveExplicitTypeArguments")
    val navigator: Navigator<Destination> = rememberSavedInstanceState(
        saver = Navigator.saver<Destination>(backDispatcher)
    ) {
        Navigator(Destination.Main, backDispatcher)
    }
    val actions = remember(navigator) { Actions(navigator) }

    Providers(AmbientBackDispatcher provides backDispatcher) {
        Crossfade(navigator.current) { destination ->
            when (destination) {
                Destination.Main -> MainScreen(actions.openApplicationDetail)
                is Destination.ApplicationDetail -> ApplicationDetail(applicationId = destination.applicationId)
            }
        }
    }
}
