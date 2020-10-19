package com.halcyonmobile.multiplatformplayground

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.halcyonmobile.multiplatformplayground.util.Navigator
import kotlinx.android.parcel.Parcelize

/**
 * Models the screens in the app and any arguments they require.
 */
sealed class Destination : Parcelable {
    @Parcelize
    object Main : Destination()

    @Immutable
    @Parcelize
    data class ApplicationDetail(val applicationId: Long) : Destination()
}

/**
 * Models the navigation actions in the app.
 */
class Actions(navigator: Navigator<Destination>) {

    val openApplicationDetail: (applicationId: Long) -> Unit = {
        navigator.navigate(Destination.ApplicationDetail(it))
    }

    val upPress: () -> Unit = {
        navigator.back()
    }
}