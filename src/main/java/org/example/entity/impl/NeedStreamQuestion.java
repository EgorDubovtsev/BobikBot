package org.example.entity.impl;

import org.example.entity.Question;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
@Component
public class NeedStreamQuestion implements Question {
    private static final String QUESTION = "Хотите посетить стрим Боба?";
    private static final List<String> ANSWERS = Arrays.asList("Да", "я скучный, не пойду(((");

    @Override
    public String getQuestion() {
        return QUESTION;
    }

    @Override
    public List<String> getAnswers() {
        return ANSWERS;
    }
}
