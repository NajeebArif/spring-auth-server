CREATE TABLE if NOT EXISTS auth_server_db.user (
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL,
  password TEXT NOT NULL,
  algorithm VARCHAR(45) NOT NULL,
  PRIMARY KEY (id));

CREATE TABLE if NOT EXISTS auth_server_db.authority (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  user INT NOT NULL,
  PRIMARY KEY (id));
