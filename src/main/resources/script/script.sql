CREATE TABLE user (
  user_id SERIAL  NOT NULL ,
  name varchar(100) NOT NULL,
  email varchar(100) NOT NULL,
  mobile_number varchar(20) NOT NULL,
  pwd varchar(500) NOT NULL,
  role varchar(100) NOT NULL,
  create_dt date DEFAULT NULL,
  PRIMARY KEY (user_id)
);


INSERT INTO user 
(name,email,mobile_number, pwd, role,create_dt)
 VALUES ('ASIF','asif@gmail.com','9131053401', '$2a$12$OC4WF6RnIdOT/MYUqj3gZufQ1A7a0UORNM0MU7niVrElQLZCBV5BW', 'admin',NOW());