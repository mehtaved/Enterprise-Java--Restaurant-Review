CREATE TABLE poll (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    question VARCHAR(255),
    answer1 VARCHAR(255),
    answer2 VARCHAR(255),
    answer3 VARCHAR(255),
    votes1 INT,
    votes2 INT,
    votes3 INT,
    date_responded DATE
);

CREATE TABLE poll_response (
    id INT AUTO_INCREMENT PRIMARY KEY,
    poll_id INT,
    selected_option INT,
    FOREIGN KEY (poll_id) REFERENCES poll(id)
);
