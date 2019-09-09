package ru.otus.spring.homework0105.service;

import ru.otus.spring.homework0105.domain.QuizAnswer;
import ru.otus.spring.homework0105.domain.QuizUnit;
import ru.otus.spring.homework0105.domain.User;

import java.util.List;

public interface QuizIoService {

    void printWelcome();

    void printGeneralInfo(int quizSize, User user);

    void printUserAnswer(String userAnswer);

    void printQuizUnit(QuizUnit quizUnit, List<String> answers);

    void printRightAnswerInfo();

    void printWrongAnswerInfo(String rightAnswer);

    void printQuizResult(List<QuizAnswer> quizAnswerList, User user, int score);

    int getUserAnswer();

    User getUserInfo();
}
