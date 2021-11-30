package com.reactive.practice.future

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask

val logger: Logger = LoggerFactory.getLogger(FutureEx::class.java)

class FutureEx

fun interface SuccessCallback {
    fun onSuccess(result: String)
}

fun interface ExceptionCallback {
    fun onError(t: Throwable?)
}

class CallbackFutureTask(
    callable: Callable<String>,
    val sc: SuccessCallback,
    val ex: ExceptionCallback,
    ) : FutureTask<String>(callable) {

    override fun done() {
        try {
            sc.onSuccess(get())
        } catch (e: ExecutionException) {
            ex.onError(e.cause)
        }
    }
}

fun main() {
    val es = Executors.newCachedThreadPool()

    val f = CallbackFutureTask(
        {
            Thread.sleep(2000)
            logger.info("Async")
            "Hello"
        },
        {
            println("Result: $it")
        },
        {
            println("Error: ${it?.message}")
        }
    )

    /*val f = object : FutureTask<String>(
        Callable {
            Thread.sleep(2000)
            logger.info("Async")
            "Hello"
        }
    ) {
        override fun done() {
            println(get())
        }
    }*/

    /*val f: Future<String> = es.submit<String> {
        Thread.sleep(2000)
        logger.info("Async")
        return@submit "Hello"
    }*/

    es.execute(f)
    es.shutdown()
}
