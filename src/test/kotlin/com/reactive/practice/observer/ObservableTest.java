package com.reactive.practice.observer;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class ObservableTest {

    @Test
    void observableCreateTest() {
        Disposable disposable = Observable.<String>create(
                emitter -> {
                    emitter.onNext("Hello, reactive world!");
                    emitter.onComplete();
                }
        ).doAfterNext(
                s -> System.out.println(s + " After!!")
        ).subscribe(
                System.out::println,
                System.out::println,
                () -> System.out.println("Done!")
        );

        System.out.println(disposable.isDisposed());
    }

    @Test
    void observableConcatTest() {
        Observable.concat(Observable.just(1), Observable.just(2))
                .forEach(System.out::println);
    }

    @Test
    void observableIntervalTest() throws InterruptedException {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(e -> System.out.println("Received: " + e));
        Thread.sleep(5000L);
    }
}
