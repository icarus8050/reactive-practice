package com.reactive.practice.future

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask

val logger: Logger = LoggerFactory.getLogger(FutureEx::class.java)

class FutureEx

fun main() {
    val es = Executors.newCachedThreadPool()

    val f = object : FutureTask<String>(
        Callable {
            Thread.sleep(2000)
            logger.info("Async")
            "Hello"
        }
    ) {
        override fun done() {
            println(get())
        }
    }

    /*val f: Future<String> = es.submit<String> {
        Thread.sleep(2000)
        logger.info("Async")
        return@submit "Hello"
    }*/

    es.execute(f)
    logger.info("Exit")
    es.shutdown()
}
