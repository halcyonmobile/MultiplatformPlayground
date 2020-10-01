package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import kotlinx.coroutines.flow.emptyFlow

internal class GetFavouritesUseCase(private val applicationRepository: ApplicationRepository) {

    //    TODO
    operator fun invoke() = emptyFlow<List<Application>>()
}