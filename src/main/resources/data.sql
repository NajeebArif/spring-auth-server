INSERT IGNORE INTO auth_server_db.user (id, username, password, algorithm) VALUES (1, "narif", "$2a$10$2cWWrDbVY4IFIB3YbovvtOaqVXWGKaGy8vZpZezyiow5MwgoNz5rS", "BCRYPT");

INSERT IGNORE INTO auth_server_db.authority (id, name, user) VALUES (1, "READ", 1);
INSERT IGNORE INTO auth_server_db.authority (id, name, user) VALUES (2, "WRITE", 1);


insert ignore into oauth2_clients (id,client_id, client_secret) VALUES (1, 'clientId','clientSecret');
insert ignore into oauth2_clients (id,client_id, client_secret) VALUES (2,'machineClientId','machineClientSecret');

insert ignore into scope (id,name, client) VALUES (1,'read',1);
insert ignore into scope (id,name, client) VALUES (2,'write',1);
insert ignore into scope (id,name, client) VALUES (3,'delete',1);
insert ignore into scope (id,name, client) VALUES (4,'read',2);

insert ignore into grant_types(id,name, client) VALUES (1,'password',1);
insert ignore into grant_types(id,name, client) VALUES (2,'refresh_token',1);
insert ignore into grant_types(id,name, client) VALUES (3,'authorization_code',1);
insert ignore into grant_types(id,name, client) VALUES (4,'client_credentials',2);

insert ignore into redirect_url (id,url, client) VALUES (1,'http://localhost:8080/greet',1);
