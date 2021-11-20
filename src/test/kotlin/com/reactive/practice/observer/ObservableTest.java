package com.reactive.practice.observer;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import org.junit.jupiter.api.Test;

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
}
