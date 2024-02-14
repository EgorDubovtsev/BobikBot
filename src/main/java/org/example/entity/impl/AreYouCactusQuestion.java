package org.example.entity.impl;

import org.example.entity.Question;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
@Component
public class AreYouCactusQuestion implements Question {
    private static final String QUESTION = "Ты кактус???";
    private static final List<String>  ANSWER = Arrays.asList("Ну кактус и че?", "Сам ты кактус!");
    @Override
    public String getQuestion() {
        return QUESTION;
    }

    @Override
    public List<String> getAnswers() {
        return ANSWER;
    }
}
