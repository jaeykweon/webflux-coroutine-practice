package org.idd.webfluxcoroutinepractice

import io.kotest.core.test.TestScope

fun TestScope.printTestNameAndElapsedTime(time: Long) {
    println(this.testCase.name.testName + ": " + time + "ms")
}