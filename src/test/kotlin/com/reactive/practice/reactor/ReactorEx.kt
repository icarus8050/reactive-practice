package com.reactive.practice.reactor

import reactor.core.publisher.Flux

class ReactorEx

fun main() {
    Flux.create<Int> {
        it.next(1)
        it.next(2)
        it.next(3)
        it.next(4)
        it.complete()
    }
        .log()
        .map { it * 10 }
        .reduce(0) { a, b ->
            a + b
        }
        .log()
        .subscribe {
            println(it)
        }
}
