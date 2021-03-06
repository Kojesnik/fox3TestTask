CREATE TABLE user (
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  login VARCHAR(30) NOT NULL,
  password VARCHAR(30) NOT NULL
);

CREATE TABLE channel (
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  channel_name VARCHAR(30) NOT NULL
);

CREATE TABLE user_channel (
  user_id INTEGER NOT NULL,
  channel_id INTEGER NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
  FOREIGN KEY (channel_id) REFERENCES channel(id) ON DELETE CASCADE
);