-- ----------------------------
--  Database Setup Information
-- ----------------------------

DROP DATABASE IF EXISTS REP;

CREATE DATABASE REP;

USE REP;

CREATE USER REP@localhost IDENTIFIED BY 'REP';

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP
ON REP.*
TO REP@localhost;

/*
 Navicat Premium Data Transfer

 Source Server         : LOCAL
 Source Server Type    : MySQL
 Source Server Version : 50620
 Source Host           : localhost
 Source Database       : NBAD3

 Target Server Type    : MySQL
 Target Server Version : 50620
 File Encoding         : utf-8
 File AUTHORIZATION	   : Jai Kiran

*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `Answer`
-- ----------------------------
DROP TABLE IF EXISTS ANSWER;
CREATE TABLE ANSWER (
  STUDYID INT NOT NULL,
  QUESTIONID INT NOT NULL,
  USERNAME varchar(40) NOT NULL DEFAULT '',
  CHOICE varchar(40) DEFAULT NULL,
  DATESUBMITTED datetime DEFAULT NULL,
  PRIMARY KEY (STUDYID,QUESTIONID,USERNAME)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `Question`
-- ----------------------------
DROP TABLE IF EXISTS QUESTION;
CREATE TABLE QUESTION (
  QUESTIONID INT NOT NULL AUTO_INCREMENT,
  STUDYID INT DEFAULT NULL,
  QUESTION varchar(50) DEFAULT NULL,
  ANSWERTYPE varchar(10) DEFAULT NULL,
  OPTION1 varchar(40) DEFAULT NULL,
  OPTION2 varchar(40) DEFAULT NULL,
  OPTION3 varchar(40) DEFAULT NULL,
  OPTION4 varchar(40) DEFAULT NULL,
  OPTION5 varchar(40) DEFAULT NULL,
  PRIMARY KEY (QUESTIONID),
  KEY STUDYID (STUDYID),
  CONSTRAINT `question_ibfk_1` FOREIGN KEY (STUDYID) REFERENCES STUDY (STUDYID) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2000 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `Reported`
-- ----------------------------
DROP TABLE IF EXISTS REPORTED;
CREATE TABLE REPORTED (
  QUESTIONID INT NOT NULL,
  STUDYID INT NOT NULL,
  DATE datetime DEFAULT NULL,
  NUMPARTICIPANTS int(15) DEFAULT NULL,
  STATUS varchar(10) DEFAULT NULL,
  PRIMARY KEY (STUDYID,QUESTIONID),
  KEY QUESTIONID (QUESTIONID),
  CONSTRAINT `reported_ibfk_1` FOREIGN KEY (QUESTIONID) REFERENCES QUESTION (QUESTIONID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reported_ibfk_2` FOREIGN KEY (STUDYID) REFERENCES STUDY (STUDYID) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `Study`
-- ----------------------------
DROP TABLE IF EXISTS STUDY;
CREATE TABLE STUDY (
  STUDYID INT NOT NULL AUTO_INCREMENT,
  STUDYNAME varchar(40) DEFAULT NULL,
  DESCRIPTION varchar(50) DEFAULT NULL,
  USERNAME varchar(50) DEFAULT NULL,
  DATECREATED datetime DEFAULT NULL,
  IMAGEURL mediumblob DEFAULT NULL,
  REQPARTICIPANTS int(15) DEFAULT NULL,
  ACTPARTICIPANTS int(15) DEFAULT NULL,
  SSTATUS varchar(10) DEFAULT NULL,
  PRIMARY KEY (STUDYID),
  KEY USERNAME (USERNAME),
  CONSTRAINT `study_ibfk_1` FOREIGN KEY (USERNAME) REFERENCES USER (USERNAME) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `User`
-- ----------------------------
DROP TABLE IF EXISTS USER;
CREATE TABLE USER (
  USERNAME varchar(50) NOT NULL DEFAULT '',
  PASSWORD varchar(50) DEFAULT NULL,
  TYPE varchar(50) DEFAULT NULL,
  STUDIES int(15) DEFAULT NULL,
  PARTICIPATION int(15) DEFAULT NULL,
  COINS int(15) DEFAULT NULL,
  PRIMARY KEY (USERNAME)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  ALTER Table Command for `User`
-- ----------------------------

ALTER TABLE USER ADD COLUMN NAME varchar(40) DEFAULT NULL;

-- ----------------------------
--  Insert Command for `User`
-- ----------------------------

--INSERT INTO USER(NAME,USERNAME,PASSWORD,TYPE,STUDIES,PARTICIPATION,COINS) 
--VALUES ('Jai Kiran','jaikiranduvvu@yahoo.com','jaikiran','Participant',0,0,0);

--INSERT INTO USER(NAME,USERNAME,PASSWORD,TYPE,STUDIES,PARTICIPATION,COINS) 
--VALUES ('Krishna Sampath','kpendyal@uncc.edu','sampath','Admin',0,0,0);

-- ----------------------------
--  ALTER Table Command for `QUESTION`
-- ----------------------------
ALTER TABLE QUESTION ADD COLUMN NOOFANSWERS int(15) DEFAULT NULL;

-- ----------------------------
--  ALTER Table Command for `Reported`
-- ----------------------------

ALTER TABLE REPORTED DROP PRIMARY KEY,DROP COLUMN NUMPARTICIPANTS,DROP COLUMN STATUS,ADD COLUMN USERNAME varchar(40) NOT NULL DEFAULT '',
ADD PRIMARY KEY(STUDYID,QUESTIONID,USERNAME);

-- ----------------------------
--  CREATE Table Command for `REPORTED_STATUS`
-- ----------------------------

CREATE TABLE REPORTED_STATUS(
  QUESTIONID INT NOT NULL,
  STUDYID INT NOT NULL,
  STATUS varchar(10) NOT NULL DEFAULT '',
  PRIMARY KEY (STUDYID,QUESTIONID,STATUS),
  KEY QUESTIONID (QUESTIONID),
  CONSTRAINT `reported_status_ibfk_1` FOREIGN KEY (QUESTIONID) REFERENCES REPORTED (QUESTIONID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reported_status_ibfk_2` FOREIGN KEY (STUDYID) REFERENCES REPORTED (STUDYID) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  ALTER Table Command for `User`
-- ----------------------------

ALTER TABLE USER ADD COLUMN SALT varchar(64) DEFAULT NULL;

ALTER TABLE USER CHANGE COLUMN PASSWORD PASSWORD varchar(64) NULL DEFAULT NULL ;

