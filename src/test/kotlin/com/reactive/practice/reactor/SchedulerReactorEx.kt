package com.reactive.practice.reactor

import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import org.slf4j.LoggerFactory
import org.springframework.scheduling.concurrent.CustomizableThreadFactory
import java.util.concurrent.Executors

class SchedulerReactorEx

fun main() {
    val logger = LoggerFactory.getLogger(SchedulerReactorEx::class.java)

    val pub = Publisher<Int> {
        it.onSubscribe(object : Subscription {
            override fun request(n: Long) {
                logger.debug("request()")
                it.onNext(1)
                it.onNext(2)
                it.onNext(3)
                it.onNext(4)
                it.onNext(5)
                it.onComplete()
            }

            override fun cancel() {
                TODO("Not yet implemented")
            }
        })
    }

    val subOnPub = Publisher<Int> {
        val es = Executors.newSingleThreadExecutor(object : CustomizableThreadFactory() {
            override fun getThreadNamePrefix(): String {
                return "subOn - "
            }
        })
        es.execute {
            pub.subscribe(it)
        }
    }

    val pubOnPub = Publisher<Int> {
        val es = Executors.newSingleThreadExecutor(object : CustomizableThreadFactory() {
            override fun getThreadNamePrefix(): String {
                return "pubOn - "
            }
        })

        subOnPub.subscribe(object : Subscriber<Int> {
            override fun onSubscribe(s: Subscription) {
                it.onSubscribe(s)
            }

            override fun onNext(t: Int) {
                es.execute {
                    it.onNext(t)
                }
            }

            override fun onError(t: Throwable) {
                es.execute {
                    it.onError(t)
                }
            }

            override fun onComplete() {
                es.execute {
                    it.onComplete()
                }
            }
        })
    }

    pubOnPub.subscribe(object : Subscriber<Int> {
        override fun onSubscribe(s: Subscription) {
            logger.debug("onSubscribe")
            s.request(Long.MAX_VALUE)
        }

        override fun onNext(t: Int) {
            logger.debug("onNext: $t")
        }

        override fun onError(t: Throwable) {
            logger.debug("onError")
        }

        override fun onComplete() {
            logger.debug("onComplete")
        }
    })

    logger.debug("Exit")
}
