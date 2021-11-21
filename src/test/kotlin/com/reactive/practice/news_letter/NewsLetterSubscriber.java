package com.reactive.practice.news_letter;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class NewsLetterSubscriber implements Subscriber<NewsLetter> {
    private Boolean completed = Boolean.FALSE;
    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription s) {
        this.subscription = s;
        this.subscription.request(1L);
    }

    @Override
    public void onNext(NewsLetter newsLetter) {
        System.out.println(newsLetter);
        if (!completed) {
            subscription.request(1L);
        }
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {
        completed = Boolean.TRUE;
        System.out.println("Subscribe completed..");
    }
}
