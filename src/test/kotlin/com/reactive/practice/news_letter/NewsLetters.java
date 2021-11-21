package com.reactive.practice.news_letter;

import java.util.List;
import java.util.Optional;

public class NewsLetters {
    private List<NewsLetter> newsLetters;

    public NewsLetters(List<NewsLetter> newsLetters) {
        this.newsLetters = newsLetters;
    }

    public void add(NewsLetter newsLetter) {
        newsLetters.add(newsLetter);
    }

    public Optional<NewsLetter> get(int index) {
        if (isOverflow(index)) {
            return Optional.empty();
        }
        return Optional.ofNullable(newsLetters.get(index));
    }

    private boolean isOverflow(int index) {
        return index > newsLetters.size() - 1;
    }
}
