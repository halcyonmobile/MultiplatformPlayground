package com.halcyonmobile.multiplatformplayground.util

import io.ktor.application.*
import io.ktor.util.pipeline.*
import com.halcyonmobile.multiplatformplayground.shared.util.PAGE
import com.halcyonmobile.multiplatformplayground.shared.util.PER_PAGE

fun PipelineContext<Unit, ApplicationCall>.requirePage() =
    call.request.queryParameters[PAGE]?.toInt()
        ?: throw IllegalStateException("$PAGE parameter is required for this request")

fun PipelineContext<Unit, ApplicationCall>.requirePerPage() =
    call.request.queryParameters[PER_PAGE]?.toInt()
        ?: throw IllegalStateException("$PER_PAGE parameter is required for this request")