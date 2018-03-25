
DROP TABLE IF EXISTS movie;

CREATE TABLE movie (
  id INT PRIMARY KEY AUTO_INCREMENT,
  movieName VARCHAR(100) UNIQUE DEFAULT '',
  translationName VARCHAR(100) DEFAULT '',
  releaseTime VARCHAR(100) DEFAULT '',
  producePlace VARCHAR(20) DEFAULT '',
  subtitle VARCHAR(20) DEFAULT '',
  category VARCHAR(50) DEFAULT '',
  introduction VARCHAR(2000) DEFAULT '',
  cover VARCHAR(100) DEFAULT '',
  magnet VARCHAR(500) DEFAULT '',
  isDownload BOOLEAN DEFAULT FALSE
)