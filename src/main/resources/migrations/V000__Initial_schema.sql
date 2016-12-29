-- table for keeping user system roles, eg. ADMINISTRATOR, USER, etc.
CREATE TABLE user_roles (
  role VARCHAR(15),
  CONSTRAINT pk_user_roles PRIMARY KEY (role)
);

-- table for storing user personal and system data
CREATE TABLE users (
  id           SERIAL             NOT NULL,
  first_name   VARCHAR(40)        NOT NULL,
  last_name    VARCHAR(40)        NOT NULL,
  email        VARCHAR(40) UNIQUE NOT NULL,
  password     VARCHAR(100)       NOT NULL,
  phone_number VARCHAR(20),
  role         VARCHAR(15)        NOT NULL,
  CONSTRAINT pk_users PRIMARY KEY (email),
  CONSTRAINT fk_user_role FOREIGN KEY (role) REFERENCES user_roles (role)
);

-- types of companies, can be hierarchic so they need to have base type (parent)
-- example: COMPUTING -> SOFTWARE_ENGINEERING, TELECOMMUNICATION, ELECTROTECHNIC -> AUTOMATION
CREATE TABLE company_type (
  id      INTEGER      NOT NULL,
  name    VARCHAR(100) NOT NULL,
  base_id INTEGER DEFAULT NULL,
  CONSTRAINT pk_company_type PRIMARY KEY (id),
  CONSTRAINT fk_base_company_type FOREIGN KEY (base_id) REFERENCES company_type (id)
);

-- company basic data
-- notes field is used for writing contact numbers and emails
CREATE TABLE company (
  id          SERIAL              NOT NULL,
  name        VARCHAR(100) UNIQUE NOT NULL,
  short_name  VARCHAR(20),
  web_address VARCHAR(100),
  address     VARCHAR(150),
  notes       TEXT,
  type        INTEGER             NOT NULL,
  CONSTRAINT pk_company PRIMARY KEY (id),
  CONSTRAINT fk_company_company_type FOREIGN KEY (type) REFERENCES company_type (id)
);

-- contest, workshop or some other event that require calls for sponsors and sponsorship tasks management
CREATE TABLE event (
  id         SERIAL       NOT NULL,
  name       VARCHAR(100) NOT NULL,
  short_name VARCHAR(20),
  year       CHAR(4)      NOT NULL,
  CONSTRAINT pk_event PRIMARY KEY (id)
);

-- lookup table: for now it will contain information about types of sponsorships: MATERIAL, FINANCIAL
CREATE TABLE sponsorship_type (
  name VARCHAR(20),
  CONSTRAINT pk_sponsorship_type PRIMARY KEY (name)
);

-- lookup table for task statuses, for now it is IN_PROGRESS, ACCEPTED, DECLINED
CREATE TABLE task_status_type (
  name VARCHAR(20) NOT NULL,
  CONSTRAINT pk_task_status_type PRIMARY KEY (name)
);

-- central table of system - it's abstraction of fund raising task which includes making contact to certain company
-- for certain event.
CREATE TABLE task (
  id             SERIAL      NOT NULL,
  event_id       INTEGER     NOT NULL,
  company_id     INTEGER     NOT NULL,
  assignee       INTEGER     DEFAULT NULL,
  type           VARCHAR(20) NOT NULL,
  call_time      TIMESTAMP   DEFAULT NULL,
  mail_time      TIMESTAMP   DEFAULT NULL,
  follow_up_time TIMESTAMP   DEFAULT NULL,
  status         VARCHAR(20) DEFAULT 'IN_PROGRESS',
  notes          TEXT        DEFAULT NULL,
  CONSTRAINT pk_task PRIMARY KEY (id),
  CONSTRAINT fk_task_event_id FOREIGN KEY (event_id) REFERENCES event (id),
  CONSTRAINT fk_task_company_id FOREIGN KEY (company_id) REFERENCES company (id)
);