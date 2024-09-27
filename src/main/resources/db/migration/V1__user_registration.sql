CREATE TABLE user_registration (

                                       id CHAR(36) PRIMARY KEY,
                                       name VARCHAR(100),
                                       user_name VARCHAR(100) UNIQUE,
                                       password VARCHAR(255)
                                       );

