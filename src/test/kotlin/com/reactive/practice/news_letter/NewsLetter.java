package com.reactive.practice.news_letter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewsLetter {
    private String title;
    private String content;

    @Override
    public String toString() {
        return "NewsLetter{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
