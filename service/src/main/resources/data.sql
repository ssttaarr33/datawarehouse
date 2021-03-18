DROP TABLE IF EXISTS campaign;

CREATE TABLE campaign (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  data_source VARCHAR(250) NOT NULL,
  campaign VARCHAR(250) NOT NULL,
  daily DATE NOT NULL,
  clicks INT NOT NULL,
  impressions INT NOT NULL
);
--
-- INSERT into campaign (data_source, campaign, daily, clicks, impressions) values
-- ('test','test','2020-02-03',1,1  );