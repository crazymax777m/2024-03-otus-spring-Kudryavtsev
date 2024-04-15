package ru.otus.hw;

import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

public class TestDataProvider {

    public List<Question> getListWithOneQuestion() {
        return List.of(
                new Question("What is the chemical symbol for gold?",
                        List.of(new Answer("Au", true))));
    }

    public List<Question> getTestQuestions() {
        return List.of(
                new Question("What is the chemical symbol for gold?",
                        List.of(new Answer("Au", true),
                                new Answer("Ag", false),
                                new Answer("Hg", false),
                                new Answer("Pb", false))),

                new Question("Who is the author of \"To Kill a Mockingbird\"?",
                        List.of(new Answer("Harper Lee", true),
                                new Answer("J.K. Rowling", false),
                                new Answer("Stephen King", false),
                                new Answer("Ernest Hemingway", false)))
        );
    }

}