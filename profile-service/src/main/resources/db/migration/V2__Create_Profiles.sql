CREATE TABLE profiles (
  id BIGINT PRIMARY KEY,
  user_id BIGINT UNIQUE NOT NULL,
  business_type character varying(255),
  name character varying(255) NOT NULL,
  website character varying(255)
);