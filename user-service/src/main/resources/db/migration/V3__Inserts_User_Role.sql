INSERT INTO USERS ( id , username , password , enabled , created_at , modified_at )
VALUES ( 1 , 'user' , 'password' , TRUE , 1507083619067 , 1507083619067 );

INSERT INTO ROLES ( id , ROLE , created_at , modified_at )
VALUES ( 1 , 'USER' , 1507083619067 , 1507083619067 ) , ( 2 , 'ADMIN' , 1507083619067 , 1507083619067 );

INSERT INTO user_roles ( user_id , role_id ) VALUES ( 1 , 1 ) , ( 1 , 2 );