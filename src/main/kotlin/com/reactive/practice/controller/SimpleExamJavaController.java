package com.reactive.practice.controller;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleExamJavaController {

    @GetMapping("/foo")
    public Publisher<String> foo(String name) {
        return s -> s.onSubscribe(new Subscription() {
            @Override
            public void request(long n) {
                System.out.println(n);
                s.onNext("Hello " + name);
                s.onComplete();
            }

            @Override
            public void cancel() {

            }
        });
    }
}
