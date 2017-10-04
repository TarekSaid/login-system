INSERT INTO users(id, username, password, enabled, created_at, modified_at)
values (1, 'user', 'password', true, 1507083619067, 1507083619067);

INSERT INTO roles(id, role, created_at, modified_at)
values (1, 'USER', 1507083619067, 1507083619067), (2, 'ADMIN', 1507083619067, 1507083619067);

INSERT INTO user_roles(user_id, role_id)
values (1, 1), (1, 2);