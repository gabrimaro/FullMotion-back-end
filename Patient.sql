CREATE DATABASE Patient;

CREATE TABLE personal_info 
(
  id INT PRIMARY KEY,
  name VARCHAR(225),
  address VARCHAR(225),
  gender ENUM('M', 'F'),
  job VARCHAR(100),
  notes TEXT
);

CREATE TABLE thresholds 
(
  id INT,
  rom FLOAT,
  FOREIGN KEY (id) REFERENCES personal_info(id)
);

CREATE TABLE sensor_data 
(
  id INT,
  motion_rom FLOAT,
  motion_speed FLOAT,
  posture_center_of_mass FLOAT,
  posture_sway_patterns TEXT,
  FOREIGN KEY (id) REFERENCES personal_info(id)
);