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

CReate TABLE if not exists auth_server_db.oauth2_clients(
    id int not null AUTO_INCREMENT,
    client_id varchar(100) not null ,
    client_secret varchar(100) not null,
    PRIMARY KEY (id)
);

CREATE TABLE if NOT EXISTS auth_server_db.scope (
                                                        id INT NOT NULL AUTO_INCREMENT,
                                                        name VARCHAR(45) NOT NULL,
                                                        client INT NOT NULL,
                                                        PRIMARY KEY (id));

CREATE TABLE if NOT EXISTS auth_server_db.grant_types (
                                                    id INT NOT NULL AUTO_INCREMENT,
                                                    name VARCHAR(45) NOT NULL,
                                                    client INT NOT NULL,
                                                    PRIMARY KEY (id));

CREATE TABLE if NOT EXISTS auth_server_db.redirect_url (
                                                    id INT NOT NULL AUTO_INCREMENT,
                                                    url VARCHAR(1000) NOT NULL,
                                                    client INT NOT NULL,
                                                    PRIMARY KEY (id));

