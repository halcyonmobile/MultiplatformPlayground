package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.shared.CoroutineViewModel
import com.halcyonmobile.multiplatformplayground.shared.observer.Observable
import com.halcyonmobile.multiplatformplayground.shared.observer.observableOf
import com.halcyonmobile.multiplatformplayground.shared.observer.observe
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ApplicationDetailViewModel internal constructor(
    private val applicationRepository: ApplicationRepository,
    applicationId: Long
) : CoroutineViewModel() {

    val application = Observable<Application>()
    val descLines = Observable<Int>()
    //    TODO solve resources
//    val toggleButtonText = Obsvable<Int>(R.string.desc_show_more)
    val backdrop = observableOf("http://backdrops.io/images/screens/screen6.jpg")
    val isFavourite: Boolean get() = application.value?.favourite ?: false

    init {
        descLines.observe { desc ->
            // todo handle resource
            desc?.let {
                //                toggleButtonText.set(if (it == LINES_COLLAPSED) R.string.desc_show_more else R.string.desc_show_less)
            }
        }

        coroutineScope.launch {
            // todo handle error, move to use-case
            applicationRepository.getById(applicationId).let {
                application.value = it
                applicationRepository.getDetailById(it.id).collect { applicationWithDetail ->
                    application.value = applicationWithDetail
                }
            }
        }
    }

    fun setFavourite() {
        val application = application.value ?: return
        coroutineScope.launch {
            applicationRepository.updateFavourites(application.id, !application.favourite)
        }
    }
}