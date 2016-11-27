INSERT INTO user_roles(role) VALUES ('USER');
INSERT INTO user_roles(role) VALUES ('COORDINATOR');
INSERT INTO user_roles(role) VALUES ('ADMIN');

INSERT INTO users(first_name, last_name, email, password, phone_number, role)
VALUES ('John', 'Doe', 'john.doe@gmail.com', '$2a$10$b1H5t0FflsFMox0oqgcaQO/J5n9foo05lbA36h17fqj1dEx.yeyW2', '0911111111', 'USER');