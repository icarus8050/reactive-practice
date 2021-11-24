package com.reactive.practice.pub_sub;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class PubSub2 {
    public static void main(String[] args) {
        Publisher<Integer> pub = new Publisher<>() {
            Iterable<Integer> iter = Stream.iterate(1, a -> a + 1)
                    .limit(10)
                    .collect(Collectors.toList());

            @Override
            public void subscribe(Subscriber<? super Integer> sub) {
                sub.onSubscribe(new Subscription() {
                    @Override
                    public void request(long n) {
                        try {
                            iter.forEach(a -> sub.onNext(a));
                            sub.onComplete();
                        } catch (Throwable t) {
                            sub.onError(t);
                        }
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };

        Subscriber<Integer> sub = new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription s) {
                log.debug("onSubscribe:");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                log.debug("onNext: {}", integer);
            }

            @Override
            public void onError(Throwable t) {
                log.debug("onError: {}", t);
            }

            @Override
            public void onComplete() {
                log.debug("onComplete");
            }
        };

        pub.subscribe(sub);
    }
}
