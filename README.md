# quizAPI
for Conceptile

Quiz API with JDBC Connection

This project provides a simple Quiz API using JDBC for database interaction, without adhering to the traditional Controller-Service-Repository architecture. It supports fetching questions, submitting answers, and tracking progress.



Open Database

After running the application go to:  http://localhost:8080/h2-console

the h2 console will open add the following details:

jdbc url = jdbc:h2:mem:testdb

username = "aditya"

password = "something"

Database Setup

Use the following SQL commands to create the quiz_question table in your database:

SQL Script to Create Table

CREATE TABLE quiz_question (
    quesid BIGINT PRIMARY KEY AUTO_INCREMENT,
    question VARCHAR(255),
    option1 VARCHAR(100),
    option2 VARCHAR(100),
    option3 VARCHAR(100),
    option4 VARCHAR(100),
    answer VARCHAR(100),
    submitted_answer VARCHAR(100)
);

Sample Data for Testing

INSERT INTO quiz_question (question, option1, option2, option3, option4, answer, submitted_answer)
VALUES
('What is the capital of France?', 'Paris', 'Berlin', 'Rome', 'Madrid', 'Paris', NULL),
('What is 2 + 2?', '3', '4', '5', '6', '4', NULL),
('Which planet is known as the Red Planet?', 'Earth', 'Mars', 'Jupiter', 'Saturn', 'Mars', NULL);

APIs

1. Fetch a Question and Options

Endpoint: /quiz/question

Method: GET



Returns a single random question along with its options.

{
    "quesid": 1,
    "question": "What is the capital of France?",
    "option1": "Paris",
    "option2": "Berlin",
    "option3": "Rome",
    "option4": "Madrid"
}

2. Submit an Answer

Endpoint: /quiz/submit

Method: POST

Request Body:

Submit the question ID and the user's answer.

{
    "quesid": 1,
    "submittedAnswer": "Paris"
}



Returns true if the submitted answer is correct, otherwise false.

true

3. Track Quiz Progress

Endpoint: /quiz/progress

Method: GET


Returns the user's quiz progress, including the total number of questions answered, correct answers, and wrong answers.
