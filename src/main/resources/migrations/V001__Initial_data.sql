INSERT INTO user_roles(role) VALUES ('USER');
INSERT INTO user_roles(role) VALUES ('COORDINATOR');
INSERT INTO user_roles(role) VALUES ('ADMIN');

-- commented for now
-- INSERT INTO users(first_name, last_name, email, password, phone_number, role)
-- VALUES ('John', 'Doe', 'john.doe@gmail.com', '$2a$10$b1H5t0FflsFMox0oqgcaQO/J5n9foo05lbA36h17fqj1dEx.yeyW2', '0911111111', 'USER');

INSERT INTO company_type(id, name, base_id) VALUES (1,  'COMPUTING',                         null);
INSERT INTO company_type(id, name, base_id) VALUES (2,  'ELECTROTECHNIC',                    null);
INSERT INTO company_type(id, name, base_id) VALUES (3,  'TELECOMMUNICATIONS',                   1);
INSERT INTO company_type(id, name, base_id) VALUES (4,  'SOFTWARE_ENGINEERING',                 1);
INSERT INTO company_type(id, name, base_id) VALUES (5,  'COMPUTING_SCIENCE',                    1);
INSERT INTO company_type(id, name, base_id) VALUES (6,  'COMPUTER_ENGINEERING',                 1);
INSERT INTO company_type(id, name, base_id) VALUES (7,  'INFORMATION_PROCESSING',               1);
INSERT INTO company_type(id, name, base_id) VALUES (8,  'AUTOMATIZATION',                       2);
INSERT INTO company_type(id, name, base_id) VALUES (9,  'ELECTRONIC_AND_COMPUTER_ENGINEERING',  2);
INSERT INTO company_type(id, name, base_id) VALUES (10, 'ELECTRONICS',                          2);
INSERT INTO company_type(id, name, base_id) VALUES (11, 'ELECTRICAL_POWER_ENGINEERING',         2);
INSERT INTO company_type(id, name, base_id) VALUES (12, 'WIRELESS_SYSTEMS',                     2);

INSERT INTO sponsorship_type(name) VALUES ('FINANCIAL');
INSERT INTO sponsorship_type(name) VALUES ('MATERIAL');

INSERT INTO task_status_type(name) VALUES ('IN_PROGRESS');
INSERT INTO task_status_type(name) VALUES ('ACCEPTED');
INSERT INTO task_status_type(name) VALUES ('DECLINED');