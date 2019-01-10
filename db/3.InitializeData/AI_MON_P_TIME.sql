insert into AI_MON_P_TIME (time_id, t_type, expr, state, remarks)
values (501, 'F', '0,0', 'E', 'ceshi');
insert into AI_MON_P_TIME (time_id, t_type, expr, state, remarks)
values (502, 'F', '0,0', 'E', 'sfsfa');
insert into AI_MON_P_TIME (time_id, t_type, expr, state, remarks)
values (1, 'CRON', '0 0/1 * * * ? *', 'U', '每隔5分钟触发一次');
insert into AI_MON_P_TIME (time_id, t_type, expr, state, remarks)
values (200, 'CRON', '0 0/10 * * * ? *', 'U', '每隔10分钟触发一次');
insert into AI_MON_P_TIME (time_id, t_type, expr, state, remarks)
values (300, 'CRON', '0 0/30 * * * ? *', 'U', '每隔30分钟触发一次');
insert into AI_MON_P_TIME (time_id, t_type, expr, state, remarks)
values (400, 'CRON', '0/10 * * * * ? *', 'U', '每隔10秒触发一次');
insert into AI_MON_P_TIME (time_id, t_type, expr, state, remarks)
values (500, 'CRON', '0 0 0/2 * * * *', 'U', '每隔2小时触发一次');
insert into AI_MON_P_TIME (time_id, t_type, expr, state, remarks)
values (600, 'CRON', '0 0/15 * * * ? *', 'U', '每隔15分钟触发一次');
insert into AI_MON_P_TIME (time_id, t_type, expr, state, remarks)
values (700, 'CRON', '0 0/5 0,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23 * * ? *', 'U', '每天在1点到6点时间段之外每5分钟触发一次');
insert into AI_MON_P_TIME (time_id, t_type, expr, state, remarks)
values (800, 'CRON', '0 0 4 1 * ?', 'U', '每月1号凌晨4点触发一次');
insert into AI_MON_P_TIME (time_id, t_type, expr, state, remarks)
values (900, 'CRON', '0 0 4 * * ?', 'U', '每天凌晨4点触发一次');
insert into AI_MON_P_TIME (time_id, t_type, expr, state, remarks)
values (41, 'CRON', '0 59 23 * * ? *', 'U', '每天晚上23点59分触发一次');
insert into AI_MON_P_TIME (time_id, t_type, expr, state, remarks)
values (42, 'CRON', '0 0,10,20,30,40,50 6 * * ? *', 'U', '每天6点每隔10分钟触发一次');
commit;