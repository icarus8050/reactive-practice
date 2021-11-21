package com.reactive.practice.news_letter;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class NewsLetterPublisher implements Publisher<NewsLetter> {
    private List<NewsLetterSubscription> newsLetterSubscriptions;
    private NewsLetters newsLetters;

    public NewsLetterPublisher(NewsLetters newsLetters) {
        this.newsLetters = newsLetters;
        this.newsLetterSubscriptions = new ArrayList<>();
    }

    @Override
    public void subscribe(Subscriber<? super NewsLetter> subscriber) {
        NewsLetterSubscription newsLetterSubscription = new NewsLetterSubscription(
                newsLetters,
                subscriber,
                newsLetterSubscriptions
        );
        newsLetterSubscriptions.add(newsLetterSubscription);
        subscriber.onSubscribe(newsLetterSubscription);
    }
}
