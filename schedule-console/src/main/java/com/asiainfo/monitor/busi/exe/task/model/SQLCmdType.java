package com.asiainfo.monitor.busi.exe.task.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.busi.exe.ds.MonTableDataSourceUtil;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.BaseContainer;
import com.asiainfo.monitor.tools.model.CmdType;
import com.asiainfo.monitor.tools.util.GlobEnvContext;
import com.asiainfo.monitor.tools.util.TimeUtil;
import com.asiainfo.monitor.tools.util.TypeConst;

public class SQLCmdType extends CmdType implements Serializable{

	private static Log log=LogFactory.getLog(SQLCmdType.class);
	
	public SQLCmdType() {
	}

	public String getType(){
		return CmdType.TABLE;
	}
	
	public void doTask(ITaskRtn taskRtn) throws Exception{
		try{
			List l = new ArrayList();
			Connection conn = null;
			PreparedStatement ptmt = null;
			ResultSet rs = null;
			try {
				String dburlName = ((TaskTableContainer)this.getContainer()).getDburlName();
				String dbacctCode = ((TaskTableContainer)this.getContainer()).getDbacctCode();
				String sql = ((TaskTableContainer)this.getContainer()).getExpr();
				if (dburlName.startsWith("$")){
					KeyName dbUrlParam = ((BaseContainer)this.getContainer()).findParameter(dburlName.substring(1));
					if (dbUrlParam != null){
						dburlName = dbUrlParam.getKey();
					}
				}
				if (dbacctCode.startsWith("$")){
					KeyName dbAcctParam = ((BaseContainer)this.getContainer()).findParameter(dbacctCode.substring(1));
					if (dbAcctParam != null){
						dbacctCode=dbAcctParam.getKey();
					}
				}
				
				
				
				conn = MonTableDataSourceUtil.getConnection(dburlName,dbacctCode);
				
				if (conn == null){
					// 数据连接为空
					log.error(AIMonLocaleFactory.getResource("MOS0000250") + ",url:"+dburlName+",acctcode:"+dbacctCode+",taskTableId:"+((TaskTableContainer)this.getContainer()).getId());
					return;
				}
				if (StringUtils.contains(sql, "$")) {
					Calendar objCalendar = Calendar.getInstance();
					objCalendar.setTime(new Date());
					int year = objCalendar.get(Calendar.YEAR);
					int shortyear = Integer.parseInt(TimeUtil.format8(objCalendar.getTime()));
					String month = TimeUtil.getMM(objCalendar.getTime());
					//上个月
					String month_1=TimeUtil.getMM(TimeUtil.addOrMinusMonth(objCalendar.getTimeInMillis(), -1));					
					String day = TimeUtil.getDD(objCalendar.getTime());
					sql = StringUtils.replace(sql, "$SHORTYEAR", String.valueOf(shortyear));
					sql = StringUtils.replace(sql, "$YEAR", String.valueOf(year));
					sql = StringUtils.replace(sql, "$MONTH_1", String.valueOf(month_1));
					sql = StringUtils.replace(sql, "$MONTH", String.valueOf(month));
					sql = StringUtils.replace(sql, "$DAY", String.valueOf(day));
					sql = StringUtils.replace(sql, "$HH_1", TimeUtil.getHH(TimeUtil.addOrMinusHours(objCalendar.getTimeInMillis(), -1)));
					sql = StringUtils.replace(sql, "$HH", TimeUtil.getHH(objCalendar.getTime()));
					sql = StringUtils.replace(sql,  "$MM",TimeUtil.getMi(objCalendar.getTime()));
					sql = StringUtils.replace(sql,  "$SS",TimeUtil.getSs(objCalendar.getTime()));
					//设置参数,重构考虑参数预编译
					if ( ((BaseContainer)this.getContainer()).getParameter()!=null && ((BaseContainer)this.getContainer()).getParameter().size()>0){
						List parameter=((BaseContainer)this.getContainer()).getParameter();						
						for (int count=0;count<parameter.size();count++){
							sql = StringUtils.replace(sql, "$"+((KeyName)parameter.get(count)).getName(),((KeyName)parameter.get(count)).getKey());
						}
					}
				}
				if (GlobEnvContext.isDebug()){
					log.error("SQL:"+sql);
				}
				ptmt = conn.prepareStatement(sql);
				rs = ptmt.executeQuery();
				while (rs.next()) {
					l.add(rs.getString(1));
				}
			} catch (Exception ex) {
				log.error("query error", ex);
			} finally {
				if (rs != null) {
					rs.close();
				}
				if (ptmt != null) {
					ptmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			}

			String rtn = StringUtils.join(l.iterator(),TypeConst._NEWLINE_CHAR);
			taskRtn.setMsg(rtn);
			
		}catch(Exception e){
			// 执行类型为TABLE的任务["+this.getContainer().getName()+"]时发生异常
			log.error(AIMonLocaleFactory.getResource("MOS0000176", this.getContainer().getName()) + ":"+e.getMessage());
		}
		return;
	}
}
