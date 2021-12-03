package com.reactive.practice

import org.slf4j.LoggerFactory
import org.springframework.util.StopWatch
import org.springframework.web.client.RestTemplate
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class LoadTest {
    companion object {
        var counter: AtomicInteger = AtomicInteger(0)
    }
}

val logger = LoggerFactory.getLogger(LoadTest::class.java)

fun main() {
    val es = Executors.newFixedThreadPool(100)

    val rt = RestTemplate()
    val url = "http://localhost:8080/rest?idx={idx}" +
            ""

    val barrier = CyclicBarrier(101)

    for (i in 1..100) {
        es.submit {
            val idx = LoadTest.counter.addAndGet(1)
            barrier.await()

            logger.info("Thread: $idx")

            val sw = StopWatch()
            sw.start()

            val result = rt.getForObject(url, String::class.java, idx)
            logger.info("$result")

            sw.stop()
            logger.info("Elapsed: $idx -> ${sw.totalTimeSeconds}")

        }
    }

    barrier.await()
    val main = StopWatch()
    main.start()

    es.shutdown()
    es.awaitTermination(100, TimeUnit.SECONDS)

    main.stop()
    logger.info("Total: ${main.totalTimeSeconds}")
}
