/* 
 * V5__addArquillianTestUsers.sql
 * Adds all test users for component tests to the user repository.
 */

INSERT INTO T_USER
(
	USER_ID,
    PASSWORD,
    FIRST_NAME,
    LAST_NAME,
    FULL_NAME,
    GENDER,
    DATE_OF_BIRTH,
    EMAIL,
    VERSION
)
VALUES 
(
	'arquillian',
	'CQZ23bQ3lqPng2azVXlOkSAKDlwZ8iGfHq+oJbeuPlA=',
	'Arquillian',
	'Test',
	'Arquillian Test',
	1,
	'1999-12-31',
	'arquillian@hm.edu',
	1
);

INSERT INTO T_ROLE
(
	ROLE_ID,
	USER_ID,
    ROLE_NAME
)
VALUES
(
    0,
	'arquillian',
	'JEETRAIN_USER'
);

INSERT INTO T_ROLE
(
	ROLE_ID,
	USER_ID,
    ROLE_NAME
)
VALUES
(
    0,
	'arquillian',
	'JEETRAIN_ADMIN'
);