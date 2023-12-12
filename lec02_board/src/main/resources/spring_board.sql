[spring board]
CREATE TABLE spring_board (
  bid int(11) NOT NULL AUTO_INCREMENT,
  bname varchar(50) DEFAULT NULL,
  btitle varchar(100) DEFAULT NULL,
  bcontent varchar(255) DEFAULT NULL,
  bdate datetime DEFAULT NULL,
  bhit int(11) DEFAULT NULL,
  bgroup int(11) DEFAULT NULL,
  bstep int(11) DEFAULT NULL,
  bindent int(11) DEFAULT NULL,
  PRIMARY KEY (bid)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

[spring quick start]
create database spring;

CREATE TABLE USERS (
  ID        varchar(50) NOT NULL,
  PASSWORD  varchar(30) NOT NULL,
  NAME      varchar(30) NOT NULL,
  ROLE      varchar(30) NOT NULL DEFAULT 'USER',
  PRIMARY KEY (`ID`)
);

DELIMITER //
BEGIN NOT ATOMIC
  declare i int default 1;
   while(i<=1001) do
      insert into users (id, password, name, role) 
              values(concat('USER_', LPAD(i,4,0)), '12345', concat('사용자_', LPAD(i,4,0)), "");
      set i = i + 1;
   end while;
END;


CREATE TABLE BOARD (
  seq         int(11)       NOT NULL AUTO_INCREMENT,
  title       varchar(200)  DEFAULT NULL,
  writer      varchar(20)   NOT NULL,
  content     varchar(2000) NOT NULL,
  regdate     date DEFAULT  sysdate(),
  cnt         int(11)       DEFAULT 0,
  filename    varchar(255)  DEFAULT NULL,
  uploadfile  varchar(255)  DEFAULT NULL,
  PRIMARY KEY (seq)
);

DELIMITER //
BEGIN NOT ATOMIC
  declare i int default 1;
   while(i<=1001) do
      insert into board (title, writer, content) 
              values(concat('TITLE_', LPAD(i,4,0)), concat('USER_', LPAD(i,4,0)), concat('글 상세내용..... \n', LPAD(i,4,0)));
      set i = i + 1;
   end while;
END;