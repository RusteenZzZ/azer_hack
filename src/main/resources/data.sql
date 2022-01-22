INSERT INTO users (id, name, email, password) VALUES
    ('1', 'robert', 'robert@gmail.com', 'password');

INSERT INTO topics (id, title) VALUES
    ('1', 'C'),
    ('2', 'Python');

INSERT INTO exams (id, average_score, description,
    difficulty, is_public, num_of_participated,
    num_of_questions, rate, title, topic_id) VALUES
    ('1', '5', 'You will not pass',
    'HIGH', '1', '1',
    '19', '0', 'C threads', '1'),
    ('2', '19', 'gg ez',
    'LOW', 'true', '1',
    '2', '5', 'Python basics', '2');

INSERT INTO categories (id, title, topic_id) VALUES
    ('1', 'Threads', '1'),
    ('2', 'Basics', '1'),
    ('3', 'Arrays', '2'),
    ('4', 'Functions', '2'),
    ('5', 'Network algorithms', '2');

INSERT INTO questions (id, answer, coefficient,
    options, penalty, suggestion,
    title, type, category_id) VALUES
    ('1', 'scanf', '0.5',
    'printf,scanf,fopen,malloc', '0.2', 'http://www.c-cpp.ru/content/scanf',
    'What allows you to get an input', 'RADIO', '2');
--    ('1', '', '0.5',
--    'printf,scanf,fopen,malloc', '0.2', 'http://www.c-cpp.ru/content/scanf',
--    'What allows you to get an input', 'RADIO', '2')