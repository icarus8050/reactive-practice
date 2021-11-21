package com.reactive.practice.news_letter;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class NewsLetterRepository {
    private Map<Long, NewsLetter> newsLetters;
    private AtomicLong idSequence;
    private Class<NewsLetter> clazz;

    public NewsLetterRepository(Map<Long, NewsLetter> newsLetters) {
        this.newsLetters = newsLetters;
        this.idSequence = new AtomicLong(1);
        this.clazz = NewsLetter.class;
    }

    public Optional<NewsLetter> findById(Long id) {
        return Optional.ofNullable(newsLetters.get(id));
    }

    public void save(NewsLetter newsLetter) {
        if (newsLetter.getId() == null) {
            saveItem(newsLetter);
            return;
        }
        updateItem(newsLetter);
    }

    private void saveItem(NewsLetter newsLetter) {
        generateIdForPersistence(newsLetter);
        newsLetters.put(newsLetter.getId(), newsLetter);
    }

    private void generateIdForPersistence(NewsLetter newsLetter) {
        try {
            Field id = clazz.getDeclaredField("id");
            id.setAccessible(true);
            id.set(newsLetter, idSequence.getAndAdd(1L));
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void updateItem(NewsLetter newsLetter) {
        findById(newsLetter.getId()).orElseThrow(() -> new IllegalArgumentException("Entity not found.."));
        newsLetters.put(newsLetter.getId(), newsLetter);
    }
}
