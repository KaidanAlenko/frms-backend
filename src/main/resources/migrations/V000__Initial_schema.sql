CREATE TABLE user_roles (
  role VARCHAR(15),
  CONSTRAINT pk_user_roles PRIMARY KEY (role)
);

CREATE TABLE users (
  id            SERIAL        NOT NULL,
  first_name    VARCHAR(40)   NOT NULL,
  last_name     VARCHAR(40)   NOT NULL,
  email         VARCHAR(40)   UNIQUE NOT NULL,
  password      VARCHAR(100)  NOT NULL,
  phone_number  VARCHAR(20),
  role          VARCHAR(15)   NOT NULL,
  CONSTRAINT pk_users PRIMARY KEY (email),
  CONSTRAINT fk_user_role FOREIGN KEY(role) REFERENCES user_roles(role)
);
