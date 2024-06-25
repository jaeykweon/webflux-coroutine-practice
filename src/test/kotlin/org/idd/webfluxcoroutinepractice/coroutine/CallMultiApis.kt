package org.idd.webfluxcoroutinepractice.coroutine

import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ranges.shouldBeIn
import kotlinx.coroutines.async
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.idd.webfluxcoroutinepractice.printTestNameAndElapsedTime
import kotlin.system.measureTimeMillis

@DisplayName("여러 api 호출")
class CallMultiApis: StringSpec({

    "여러 api 순차 호출" {
        measureTimeMillis {
            val api1Response = call1000msApi().awaitSingleOrNull()
            val api2Response = call1500msApi().awaitSingleOrNull()
            println("api1 response: $api1Response")
            println("api2 response: $api2Response")
        }.let {
            printTestNameAndElapsedTime(it)
            it shouldBeIn 2500L..2700L
        }
    }

    "여러 api 동시 호출" {
        measureTimeMillis {
            val api1 = async { call1000msApi() }
            val api2 =  async { call1500msApi()  }

            val api1Response = api1.await().awaitSingleOrNull()
            val api2Response = api2.await().awaitSingleOrNull()

            println("api1 response: $api1Response")
            println("api2 response: $api2Response")
        }.let {
            printTestNameAndElapsedTime(it)
            it shouldBeIn 1500L..1600L
        }
    }
})