package com.conceptleassignment.quizAPI.entity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@RestController
public class controller {

    private static final String JDBC_URL = "jdbc:h2:mem:testdb";
    private static final String DB_USERNAME = "aditya";
    private static final String DB_PASSWORD = "something";
    @GetMapping("/quiz/{id}")
    public questionresponsedto getQuestion(@PathVariable Long id) {
        questionresponsedto questionResponse = null;

        String query = "SELECT question, option1, option2, option3, option4 FROM Questions WHERE questionid = ?";

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


}
