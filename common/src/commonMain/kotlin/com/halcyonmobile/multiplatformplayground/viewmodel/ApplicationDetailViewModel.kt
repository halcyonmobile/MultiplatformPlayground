package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.model.ApplicationWithDetail
import com.halcyonmobile.multiplatformplayground.shared.CoroutineViewModel
import com.halcyonmobile.multiplatformplayground.shared.observer.Observable
import com.halcyonmobile.multiplatformplayground.shared.observer.observableOf
import com.halcyonmobile.multiplatformplayground.shared.observer.observe
import com.halcyonmobile.multiplatformplayground.usecase.GetApplicationUseCase
import com.halcyonmobile.multiplatformplayground.usecase.UpdateFavouriteUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ApplicationDetailViewModel internal constructor(
    private val getApplication: GetApplicationUseCase,
    private val updateFavourite: UpdateFavouriteUseCase,
    applicationId: Long
) : CoroutineViewModel() {

    val applicationWithDetail = Observable<ApplicationWithDetail>()
    val descLines = Observable<Int>()
    //    TODO solve resources
//    val toggleButtonText = Obsvable<Int>(R.string.desc_show_more)
    val backdrop = observableOf("http://backdrops.io/images/screens/screen6.jpg")
    val isFavourite: Boolean get() = applicationWithDetail.value?.application?.favourite ?: false

    init {
        descLines.observe { desc ->
            // todo handle resource
            desc?.let {
                //                toggleButtonText.set(if (it == LINES_COLLAPSED) R.string.desc_show_more else R.string.desc_show_less)
            }
        }

        coroutineScope.launch {
            getApplication(applicationId).catch {
                // todo handle error
            }.collect {
                applicationWithDetail.value = it
            }
        }
    }

    fun setFavourite() {
        applicationWithDetail.value?.application?.let {
            coroutineScope.launch {
                updateFavourite(
                    it.id,
                    it.favourite
                )
            }
        }
    }
}