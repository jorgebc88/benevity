DROP TABLE post if EXISTS ;
DROP TABLE users if EXISTS ;

CREATE TABLE post (
                              ID VARCHAR(50) NOT NULL CONSTRAINT PK_post PRIMARY KEY,
                              name VARCHAR(50) NOT NULL,
                              title VARCHAR(50) NOT NULL,
                              content VARCHAR(50) NOT NULL,
                              slug VARCHAR(50) NOT NULL,
                              date VARCHAR(50)  NOT NULL,
                              user_id VARCHAR(50) NOT NULL,
                              image_url VARCHAR(50)
);

CREATE TABLE users(
                                   ID VARCHAR(50) NOT NULL CONSTRAINT PK_users PRIMARY KEY,
                                   username VARCHAR(50) NOT NULL,
                                   email VARCHAR(50) NOT NULL,
                                   password VARCHAR(100) NOT NULL
);
