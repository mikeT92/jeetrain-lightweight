CREATE SCHEMA jeetrain_db CHARACTER SET = 'utf8';
CREATE USER 'jeetrain-admin'@'localhost' IDENTIFIED BY 'admin123';
GRANT ALL ON jeetrain_db.* TO 'jeetrain-admin'@'localhost';
CREATE USER 'jeetrain-connect'@'localhost' IDENTIFIED BY 'connect123';
GRANT INSERT, SELECT, UPDATE, DELETE ON jeetrain_db.* TO 'jeetrain-connect'@'localhost';
FLUSH PRIVILEGES;
