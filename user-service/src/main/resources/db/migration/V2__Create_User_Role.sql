CREATE SEQUENCE hibernate_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1;

CREATE TABLE USERS (
  id BIGINT PRIMARY KEY,
  created_at BIGINT NOT NULL,
  modified_at BIGINT NOT NULL,
  enabled BOOLEAN,
  password character varying(255) NOT NULL,
  username character varying(255) UNIQUE NOT NULL
);

CREATE TABLE ROLES (
  id BIGINT PRIMARY KEY,
  created_at BIGINT NOT NULL,
  modified_at BIGINT NOT NULL,
  ROLE character varying(255) UNIQUE NOT NULL
);

CREATE TABLE user_roles (
  user_id BIGINT REFERENCES USERS(id),
  role_id BIGINT REFERENCES ROLES(id)
);