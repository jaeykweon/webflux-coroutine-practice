package org.idd.webfluxcoroutinepractice.coroutine

import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ranges.shouldBeIn
import kotlinx.coroutines.async
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.withTimeoutOrNull
import org.idd.webfluxcoroutinepractice.printTestNameAndElapsedTime
import reactor.core.publisher.Mono
import kotlin.system.measureTimeMillis

@DisplayName("여러 api 호출 및 타임아웃")
class CallMultiApisWithTimeout: StringSpec({

    "어러 api 동시 호출 + 타임아웃 없는 경우" {
        measureTimeMillis {
            val api1 = async { call1000msApi() }
            val api2 =  async { call1500msApi()  }
            val api3 = async { call5000msApi() }

            val api1Response = api1.await().awaitSingleOrNull()
            val api2Response = api2.await().awaitSingleOrNull()
            val api3Response = api3.await().awaitSingleOrNull()

            println("api1 response: $api1Response")
            println("api2 response: $api2Response")
            println("api3 response: $api3Response")
        }.let {
            printTestNameAndElapsedTime(it)
            it shouldBeIn 5000L..5100L
        }
    }

    "어러 api 동시 호출 + 타임아웃이 있는 경우" {
        measureTimeMillis {
            val api1 = async { call1000msApi() }
            val api2 =  async { call1500msApi()  }
            val api3 = async {
                withTimeoutOrNull(2000) {
                    call5000msApi()
                } ?: Mono.just(2)
            }

            val api1Response = api1.await().awaitSingleOrNull()
            val api2Response = api2.await().awaitSingleOrNull()
            val api3Response = api3.await().awaitSingleOrNull()

            println("api1 response: $api1Response")
            println("api2 response: $api2Response")
            println("api3 response: $api3Response")
        }.let {
            printTestNameAndElapsedTime(it)
            it shouldBeIn 2000L..2100L
        }
    }
})