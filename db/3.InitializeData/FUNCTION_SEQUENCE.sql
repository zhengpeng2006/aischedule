-- ----------------------------
--  Procedure definition for `seq_curval`
-- ----------------------------
DROP FUNCTION IF EXISTS `seq_curval`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `seq_curval`(seq_name CHAR (30)) RETURNS int(11)
BEGIN
 DECLARE cur  INT;
 SELECT LAST_NUMBER INTO cur FROM SYS_SEQUENCES WHERE SEQUENCE_NAME=seq_name;
 RETURN  cur;
END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `seq_nextval`
-- ----------------------------
DROP FUNCTION IF EXISTS `seq_nextval`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `seq_nextval`(seq_name CHAR (30)) RETURNS int(11)
BEGIN
 UPDATE SYS_SEQUENCES SET LAST_NUMBER= CASE last_number WHEN 0 THEN LAST_INSERT_ID(START_BY) ELSE LAST_INSERT_ID(LAST_NUMBER+INCREMENT_BY) END WHERE SEQUENCE_NAME=seq_name;
 RETURN LAST_INSERT_ID();
END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `TO_DATE`
-- ----------------------------
DROP FUNCTION IF EXISTS `TO_DATE`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `TO_DATE`(V_DATE CHAR(30),V_FORMAT CHAR(30)) RETURNS datetime
BEGIN
	SET V_FORMAT = LCASE(V_FORMAT);
	SET V_DATE = LCASE(V_DATE);
	IF('sysdate' LIKE LCASE(V_DATE)) THEN
		SET V_DATE = CAST(SYSDATE() AS CHAR);
	END IF;
	SET V_FORMAT = REPLACE(V_FORMAT,'yyyy','%Y');
	SET V_FORMAT = REPLACE(V_FORMAT,'yy','%y');
	SET V_FORMAT = REPLACE(V_FORMAT,'mm','%m');
	SET V_FORMAT = REPLACE(V_FORMAT,'dd','%d');
	SET V_FORMAT = REPLACE(V_FORMAT,'hh24','%H');
	SET V_FORMAT = REPLACE(V_FORMAT,'hh','%h');
	SET V_FORMAT = REPLACE(V_FORMAT,'mi','%i');
	SET V_FORMAT = REPLACE(V_FORMAT,'ss','%s');
	RETURN STR_TO_DATE(DATE_FORMAT(V_DATE,V_FORMAT),V_FORMAT);
END
;;
DELIMITER ;


