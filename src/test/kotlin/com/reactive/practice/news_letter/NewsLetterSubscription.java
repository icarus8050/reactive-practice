package com.reactive.practice.news_letter;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class NewsLetterSubscription implements Subscription {
    private Boolean canceled = Boolean.FALSE;
    private NewsLetters newsLetters;
    private AtomicLong sequence;
    private Subscriber<? super NewsLetter> newsLetterSubscriber;
    private List<NewsLetterSubscription> newsLetterSubscriptions;

    public NewsLetterSubscription(NewsLetters newsLetters,
                                  Subscriber<? super NewsLetter> newsLetterSubscriber,
                                  List<NewsLetterSubscription> newsLetterSubscriptions) {
        this.newsLetters = newsLetters;
        this.sequence = new AtomicLong(0L);
        this.newsLetterSubscriber = newsLetterSubscriber;
        this.newsLetterSubscriptions = newsLetterSubscriptions;
    }

    @Override
    public void request(long n) {
        if (canceled) {
            System.out.println("Subscription is canceled");
            return;
        }

        for (int i = 0; i < n; i++) {
            Optional<NewsLetter> foundNewsLetter = newsLetters.get((int) sequence.getAndAdd(1L));
            if (foundNewsLetter.isEmpty()) {
                newsLetterSubscriber.onComplete();
                cancel();
                return;
            }
            newsLetterSubscriber.onNext(foundNewsLetter.get());
        }
    }

    @Override
    public void cancel() {
        canceled = Boolean.TRUE;
        newsLetterSubscriptions.remove(this);
        newsLetters = null;
        sequence = null;
        newsLetterSubscriber = null;
        newsLetterSubscriptions = null;
    }
}
