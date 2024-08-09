CREATE TABLE users (
                                 user_id SERIAL  NOT NULL ,
                                 name varchar(100)  NOT NULL,
                                 email varchar(100) UNIQUE NOT NULL,
                                 mobile_number varchar(20) NOT NULL,
                                 pwd varchar(500) NOT NULL,
                                 role varchar(100) NOT NULL,
                                 create_dt date DEFAULT NULL,
                                 update_dt date DEFAULT NULL,
                                 PRIMARY KEY (user_id)
);


INSERT INTO users
(name,email,mobile_number, pwd, role,create_dt)
VALUES ('ASIF','asif@gmail.com','9131053401', '$2a$12$OC4WF6RnIdOT/MYUqj3gZufQ1A7a0UORNM0MU7niVrElQLZCBV5BW', 'ROLE_ADMIN',NOW());

CREATE TABLE authorities (
                                       id SERIAL  NOT NULL ,
                                       user_id int NOT NULL,
                                       authority varchar(50) NOT NULL,
                                       PRIMARY KEY (id),
                                       CONSTRAINT authorities_ibfk_1 FOREIGN KEY (user_id) REFERENCES users (user_id)
);
INSERT INTO authorities (user_id, authority)
VALUES (1, 'ROLE_USER');

INSERT INTO authorities (user_id, authority)
VALUES (1, 'ROLE_ADMIN');