INSERT IGNORE INTO auth_server_db.user (id, username, password, algorithm) VALUES (1, "narif", "$2a$10$2cWWrDbVY4IFIB3YbovvtOaqVXWGKaGy8vZpZezyiow5MwgoNz5rS", "BCRYPT");

INSERT IGNORE INTO auth_server_db.authority (id, name, user) VALUES (1, "READ", 1);
INSERT IGNORE INTO auth_server_db.authority (id, name, user) VALUES (2, "WRITE", 1);