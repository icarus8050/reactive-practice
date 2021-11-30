package com.reactive.practice.future

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors
import java.util.concurrent.Future

val logger: Logger = LoggerFactory.getLogger(FutureEx::class.java)

class FutureEx

fun main() {
    val es = Executors.newCachedThreadPool()

    val f: Future<String> = es.submit<String> {
        Thread.sleep(2000)
        logger.info("Async")
        return@submit "Hello"
    }
    logger.info(f.isDone.toString())
    logger.info("Exit")
    logger.info(f.get())
    logger.info(f.isDone.toString())
    es.shutdown()
}
