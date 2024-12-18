package com.conceptleassignment.quizAPI.entity;

import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@RestController
public class controller {

    private static final String JDBC_URL = "jdbc:h2:mem:testdb";
    private static final String DB_USERNAME = "aditya";
    private static final String DB_PASSWORD = "something";
    private int count= 0;
    private int countcorrect = 0;
    private int countwrong = 0;
    @GetMapping("/quiz/{id}")
    public questionresponsedto getQuestion(@PathVariable Long id) {
        questionresponsedto questionResponse = null;

        String query = "SELECT question, option1, option2, option3, option4 FROM quiz_question WHERE quesid = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String question = resultSet.getString("question");
                    String option1 = resultSet.getString("option1");
                    String option2 = resultSet.getString("option2");
                    String option3 = resultSet.getString("option3");
                    String option4 = resultSet.getString("option4");

                    questionResponse = new questionresponsedto(question, option1, option2, option3, option4);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions properly in production
        }

        return questionResponse;
    }

    @PostMapping("/quiz/submit")
    public boolean submitAnswer(@RequestBody SubmitAnswerRequest request) {
        boolean isCorrect = false;

        String selectQuery = "SELECT answer FROM quiz_question WHERE quesid = ?";
        String updateQuery = "UPDATE quiz_question SET submitted_answer = ? WHERE quesid = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USERNAME, DB_PASSWORD)) {

            String correctAnswer = null;
            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                selectStatement.setLong(1, request.getQuesid());
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        correctAnswer = resultSet.getString("answer");
                    }
                }
            }


            if (correctAnswer != null && correctAnswer.equals(request.getSubmittedAnswer())) {
                isCorrect = true;
                countcorrect++;
            }else{
                countwrong++;
            }

            count++;

            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setString(1, request.getSubmittedAnswer());
                updateStatement.setLong(2, request.getQuesid());
                updateStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isCorrect;
    }

    @GetMapping("/quiz/progress")
    public List<Integer> progress(){
        List<Integer> prog = new ArrayList<>();
        prog.add(count);
        prog.add(countcorrect);
        prog.add(countwrong);
        return prog;
    }

    }
