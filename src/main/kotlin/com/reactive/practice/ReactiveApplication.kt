package com.reactive.practice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class ReactiveApplication {
    @RestController
    class MyController {

        @GetMapping("/rest")
        fun rest(idx: Int?): String {
            return "rest $idx"
        }
    }
}

fun main(args: Array<String>) {
    runApplication<ReactiveApplication>(*args)
}
