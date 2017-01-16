INSERT INTO user_roles(role) VALUES ('USER');
INSERT INTO user_roles(role) VALUES ('COORDINATOR');
INSERT INTO user_roles(role) VALUES ('ADMIN');

-- commented for now
-- INSERT INTO users(first_name, last_name, email, password, phone_number, role)
-- VALUES ('John', 'Doe', 'john.doe@gmail.com', '$2a$10$b1H5t0FflsFMox0oqgcaQO/J5n9foo05lbA36h17fqj1dEx.yeyW2', '0911111111', 'USER');

INSERT INTO company_type(id, name, base_id) VALUES (1, 'COMPUTING', null);
INSERT INTO company_type(id, name, base_id) VALUES (2, 'SOFTWARE_ENGINEERING', 1);
INSERT INTO company_type(id, name, base_id) VALUES (3, 'TELECOMMUNICATION', 1);

INSERT INTO sponsorship_type(name) VALUES ('FINANCIAL');
INSERT INTO sponsorship_type(name) VALUES ('MATERIAL');

INSERT INTO task_status_type(name) VALUES ('IN_PROGRESS');
INSERT INTO task_status_type(name) VALUES ('ACCEPTED');
INSERT INTO task_status_type(name) VALUES ('DECLINED');