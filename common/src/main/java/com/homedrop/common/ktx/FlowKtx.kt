package com.homedrop.common.ktx

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun tickerFlow(start: Long, end: Long): Flow<Long> {
    return flow {
        for (i in start downTo end) {
            emit(i)
            delay(1000L)
        }
    }
}