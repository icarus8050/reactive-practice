package com.reactive.practice.controller

import org.reactivestreams.Publisher
import org.reactivestreams.Subscription
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SimpleExamController {

    @GetMapping("/hello")
    fun hello(name: String): Publisher<String> {

        return Publisher<String> {
            it.onSubscribe(object : Subscription {
                override fun request(n: Long) {
                    println(n)
                    it.onNext("Hello $name")
                }

                override fun cancel() {}
            })
        }
    }
}
