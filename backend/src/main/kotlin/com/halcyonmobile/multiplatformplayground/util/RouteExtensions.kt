package com.halcyonmobile.multiplatformplayground.util

import com.halcyonmobile.multiplatformplayground.shared.util.PAGE
import com.halcyonmobile.multiplatformplayground.shared.util.PER_PAGE
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.pipeline.PipelineContext

fun PipelineContext<Unit, ApplicationCall>.requirePage() =
    call.request.queryParameters[PAGE]?.toInt()
        ?: throw IllegalStateException("$PAGE parameter is required for this request")

fun PipelineContext<Unit, ApplicationCall>.requirePerPage() =
    call.request.queryParameters[PER_PAGE]?.toInt()
        ?: throw IllegalStateException("$PER_PAGE parameter is required for this request")