package org.idd.webfluxcoroutinepractice.coroutine

import kotlinx.coroutines.delay
import reactor.core.publisher.Mono

suspend fun call1000msApi(): Mono<Int> {
    println("call1000msApi started")
    delay(1000)
    return Mono.just(1)
}

suspend fun call1500msApi(): Mono<Int> {
    println("call1500msApi started")
    delay(1500)
    return Mono.just(2)
}

suspend fun call5000msApi(): Mono<Int> {
    println("call5000msApi started")
    delay(5000)
    return Mono.just(5)
}