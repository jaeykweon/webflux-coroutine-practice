package org.idd.webfluxcoroutinepractice.coroutine

import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ranges.shouldBeIn
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.withTimeoutOrNull
import org.idd.webfluxcoroutinepractice.printTestNameAndElapsedTime
import kotlin.system.measureTimeMillis

@DisplayName("타임아웃")
class TimeOut: StringSpec({

    "타임아웃이 없는 경우" {
        measureTimeMillis {
            call5000msApi().awaitSingleOrNull()
        }.let {
            printTestNameAndElapsedTime(it)
            it shouldBeIn 5000L..5100L
        }
    }

    "타임아웃이 있는 경우" {
        measureTimeMillis {
            withTimeoutOrNull(3000) {
                call5000msApi().awaitSingleOrNull()
            }
        }.let {
            printTestNameAndElapsedTime(it)
            it shouldBeIn 3000L..3100L
        }
    }
})