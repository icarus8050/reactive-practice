package com.reactive.practice.news_letter;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class NewsLetterPublisher implements Publisher<NewsLetter> {
    private List<NewsLetterSubscriber> newsLetterSubscribers;
    private NewsLetters newsLetters;

    public NewsLetterPublisher(NewsLetters newsLetters) {
        this.newsLetters = newsLetters;
        this.newsLetterSubscribers = new ArrayList<>();
    }

    @Override
    public void subscribe(Subscriber<? super NewsLetter> subscriber) {
        subscriber.onSubscribe(
                new NewsLetterSubscription(
                        newsLetters,
                        subscriber,
                        newsLetterSubscribers
                )
        );
    }
}
