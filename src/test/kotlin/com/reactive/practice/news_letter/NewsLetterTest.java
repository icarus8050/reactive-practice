package com.reactive.practice.news_letter;

import org.junit.jupiter.api.Test;

import java.util.List;

public class NewsLetterTest {

    @Test
    void simpleNewsLetterTest() {
        NewsLetterPublisher newsLetterPublisher = new NewsLetterPublisher(
                new NewsLetters(
                        List.of(
                                new NewsLetter("a title", "a content"),
                                new NewsLetter("b title", "b content"),
                                new NewsLetter("c title", "c content"),
                                new NewsLetter("d title", "d content")
                        )
                )
        );

        NewsLetterSubscriber newsLetterSubscriber = new NewsLetterSubscriber();
        newsLetterPublisher.subscribe(newsLetterSubscriber);
    }
}
