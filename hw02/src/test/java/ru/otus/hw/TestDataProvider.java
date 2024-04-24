package ru.otus.hw;

import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

public class TestDataProvider {

    public List<Question> createListWithOneQuestion(Question question) {
        return List.of(question);
    }

    public List<Question> createTestQuestions() {
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

    public Question createQuestion() {
        return new Question("What is the chemical symbol for gold?", List.of(new Answer("Au", true)));
    }

    public TestResult createSuccessfulTestResult(Student student, Question question) {
        TestResult testResult = new TestResult(student);
        testResult.getAnsweredQuestions().add(question);
        testResult.setRightAnswersCount(1);
        return testResult;
    }

    public TestResult createTestResultWithOneRightAnswer(Student student, List<Question> questions) {
        TestResult testResult = new TestResult(student);
        testResult.getAnsweredQuestions().addAll(questions);
        testResult.setRightAnswersCount(2);
        return testResult;
    }

}
