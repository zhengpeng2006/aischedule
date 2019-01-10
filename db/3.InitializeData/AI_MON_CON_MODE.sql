insert into ai_mon_con_mode(con_id,con_type,con_port,state,create_date,remark) values(1,'SSH',22,'U',sysdate(),'SHH连接方式'); 
insert into ai_mon_con_mode(con_id,con_type,con_port,state,create_date,remark) values(2,'Telnet',23,'U',sysdate(),'TELNET连接方式'); 
insert into ai_mon_con_mode(con_id,con_type,con_port,state,create_date,remark) values(3,'Ftp',21,'U',sysdate(),'FTP连接方式'); 
insert into ai_mon_con_mode(con_id,con_type,con_port,state,create_date,remark) values(4,'SFTP',22,'U',sysdate(),'SFTP连接方式'); 
commit;