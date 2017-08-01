
INSERT INTO BLCH_USERS(USERNAME,PASSWORD,ENABLED) VALUES ('user001','$2a$10$58kDtqo56oeNg8d8ONfrkO59WRibip5EgMEhQisNQ4H6APJBW3NaS', true);
INSERT INTO BLCH_USERS(USERNAME,PASSWORD) VALUES ('user002','$2a$10$58kDtqo56oeNg8d8ONfrkO59WRibip5EgMEhQisNQ4H6APJBW3NaS');
INSERT INTO BLCH_USERS(USERNAME,PASSWORD,ENABLED) VALUES ('user003','$2a$10$58kDtqo56oeNg8d8ONfrkO59WRibip5EgMEhQisNQ4H6APJBW3NaS', true);
INSERT INTO BLCH_USERS(USERNAME,PASSWORD,ENABLED) VALUES ('admin01','$2a$10$.ZmwWO3je9Actj5aNuwx4.NpzaqQmJvZvXcCKLzkW2WCFAA6wpsdG', true);


INSERT INTO BLCH_USER_ROLES (USERNAME, ROLE) VALUES ('user001', 'ROLE_USER');
INSERT INTO BLCH_USER_ROLES (USERNAME, ROLE) VALUES ('user002', 'ROLE_USER');
INSERT INTO BLCH_USER_ROLES (USERNAME, ROLE) VALUES ('user003', 'ROLE_USER');
INSERT INTO BLCH_USER_ROLES (USERNAME, ROLE) VALUES ('admin01', 'ROLE_ADMIN');