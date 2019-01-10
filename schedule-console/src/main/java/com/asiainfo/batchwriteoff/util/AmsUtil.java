package com.asiainfo.batchwriteoff.util;

import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.ai.common.bo.BOBsStaticDataBean;
import com.ai.common.i18n.CrmLocaleFactory;
import com.ai.common.ivalues.IBOBsParaDetailValue;
import com.ai.common.ivalues.IBOBsStaticDataValue;
import com.ai.common.util.ExceptionUtil;
import com.ai.common.util.FtpUtil;
import com.ai.common.util.StaticDataUtil;
import com.ai.common.util.TimeUtil;
import com.ai.secframe.common.Constants;
import com.asiainfo.batchwriteoff.cache.getter.BsParaDetailCacheGetter;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.RemoteException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * <p> @Description </p>
 * <p> @company Asiainfo-linkage (NJ) </p>
 * <p> @author 闫瑞国 </p>
 * <p> @Email yanrg@asiainfo-linkage.com </p>
 * <p> @createDate Dec 23, 2010 11:31:35 AM </p>
 * <p> @modifyDate </p>
 * <p> @version 1.0 </p>
 */
public class AmsUtil {
	
	private static final transient Log log = LogFactory.getLog(AmsUtil.class);
	
//	private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");//add by guowx 20130606  yyyyMMddHHmmssSSS

	public static Timestamp delayPsPayment(Timestamp orgiCreateTime)throws Exception{
		int delayTimeSecond = 120;
		
		IBOBsParaDetailValue delayPara = BsParaDetailCacheGetter.
					getParaDetailValue(AmsConst.PARA_DETAIL.PARA_DETAIL_REGION_X, AmsConst.PARA_DETAIL.PARA_TYPE_AM_PAYMENT, AmsConst.PARA_DETAIL.PARA_CODE_DELAY_PS_TIME);
		if(delayPara!=null && StringUtils.isNotBlank(delayPara.getPara1()) && StringUtils.isNumeric(delayPara.getPara1().trim())){
			delayTimeSecond = Integer.parseInt(delayPara.getPara1().trim());
		}
		return new Timestamp((TimeUtil.addOrMinusSecond(orgiCreateTime.getTime(), delayTimeSecond)).getTime());
	}
	
	/**
	 * 根据卡号判断是505卡或者是神州行充值卡
	 * @param cardNo
	 * @return	true : 505卡     false：神州行充值卡
	 * @throws Exception
	 */
	public static boolean is505Card(String cardNo)throws Exception{
		if(StringUtils.isBlank(cardNo)){
			ExceptionUtil.throwBusinessException("AMS0700386");
		}
		if(cardNo.length()>10){
			return false;
		}
		return true;
	}
	
	/**
	 * 
	* @Function: AmsUtil.java
	* @Description: 
	*
	* @param cardNo			神州行全国卡卡号
	* @return				true：归属地是浙江省   false：归属地是外省
	* @throws Exception
	 */
	public static boolean isZJProvinceECard(String cardNo)throws Exception{
		if(StringUtils.isBlank(cardNo)){
			ExceptionUtil.throwBusinessException("AMS0700386");
		}
		if(cardNo.length()<17){
			ExceptionUtil.throwBusinessException("AMS0700387", cardNo);
		}
		String proviceFlag = cardNo.substring(5, 7);
		if("11".equalsIgnoreCase(proviceFlag)){
			return true;
		}
		return false;
	}
	
	/*public static IInsAccrelValue[] preDealAcctRels(IInsAccrelValue[] acctRelInfos)throws Exception{
		
		if(acctRelInfos!=null && acctRelInfos.length>0){
			for(int i=0;i<acctRelInfos.length;i++){
				IInsAccrelValue temp = acctRelInfos[i];
				long oldAcctId = temp.getOldAcctId();
				String userRegionId = temp.getUserRegionId();
				
				if(oldAcctId>0 && oldAcctId!=temp.getAcctId()
						&& !userRegionId.equals(temp.getAcctRegionId())){
					
					temp.setAcctId(oldAcctId);
					temp.setAcctRegionId(userRegionId);
					temp.setOldAcctId(-1);
				}
			}
		}
		*//**
		 * patch code end
		 *//*
		
		return acctRelInfos;
	}*/
	
	/**
	 * 对异地统付账户做特殊处理
	 * 还是查询出本地账户，做充值或是查询时，还是依据本地的老账户为准
	 * 以后全省割接完毕后，可以注释掉，直接充值到移动统付账户上
	 * patch code start 
	 *//*
	public static IQAcctRelInfoValue[] preDealAcctRels(IQAcctRelInfoValue[] acctRelInfos)throws Exception{
		
		if(acctRelInfos!=null && acctRelInfos.length>0){
			for(int i=0;i<acctRelInfos.length;i++){
				IQAcctRelInfoValue temp = acctRelInfos[i];
				long oldAcctId = temp.getOldAcctId();
				String userRegionId = temp.getUserRegionId();
				
				if(oldAcctId>0 && oldAcctId!=temp.getAcctId()
						&& !userRegionId.equals(temp.getAcctRegionId())){
					
					ICrmCustomerSV customerSV = (ICrmCustomerSV) ServiceFactory.getService(ICrmCustomerSV.class);
					IBOCmAccountValue cmAccountValue = customerSV.queryOnlyAccountInfoById(oldAcctId);
					
					temp.setAcctId(oldAcctId);
					temp.setAcctRegionId(userRegionId);
					
					temp.setCustId(cmAccountValue.getCustId());
					temp.setCustType(cmAccountValue.getCustType());
					temp.setAcctName(cmAccountValue.getAcctName());
					//add by xuds3 for ase 20120827
					temp.initProperty(IQAcctRelInfoValue.S_AcctName, cmAccountValue.getOldObj(IBOCmAccountValue.S_AcctName));
					//end
					temp.setAcctType(cmAccountValue.getAcctType());

					temp.setAcctCreditId(cmAccountValue.getAcctCreditId());
					temp.setAcctCreditValue(cmAccountValue.getAcctCreditValue());
			
					String acctType = Integer.toString(cmAccountValue.getAcctType());
					IBOBsStaticDataValue acctTypeDesc = StaticDataCacheGetter.
							getBsStaticDataByTypeAndValue(CmConstants.CmStaticDataCodeType.ACCOUNT_TYPE, acctType);
					String acctTypeDisplay = "";
					if (acctTypeDesc != null){
						acctTypeDisplay = acctTypeDesc.getCodeName();
					}
					temp.setAcctTypeDesc(acctTypeDisplay);
					
					
					temp.setAcctPayMethod(cmAccountValue.getAcctPayMethod());
					String acctPayMethod = Integer.toString(cmAccountValue.getAcctPayMethod());
					
					IBOBsStaticDataValue acctPayMethodDesc = StaticDataCacheGetter.
								getBsStaticDataByTypeAndValue(CmConstants.CmStaticDataCodeType.ACCOUNT_PAY_METHOD, acctPayMethod);
					
					String acctPayMethodDisplay = "";
					if (acctPayMethodDesc != null){
						acctPayMethodDisplay = acctPayMethodDesc.getCodeName();
					}
					temp.setAcctPayMethodDesc(acctPayMethodDisplay);
					
					temp.setCountyId(cmAccountValue.getCountyId());
					temp.setOldAcctId(-1);
				}
			}
		}
		*//**
		 * patch code end
		 *//*
		
		return acctRelInfos;
	}*/
	
	
	public static Map preDealProcParam(String procParam)throws Exception{
		Map map = new HashMap();
		if(StringUtils.isNotBlank(procParam)){
			String[] params = procParam.split(AmsConst.COMMON.AM_PARA_SEPARATOR);
			int length = 0;
			if(params!=null && (length = params.length)>0){
				for(int i=0;i<length;i++){
					String temp = params[i];
					if(StringUtils.isNotBlank(temp)){
						String[] tempArray = temp.split("=");
						
						if(tempArray!=null && tempArray.length==2){
							map.put(tempArray[0], tempArray[1]);
						}
					}
				}
			}
		}
		return map;
	}
	
	public static long fmNumber(long orgiNum,int numLength,long offset)throws Exception{
		long num = (long) Math.pow(10, numLength)-1;
		orgiNum += offset;
		
		long num2 = (long) Math.pow(10, Long.toString(orgiNum).length())-1;
		return num - num2 + orgiNum;
	}
	
	public static String getChannelTypeByBusinessId(long businessId)throws Exception{
		//TODO
		return "";
	}

	
	/**
	 * 根据一个银行短号判断对应的省级银行信息是否存在 
	* @Function: AmsUtil.java
	* @Description: 
	*
	* @param bankCodeType
	* @return
	* @throws Exception
	*
	* @version: v1.0.0
	* @author: yanrg
	* @date: May 28, 2011 10:52:35 AM 
	*
	* Modification History:
	* Date         		Author          	 Version            Description
	*------------------------------------------------------------------------*
	* May 28, 2011     		yanrg              v1.0.0
	 */
	/*public static boolean checkBankIsExistByBankCodeType(String bankCodeType)throws Exception{
		boolean retValue = false;
		IBOBsBankValue bankValue = BsAmsBankInfoValueCacheGetter.getProvinceBankByBankCodeType(bankCodeType);
		if(bankValue!=null){
			retValue = true;
		}
		return retValue;
	}*/
	
	/**
	 * 判断一个业务是否需要记录营业报表
	 * 
	* @Function: AmsUtil.java
	* @Description: 
	*
	* @param businessId
	* @return
	* @throws Exception
	*
	* @version: v1.0.0
	* @author: yanrg
	* @date: May 21, 2011 11:06:08 AM 
	*
	* Modification History:
	* Date         		Author          	 Version            Description
	*------------------------------------------------------------------------*
	* May 21, 2011     		yanrg              v1.0.0
	 */
	/*public static boolean checkIsNeed2Report(long businessId,long optOrgId)throws Exception{
		boolean retValue = false;
		
		if(!checkIsProvinceOperatorByOrgId(optOrgId)){
			IBOBsOperationValue optValue = BsOperationCacheGetter.getBsOperationValue(businessId);
			if(optValue!=null && StringUtils.isNotBlank(optValue.getReportFlag()) &&
					optValue.getReportFlag().equalsIgnoreCase(AmsConst.COMMON.BUSSNESS_ID_REPORT_FLAG_YES)){
				retValue = true;
			}
		}
		return retValue;
	}*/
	
	/**
	 * 
	 * 根据组织ID判断是否是省级工号
	 * 
	* @Function: AmsUtil.java
	* @Description: 
	*
	* @param orgId
	* @return
	* @throws Exception
	*
	* @version: v1.0.0
	* @author: yanrg
	* @date: May 17, 2011 11:19:52 PM 
	 */
	public static boolean checkIsProvinceOperatorByOrgId(long orgId)throws Exception{
		return orgId== Constants.PROVINCE_ORG_ID;
	}
	
	/**
	 * 
	 * 根据地市标识判断工号是否是省级工号
	 * 
	* @Function: AmsUtil.java
	* @Description: 
	*
	* @param regionId
	* @return
	* @throws Exception
	*
	* @version: v1.0.0
	* @author: yanrg
	* @date: May 17, 2011 11:37:40 PM 
	 */
	/*public static boolean checkIsProvinceOperatorByRegionId(String regionId)throws Exception{
		if(StringUtils.isBlank(regionId)){
			ExceptionUtil.throwBusinessException("AMS0700255");
		}
		IBOBsParaDetailValue paraDetialValue = BsParaDetailCacheGetter.
					getParaDetailValue(AmsConst.PARA_DETAIL.PARA_DETAIL_REGION_X, AmsConst.PARA_DETAIL.PARA_TYPE_AM_PAYMENT, AmsConst.PARA_DETAIL.PARA_CODE_REGION_ID_PROVINCE);

		if(paraDetialValue==null || StringUtils.isBlank(paraDetialValue.getPara1().trim())){
			ExceptionUtil.throwBusinessException("AMS0700256",AmsConst.PARA_DETAIL.PARA_DETAIL_REGION_X,AmsConst.PARA_DETAIL.PARA_TYPE_AM_PAYMENT,AmsConst.PARA_DETAIL.PARA_CODE_REGION_ID_PROVINCE);
		}
		
		return regionId.equalsIgnoreCase(paraDetialValue.getPara1().trim());
	}*/
	
	/**
	 * 
	 * 获取省级工号地市标识
	 * 
	* @Function: AmsUtil.java
	* @Description: 
	*
	* @return
	* @throws Exception
	*
	* @version: v1.0.0
	* @author: yanrg
	* @date: May 18, 2011 10:55:21 AM 
	 */
	public static String getProvinceRegionId()throws Exception{
		IBOBsParaDetailValue paraDetialValue = BsParaDetailCacheGetter.
		getParaDetailValue(AmsConst.PARA_DETAIL.PARA_DETAIL_REGION_X, AmsConst.PARA_DETAIL.PARA_TYPE_AM_PAYMENT, AmsConst.PARA_DETAIL.PARA_CODE_REGION_ID_PROVINCE);

		if(paraDetialValue==null || StringUtils.isBlank(paraDetialValue.getPara1().trim())){
			ExceptionUtil.throwBusinessException("AMS0700256", AmsConst.PARA_DETAIL.PARA_DETAIL_REGION_X, AmsConst.PARA_DETAIL.PARA_TYPE_AM_PAYMENT, AmsConst.PARA_DETAIL.PARA_CODE_REGION_ID_PROVINCE);
		}

		return paraDetialValue.getPara1().trim();
	}
	
	/**
	 *  判断一个业务编码是否可以异地办理     
	 * @param businessId    bs_operation  业务编码
	 * @return				该业务编码对应的异地业务编码信息
	 *						如果返回值为 null 则表示该业务编码不支持异地业务
	 *						如果返回值不为 null ，则需要用返回的异地业务编码信息中的 business_id 代替 原来业务编码
	 * @throws Exception
	 * @throws RemoteException
	 */
	/*public static IBOBsOperationValue checkBusinessSpanAble(long businessId)throws Exception,RemoteException{
		IBOBsOperationValue bsOperationValue = BsOperationCacheGetter.getBsOperationValue(businessId);
		if(bsOperationValue==null){
			ExceptionUtil.throwBusinessException("AMS0700151", Long.toString(businessId));
		}

		String bsSpanFlag = bsOperationValue.getSpanFlag();
		long spanBusiId = bsOperationValue.getSpanBusinessId();
		IBOBsOperationValue spanOperationValue = BsOperationCacheGetter.getBsOperationValue(spanBusiId);

		if(AmsConst.COMMON.BS_OPERATION_SPAN_FLAG_ABLE.equalsIgnoreCase(bsSpanFlag) && spanBusiId>0 && spanOperationValue!=null){
			return spanOperationValue;
		}

		return null;
	}*/

	/**
	 * 是否需要计算滞纳金
	 * 判断顺序
	 * 		1： 该 businessId 是否需要计算滞纳金
	 * 		2： 如果是专款缴费，判断是否需要计算滞纳金
	 *  	3： 该地市是否需要计算滞纳金
	 * 并且 这三种判断中 只要有一种情况不计算滞纳金，该方法返回 false 不计算
	* @Function: AmsUtil.java
	* @Description:
	*
	* @return
	* @throws Exception
	 */
	public static boolean isCalculateLateFee(String acctRegionId,long businessId,String spePayType)throws Exception{

		if(StringUtils.isBlank(acctRegionId)){
			ExceptionUtil.throwBusinessException("AMS0700199");
		}
		boolean isCountLatefee = true;

		// 判断该业务是否需要计算违约金
		if(businessId>0){
			IBOBsParaDetailValue paraDetialValue = BsParaDetailCacheGetter.
					getParaDetailValue(acctRegionId, AmsConst.PARA_DETAIL.PARA_TYPE_AM_PAYMENT, AmsConst.PARA_DETAIL.PARA_CODE_IS_COUNT_LATE_FEE);
			if(paraDetialValue==null){
				paraDetialValue = BsParaDetailCacheGetter.
					getParaDetailValue(AmsConst.PARA_DETAIL.PARA_DETAIL_REGION_X, AmsConst.PARA_DETAIL.PARA_TYPE_AM_PAYMENT, AmsConst.PARA_DETAIL.PARA_CODE_IS_COUNT_LATE_FEE);
			}
			if(paraDetialValue!=null){
				String busiIds = paraDetialValue.getPara1();
				if(StringUtils.isNotBlank(busiIds) && busiIds.indexOf(Long.toString(businessId))!=-1){
					isCountLatefee = false;
				}
			}
		}


		/**
		 * 判断 缴纳专款时，是否计算滞纳金
		 */
		if(isCountLatefee && StringUtils.isNotBlank(spePayType) && !spePayType.equalsIgnoreCase(AmsConst.BALANCE.SPE_PAY_TYPE_COMMON)){
			IBOBsParaDetailValue paraDetialValue2 = BsParaDetailCacheGetter.getParaDetailValue(acctRegionId, AmsConst.PARA_DETAIL.PARA_TYPE_AM_PAYMENT, AmsConst.PARA_DETAIL.PARA_CODE_SPE_PAY_LATE_FEE);
			if(paraDetialValue2==null){
				paraDetialValue2 = BsParaDetailCacheGetter.getParaDetailValue(AmsConst.PARA_DETAIL.PARA_DETAIL_REGION_X, AmsConst.PARA_DETAIL.PARA_TYPE_AM_PAYMENT, AmsConst.PARA_DETAIL.PARA_CODE_SPE_PAY_LATE_FEE);
			}
			if(paraDetialValue2!=null){
				String speLateFeeFlag = paraDetialValue2.getPara1();
				if(StringUtils.isNotBlank(speLateFeeFlag) && speLateFeeFlag.equalsIgnoreCase("N")){
					isCountLatefee = false;
				}
			}
		}


		/**
		 * 判断该地市是否需要计算滞纳金
		 */
		IBOBsParaDetailValue paraDetialRegion = BsParaDetailCacheGetter.getParaDetailValue(acctRegionId, AmsConst.PARA_DETAIL.PARA_TYPE_AM_LATE_FEE, AmsConst.PARA_DETAIL.PARA_CODE_CALCULATE_FLAG);
		if(paraDetialRegion==null){
			paraDetialRegion = BsParaDetailCacheGetter.getParaDetailValue(AmsConst.PARA_DETAIL.PARA_DETAIL_REGION_X, AmsConst.PARA_DETAIL.PARA_TYPE_AM_LATE_FEE, AmsConst.PARA_DETAIL.PARA_CODE_CALCULATE_FLAG);
		}
		if(paraDetialRegion!=null){
			String speLateFeeFlag = paraDetialRegion.getPara1();
			if(StringUtils.isNotBlank(speLateFeeFlag) && speLateFeeFlag.equalsIgnoreCase("N")){
				isCountLatefee = false;
			}
		}

		return isCountLatefee;
	}


	/**
	 * 得到指定时间戳的字符串格式时间以 YYYYMMDDHH24MISS 格式返回
	 * HH  0--23
	 * kk  1-24
	 * @param ts
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 */
	public static String getStrDate(Timestamp ts)throws Exception,RemoteException{
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(ts);
	}

	/**
	 * 得到指定时间戳的字符串格式时间以 YYYYMMDD格式返回
	 * @param ts
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 */
	public static String getStrDateYMD(Timestamp ts)throws Exception,RemoteException{
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(ts);
	}

	public static String getStrDateYM(Timestamp ts)throws Exception,RemoteException{
		DateFormat format = new SimpleDateFormat("yyyyMM");
		return format.format(ts);
	}

	public static boolean checkYYYYMMDD(String str)throws Exception{
		boolean retValue = false;
		if(StringUtils.isNotBlank(str)){
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				format.parse(str);
				retValue = true;
			} catch (Exception e) {

			}
		}
		return retValue;
	}

	public static boolean checkYYYYMM(String str)throws Exception{
		boolean retValue = false;
		if(StringUtils.isNotBlank(str)){
			DateFormat format = new SimpleDateFormat("yyyyMM");
			try {
				format.parse(str);
				retValue = true;
			} catch (Exception e) {

			}
		}
		return retValue;
	}

	/**
	 * 通过传入的 bobean 类型对象 获取对应的sequence
	 * 不能传入查询BO
	 * <p> @Description </p>
	 * <p> @author 闫瑞国 </p>
	 * <p> @createDate Apr 14, 20106:01:26 PM </p>
	 * <p> @modifyDate </p>
	 * @param boBeanClass   	如： BOAmPaymentBean.class
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 */
	/*public static long getNewSequence(Class boBeanClass)throws Exception,RemoteException{
		ICommonSV commonSV = (ICommonSV) ServiceFactory.getService(ICommonSV.class);
		return commonSV.getNewSequence(boBeanClass);
	}*/

	public static String getTableName(Class boBeanClass,boolean dropTableSplitIdentifier)throws Exception{
		String tableName = "";
		if(boBeanClass!=null){
		    if (!ClassUtils.isAssignable(boBeanClass, DataContainerInterface.class)) {
		    	ExceptionUtil.throwBusinessException("AMS0700055", boBeanClass.getName(), DataContainerInterface.class.getName());
		    }
			ObjectType objectType = ServiceManager.getObjectTypeFactory().getObjectTypeByClass(boBeanClass);
			String mapingEntyType = objectType.getMapingEntyType();
			if(StringUtils.isBlank(mapingEntyType)){
				ExceptionUtil.throwBusinessException("AMS0700057");
			}else if( AmsConst.COMMON.BO_MAPING_ENTY_TYPE_QUERY.equalsIgnoreCase(mapingEntyType)){
				ExceptionUtil.throwBusinessException("AMS0700058");
			}else{
				String mapingEnty = objectType.getMapingEnty();

				if(dropTableSplitIdentifier){
					if(StringUtils.isNotBlank(mapingEnty)){
						if("{".equalsIgnoreCase(mapingEnty.substring(0, 1))){
							mapingEnty = mapingEnty.substring(1);
						}
					}

					if(StringUtils.isNotBlank(mapingEnty)){
						if("}".equalsIgnoreCase(mapingEnty.substring(mapingEnty.length()-1))){
							mapingEnty = mapingEnty.substring(0,mapingEnty.length()-1);
						}
					}
				}
				tableName = mapingEnty;
			}

		}else{
			ExceptionUtil.throwBusinessException("AMS0700056");
		}
		return tableName;
	}

	/**
	 *
	* @Function: AmsUtil.java
	* @Description: 得到数据库系统时间
	*
	* @return
	* @throws Exception
	* @throws RemoteException
	*
	* @version: v1.0.0
	* @author: yanrg
	* @date: Feb 25, 2011 11:21:27 AM
	*
	* Modification History:
	* Date         		Author          	 Version            Description
	*------------------------------------------------------------------------*
	* Feb 25, 2011     		yanrg              v1.0.0
	 */
//	public static Timestamp getDBSysdate()throws Exception,RemoteException{
//		IBaseSV baseSV = (IBaseSV) ServiceFactory.getService(IBaseSV.class);
//		return baseSV.getDBSysdate();
//	}




	/**
	 * 关闭JDBC资源
	 * <p> @Description </p>
	 * <p> @author 闫瑞国 </p>
	 * <p> @createDate Apr 14, 20106:01:26 PM </p>
	 * <p> @modifyDate </p>
	 * @param conn
	 * @param sta
	 * @param rs
	 */
	public static void closeJDBC(Connection conn,Statement sta,ResultSet rs){

		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				log.error("There is an Exception thrown when : "
	                    + (e.getCause() == null ? e.getMessage() : e.getCause().getMessage()),e);
			}
		}

		if(sta!=null){
			try {
				sta.close();
			} catch (SQLException e) {
				log.error("There is an Exception thrown when : "
	                    + (e.getCause() == null ? e.getMessage() : e.getCause().getMessage()),e);
			}
		}

		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("There is an Exception thrown when : "
	                    + (e.getCause() == null ? e.getMessage() : e.getCause().getMessage()),e);
			}
		}

	}



	/**
	 * 得到账本默认生效时间
	 * <p> @Description </p>
	 * <p> @author 闫瑞国 </p>
	 * <p> @createDate Apr 14, 20106:01:26 PM </p>
	 * <p> @modifyDate </p>
	 * @return
	 * @throws Exception
	 */
	public static Timestamp getDefaultEffectiveDate()throws Exception{
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strEffective = "1900-1-1 00:00:00";
		Timestamp effectiveTimestamp = new Timestamp(dateformat.parse(strEffective).getTime());
		return effectiveTimestamp;
	}

	public static Timestamp getTimestampByStr(String strTime,String formatStr)throws Exception{
		DateFormat dateformat = new SimpleDateFormat(formatStr);
		Timestamp timestamp = new Timestamp(dateformat.parse(strTime).getTime());
		return timestamp;
	}

	/**
	 * 得到账本默认失效时间
	 * <p> @Description </p>
	 * <p> @author 闫瑞国 </p>
	 * <p> @createDate Apr 14, 20106:01:26 PM </p>
	 * <p> @modifyDate </p>
	 * @return
	 * @throws Exception
	 */
	public static Timestamp getDefaultExpireDate()throws Exception{
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strExpire = "2099-12-31 23:59:59";
		Timestamp ExpireTimestamp = new Timestamp(dateformat.parse(strExpire).getTime());
		return ExpireTimestamp;
	}

	/**
	 *
	* @Function: AmsUtil.java
	* @Description:  将给定的时间取整
	*
	* @param date
	* @return
	*
	* @version: v1.0.0
	* @author: yanrg
	* @date: Feb 25, 2011 4:54:34 PM
	*
	* Modification History:
	* Date         		Author          	 Version            Description
	*------------------------------------------------------------------------*
	* Feb 25, 2011     		yanrg              v1.0.0
	 */
	public static Timestamp truncDate(Date date) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.set(Calendar.HOUR_OF_DAY,0);
		rightNow.set(Calendar.MILLISECOND,0);
		rightNow.set(Calendar.SECOND,0);
		rightNow.set(Calendar.MINUTE,0);
		return new Timestamp(rightNow.getTimeInMillis());
	}

	/**
	 * @Function: monthCross
	 * @Description: 计算两个时间月份跨度
	 *
	 * @param date1
	 * @param date2
	 * @return
	 *
	 * @version: v1.0.0
	 * @author: lixh6
	 * @date: May 26, 2011 5:31:28 PM
	 *
	 * Modification History:
	 * Date         Author          Version            Description
	 *---------------------------------------------------------*
	 */
	public static int monthCross(Date date1, Date date2){
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int n1 = cal1.get(Calendar.YEAR)*12 + cal1.get(Calendar.MONTH);
		int n2 = cal2.get(Calendar.YEAR)*12 + cal2.get(Calendar.MONTH);
		return n2-n1+1;

	}

	/*
	 * 当程序出现异常时，数据库回滚，写到FTP上的文件则要删除，本方法即为删除FTP上文件
	 */
	public static void deleteFile(String ftpPathCode,String fileName)throws Exception,RemoteException{
		FtpUtil ftp = null;
		try{
			if(StringUtils.isNotBlank(fileName)){
				ftp = new FtpUtil(ftpPathCode);
				ftp.delete(fileName);
			}
		}catch(Exception e){
			log.error(e);
		}finally{
			if(ftp!=null){
				try{
					ftp.close();
				}catch(Exception e){
					log.error(e);
				}
			}
		}

	}

	/**
	 * 告警空对象
	 * <p> @Description </p>
	 * <p> @author YIYS </p>
	 * <p> @param obj 对象  </p>
	 * <p> @param objDesc 对象描述  </p>
	 * <p> @createDate 1 24, 2011 6:01:26 PM </p>
	 * <p> @modifyDate </p>
	 * @return
	 * @throws Exception
	 */
	public static void alarmNullObject(Object obj,String objDesc, Log logObj)throws Exception{
		if(obj == null) {
			String objDescirbeTmp=objDesc;
			if(objDescirbeTmp == null) {
				objDescirbeTmp = "";
			}
			if(logObj == null) {
				log.error(CrmLocaleFactory.getResource("AMS2700053", objDescirbeTmp));
			}else {
				logObj.error(CrmLocaleFactory.getResource("AMS2700053", objDescirbeTmp));
			}

			ExceptionUtil.throwBusinessException("AMS2700053", objDescirbeTmp);
		}
	}

	/**
	 *
	* @Function: AmsUtil.java
	* @Description: 连接两个字符串，指定连接符
	*
	* @param srcString
	* @param joinString
	* @param separator
	* @return
	* @throws Exception
	*
	* @version: v1.0.0
	* @author: yanrg
	* @date: Mar 9, 2011 3:19:36 PM
	 */
	public static String joinString(String srcString,String joinString,String separator)throws Exception{
		if(StringUtils.isBlank(srcString)){
			if(StringUtils.isBlank(joinString)){
				return null;
			}else {
				return joinString;
			}
		}else {
			if(StringUtils.isBlank(joinString)){
				return srcString;
			}else {
				if(StringUtils.isBlank(separator)){
					return srcString.concat(joinString);
				}else {
					if(srcString.lastIndexOf(separator)==srcString.length()-separator.length()){
						return srcString.concat(joinString);
					}else {
						return srcString.concat(separator).concat(joinString);
					}
				}
			}
		}

	}

	/**
	 * 对返回的静态数据根据 SORT_ID 字段进行排序
	 * @author yanrg
	 *
	 */
	public static IBOBsStaticDataValue[] getStaticData(String codeType)throws Exception{
		IBOBsStaticDataValue[] bsStaticDataValues = StaticDataUtil.getStaticData(codeType);
		if(bsStaticDataValues!=null && bsStaticDataValues.length>0){
			Arrays.sort(bsStaticDataValues, new BsStaticDataArrayComparator());
			return bsStaticDataValues;
		}
		return null;
	}

	public static Timestamp getDBSysdate() throws Exception {
		return TimeUtil.getPrimaryDataSourceSysDate();
	}

	static class BsStaticDataArrayComparator implements Comparator{

		public int compare(Object o1, Object o2) {
			IBOBsStaticDataValue staticData1 = (IBOBsStaticDataValue)o1;
			IBOBsStaticDataValue staticData2 = (IBOBsStaticDataValue)o2;
			if(staticData1.getSortId()>staticData2.getSortId()){
				return 1;
			}else if(staticData1.getSortId()<staticData2.getSortId()){
				return -1;
			}
			return 0;
		}
	}

	/**
	 * 对返回用于DS的静态数据根据,按 SORT_ID 字段进行排序,
	 * @param withAllKey 为空串或null则不增加 "所有银行"这样的记录,否则增加
	 * @param withAllKeyName 为空串或null 则使用默认配置，否则使用该值
	 *
	 */
	public static IBOBsStaticDataValue[] getStaticDataWithWholeOpt4Ds(
			String codeType, String withAllKey, String withAllKeyName)throws Exception {
		if(StringUtils.isNotBlank(withAllKey)) {
			IBOBsStaticDataValue[] values = getStaticData(codeType);
			if(values!=null && values.length>0){
				Vector vList = new Vector();
				BOBsStaticDataBean forAllValue = new BOBsStaticDataBean();
				forAllValue.setCodeValue(withAllKey.trim());
				if (StringUtils.isNotBlank(withAllKeyName)) {
					forAllValue.setCodeName(withAllKeyName);
				}
				else {
					forAllValue.setCodeName(CrmLocaleFactory.getResource("AMS2700303"));//全部
				}

				vList.addElement(forAllValue);
				for(int i=0;i < values.length;i++) {
					vList.addElement(values[i]);
				}
				return (IBOBsStaticDataValue[]) vList.toArray(new IBOBsStaticDataValue[0]);
			}
			return values;
		}
		return getStaticData(codeType);
	}

	/**
	 * @Function: getAccOweTimeDefBanks
	 * @Description: 根据地市编码取得催缴时间设置所配置的银行数据。
	 * 如果regionId为null，或者“”，或者“-1”则表示取全部银行，包括增加一个“全部”的值为“-1”的数据。
	 * 否则只返回全省范围及该地区的银行。
	 * 对返回的静态数据根据 SORT_ID 字段进行排序。
	 *
	 * @param regionId 地区编码
	 * @return
	 * @throws Exception
	 *
	 * @version: v1.0.0
	 * @author: lixh6
	 * @date: Apr 1, 2011 8:57:10 PM
	 *
	 * Modification History:
	 * Date         Author          Version            Description
	 *---------------------------------------------------------*
	 */
	public static IBOBsStaticDataValue[] getAccOweTimeDefBanks(String regionId)throws Exception{
		IBOBsStaticDataValue[] bsStaticDataValues = StaticDataUtil.getStaticData(AmsConst.STATIC_DATA.CODE_TYPE_ACC_OWE_TIME_DEF_BANKS);
		if(bsStaticDataValues==null){
			bsStaticDataValues = new IBOBsStaticDataValue[0];
		}
		ArrayList list = new ArrayList();

		IBOBsStaticDataValue all = new BOBsStaticDataBean();
		all.setCodeName(CrmLocaleFactory.getResource("AMS0000014"));//全部
		all.setSortId(-9999999);
		all.setCodeDesc("");//
		all.setCodeValue("-1");//
		list.add(all);

		for(int i=0;i<bsStaticDataValues.length;i++){
			if(StringUtils.isNotBlank(regionId)){//地市
				String region = bsStaticDataValues[i].getExternCodeType();
				if(StringUtils.isNotBlank(region) && !"0".equals(region) && !regionId.equals(region)){//不是全省且不是该地区的
					continue;
				}
			}
			list.add(bsStaticDataValues[i]);
		}
		IBOBsStaticDataValue[] values = (IBOBsStaticDataValue[])list.toArray(new IBOBsStaticDataValue[0]);
		Arrays.sort(values, new BsStaticDataArrayComparator());
		return values;
	}

	/**
	 * <p> @Description </p>
	 * <p> @author 闫瑞国 </p>
	 * <p> @createDate Apr 14, 20106:01:26 PM </p>
	 * <p> @modifyDate </p>
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		//CenterFactory.setCenterInfoByTypeAndValue(CenterConst.REGION_ID, "571");
		/*DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strEffective = "1900-1-2 00:00:00";
		Timestamp effectiveTimestamp = new Timestamp(dateformat.parse(strEffective).getTime());

		DateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd");
		String strEffective2 = "1900-1-3";
		Timestamp effectiveTimestamp2 = new Timestamp(dateformat2.parse(strEffective2).getTime());

		System.out.println(effectiveTimestamp.compareTo(effectiveTimestamp2));

		System.out.println(TimeUtil.daysBetween(effectiveTimestamp, effectiveTimestamp2));*/

		/*System.out.println(getDefaultEffectiveDate());
		System.out.println(getDefaultExpireDate());*/



		/*DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String strExpire = "2013-2-25 01:00:00";
		Timestamp ExpireTimestamp = new Timestamp(dateformat.parse(strExpire).getTime());

		String strEffective = "2014-2-25 23:30:40";
		Timestamp effectiveTimestamp = new Timestamp(dateformat.parse(strEffective).getTime());

		System.out.println(TimeUtil.yearsBetween(effectiveTimestamp, ExpireTimestamp));*/

		//System.out.println(truncDate(getDefaultExpireDate()));

		/*DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(dateformat.format(getDBSysdate()));
		Timestamp now = new Timestamp(System.currentTimeMillis());
		System.out.println(dateformat.format(now));*/
		/*DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String strExpire = "2013-2-25 23:59:59";
		Timestamp ExpireTimestamp = new Timestamp(dateformat.parse(strExpire).getTime());
		System.out.println(getStrDate(ExpireTimestamp));*/
		//System.out.println(isCalculateLateFee("571", 123, "0"));
		///System.out.println(checkBankIsExistByBankCodeType("30"));

		//System.out.println(fmNumber(1000,6,1234));

		//System.out.println(checkYYYYMMDD("2011-12-28"));
		System.out.println(isZJProvinceECard("11515110051682173"));
	}

	public static IBOBsStaticDataValue[] getIndepFeeDtlTypes() throws Exception{
		IBOBsStaticDataValue[] values = StaticDataUtil.getStaticData(AmsConst.STATIC_DATA.INDEP_FEE_DTL_TYPE);
		if(values==null || values.length<1){
			return new IBOBsStaticDataValue[0];
		}
		return values;
	}

	/**
	 * @Function: getCenterRegions
	 * @Description: 返回各中心的代表regionId，一个中心返回一个regionId,存在多个的只返回一个
	 *
	 * @param cutOverOnly 是否只返回割接的中心
	 * @return
	 * @throws Exception
	 *
	 * @version: v1.0.0
	 * @author: lixh6
	 * @date: Apr 6, 2011 3:23:24 PM
	 *
	 * Modification History:
	 * Date         Author          Version            Description
	 *---------------------------------------------------------*
	 */
//	public static String[] getCenterRegionIds(boolean cutOverOnly) throws Exception {
//		IBOBsDistrictValue[] regions = DistrictUtil.getRegion();
//		HashMap centerRegionMap = new HashMap();
//		if(regions!=null && regions.length>0){
//			for(int i=0;i<regions.length;i++){
//				if(cutOverOnly && !AmsUtil.isCursedDistrict(regions[i])){//如果要求只获取已割接的，则对未割接地市不处理
//					continue;
//				}
//				if(centerRegionMap.containsKey(regions[i].getCenterInfoCode())){
//					continue;
//				}
//				centerRegionMap.put(regions[i].getCenterInfoCode(), regions[i].getRegionId());
//			}
//		}
//		return (String[])centerRegionMap.values().toArray(new String[0]);
//	}


	/**
	 * @Function: isDateString
	 * @Description: 判断该字符串是否正常的日期的yyyyMMdd格式
	 *
	 * @param dateStr yyyyMMdd字符串
	 * @return
	 *
	 * @version: v1.0.0
	 * @author: lixh6
	 * @date: Mar 30, 2011 7:51:33 PM
	 *
	 * Modification History:
	 * Date         Author          Version            Description
	 *---------------------------------------------------------*
	 */
	public static boolean isDateString(String dateStr){
		if(StringUtils.isBlank(dateStr) || !StringUtils.isNumeric(dateStr) || dateStr.length()!=8){
			return false;
		}
		Calendar cal = Calendar.getInstance();
		int year = Integer.parseInt(dateStr.substring(0,4));
		int month = Integer.parseInt(dateStr.substring(4,6));
		if(month>12 || month==0){//不可能是负值
			return false;
		}
		int date = Integer.parseInt(dateStr.substring(6,8));
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DATE, 1);
		int lastDate = cal.getActualMaximum(Calendar.DATE);
		if(date>lastDate){
			return false;
		}
		return true;
	}

	/*
	 * Modification History:
	 * Date         Author          Version            Description
	 *---------------------------------------------------------*
	 */
	public static Date parseDate(String dateStr){
		if(StringUtils.isBlank(dateStr) || !StringUtils.isNumeric(dateStr) || dateStr.length()!=8){
			return null;
		}
		int value = Integer.parseInt(dateStr);
		int date = value %100;
		value = value/100;
		int month = value %100;
		int year = value/100;
		if(month>12 || month==0){//不可能是负值
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DATE, 1);
		int lastDate = cal.getActualMaximum(Calendar.DATE);
		if(date>lastDate){
			return null;
		}
		cal.set(Calendar.DATE, date);
		return cal.getTime();
	}

	public static Date parseNewDate(String dateStr){
		if(StringUtils.isBlank(dateStr) || !StringUtils.isNumeric(dateStr)){
			return null;
		}
		int value = Integer.parseInt(dateStr);
		int date = value %100;
		value = value/100;
		int month = value %100;
		int year = value/100;
		if(month>12 || month==0){//不可能是负值
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, 1);
		int lastDate = cal.getActualMaximum(Calendar.DATE);
		if(date>lastDate){
			return null;
		}
		cal.set(Calendar.DATE, date);
		return cal.getTime();
	}

	/**
	 *
	 * Modification History:
	 * Date         Author          Version            Description
	 *---------------------------------------------------------*
	 */
	public static Date parseDatetime(String datetimeStr){
		if(StringUtils.isBlank(datetimeStr) || !StringUtils.isNumeric(datetimeStr) || datetimeStr.length()!=14){
			return null;
		}

		int date = Integer.parseInt(datetimeStr.substring(0,8));
		int time = Integer.parseInt(datetimeStr.substring(8));

		int second = time % 100;
		if (second >59 || second <0) {
			return null;
		}
		time = time /100;
		int minute = time%100;
		if ( minute > 59 || minute < 0)  {
			return null;
		}

		int hour = time/100;
		if (hour >23 || hour < 0 ) {
			return null;
		}

		int day = date % 100;
		date = date /100;
		int month = date%100;
		if (month > 12 || month < 1) {
			return null;
		}

		int year = date/100;

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);

		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		if(day>lastDay){
			return null;
		}

		cal.set(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	/**
	 * @Function: getFormatDateString
	 * @Description: 根据指定格式返回格式化后的时间字符串
	 *
	 * @param date 时间
	 * @param formatStr 格式化字符串
	 * @return
	 *
	 * @version: v1.0.0
	 * @author: lixh6
	 * @date: Mar 19, 2011 2:33:03 PM
	 *
	 * Modification History:
	 * Date         Author          Version            Description
	 *---------------------------------------------------------*
	 */
	public static String getFormatDateString(Date date, String formatStr){
		try{
			DateFormat format = new SimpleDateFormat(formatStr);
			return format.format(date);
		}catch(Exception e){
			return "";
		}
	}

	/**
	 * @Function: getAirPayDefaultRegionId
	 * @Description: 获取配置的空中充值相关表存放的固定中心regionId
	 *
	 * @return
	 * @throws Exception
	 *
	 * @version: v1.0.0
	 * @author: lixh6
	 * @date: May 4, 2011 5:47:29 PM
	 *
	 * Modification History:
	 * Date         Author          Version            Description
	 *---------------------------------------------------------*
	 */
	public static String getAirPayDefaultRegionId() throws Exception{
		IBOBsStaticDataValue[] values = StaticDataUtil.getStaticData(AmsConst.STATIC_DATA.AIR_PAY_DEFAULT_REGION_ID);
		if(values==null || values.length!=1){
			ExceptionUtil.throwBusinessException("AMS0005004");//未配置空中充值固定中心的地市编码
		}
		return values[0].getCodeValue();
	}

	public static String getBankFlowDefaultRegionId() throws Exception{
		IBOBsStaticDataValue[] values = StaticDataUtil.getStaticData(AmsConst.STATIC_DATA.BANK_FLOW_DEF_DEFAULT_REGION_ID);
		if(values==null || values.length!=1){
			ExceptionUtil.throwBusinessException("AMS0350341");//未配置BANK_FLOW_DEF固定中心的地市编码
		}
		return values[0].getCodeValue();
	}

	public static String getSmsNoticeDefaultRegionId() throws Exception{
		IBOBsStaticDataValue[] values = StaticDataUtil.getStaticData(AmsConst.STATIC_DATA.SMS_NOTICE_DEFAULT_REGION_ID);
		if(values==null || values.length!=1){
			ExceptionUtil.throwBusinessException("AMS0350392");//未配置短信回执表固定中心的地市编码！
		}
		return values[0].getCodeValue();
	}

//	/**
//	 *
//	 * @Function: busiIdGenerator
//	 * @Description: 银行业务记录标识，批量缴费中，XXX(3位地市)+YYYYMMDDHHMMSS+XXXXXXXX(8位流水号)
//	 *
//	 * @param regionId
//	 * @return
//	 * @throws Exception
//	 *
//	 * @version: v1.0.0
//	 * @author: chenyf3
//	 * @date: Apr 22, 2011 8:02:31 AM
//	 *
//	 * Modification History:
//	 * Date         Author          Version            Description
//	 *---------------------------------------------------------*
//	 *Apr 22, 2011       chenyf3         v1.0.0
//	 */
//	public static String busiIdGenerator(String regionId) throws Exception {
//		if(regionId==null||regionId.trim().length()!=3){
//			regionId="000";
//		}
//		DateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
//		String dateStr = dateformat.format(getDBSysdate());
//		//CenterFactory.setCenterInfoByTypeAndValue(AmsConst.COMMON.CENTER_TYPE_REGION_ID, "571");//设置中心？？暂时这样处理
//		IAmFinBusiRecDAO busiDAO = (IAmFinBusiRecDAO) ServiceFactory.getService(IAmFinBusiRecDAO.class);
//		//String seq = String.valueOf(ServiceManager.getIdGenerator().getNewId("AM_AUDIT_BUSI_REC$SEQ").longValue());
//		String seq = String.valueOf(busiDAO.getNewId().longValue());
//		int len = seq.length();
//		StringBuffer sb=new StringBuffer();
//		sb.append(regionId);
//		sb.append(dateStr);
//		for(int i=len;i<8;i++){
//			sb.append("0");
//		}
//		sb.append(seq);
//
//		return sb.toString();
//	}
//
//	/**
//	 * @Function: isRegionCutOver
//	 * @Description: 判断地市是否已割接完毕
//	 *
//	 * @param regionId
//	 * @return
//	 * @throws Exception
//	 *
//	 * @version: v1.0.0
//	 * @author: lixh6
//	 * @date: May 19, 2011 3:10:23 PM
//	 *
//	 * Modification History:
//	 * Date         Author          Version            Description
//	 *---------------------------------------------------------*
//	 */
//	public static boolean isRegionCutOver(String regionId) throws Exception {
//		IBOBsDistrictValue region = null;
//		region = DistrictUtil.getDistrictByRegionId(regionId);
//		if(region==null){
//			ExceptionUtil.throwBusinessException("AMS0000020", regionId == null ? "" : regionId);//地市编码{0}无效
//		}
//		return isCursedDistrict(region);
//	}
//	/**
//	 *
//	 * 根据服务号码判断该服务号码所在地市是否已经割接完成
//	 *
//	* @Function: AmsUtil.java
//	* @Description:
//	*
//	* @param billId
//	* @return
//	* @throws Exception
//	*
//	* @version: v1.0.0
//	* @author: yanrg
//	* @date: Jun 13, 2011 7:09:30 PM
//	*
//	* Modification History:
//	* Date         		Author          	 Version            Description
//	*------------------------------------------------------------------------*
//	* Jun 13, 2011     		yanrg              v1.0.0
//	 */
//	public static boolean isCutOverByBillId(String billId)throws Exception{
//		boolean retValue = false;
//		String userRegionId = CenterUtil.getRegionIdByBillId(billId);
//		retValue = isRegionCutOver(userRegionId);
//		return retValue;
//	}
//
//	/**
//	 * @Function: isProvinceCutOver
//	 * @Description: 判断是否已全省割接完毕
//	 *
//	 * @return
//	 * @throws Exception
//	 *
//	 * @version: v1.0.0
//	 * @author: lixh6
//	 * @date: May 19, 2011 3:10:23 PM
//	 *
//	 * Modification History:
//	 * Date         Author          Version            Description
//	 *---------------------------------------------------------*
//	 */
//	public static boolean isProvinceCutOver() throws Exception {
//		IBOBsDistrictValue[] regions = DistrictUtil.getRegion();
//		if(regions==null || regions.length<1){
//			ExceptionUtil.throwBusinessException("AMS0000019");//未能获取到地市数据
//		}
//		for(int i=0;i<regions.length;i++){
//			if (!isCursedDistrict(regions[i])) //regions[i].getCourseFlag()!=AmsConst.COMMON.REGION_CUT_OVER_FLAG
//				return false;
//		}
//		return true;
//	}
//	/**
//	 * @Function: isCursedDistrict
//	 * @Description:判断区域是否割接
//	 *
//	 * @return：true 割了 false 没割
//	 */
//	public static boolean isCursedDistrict(IBOBsDistrictValue districtValue) {
//		if (districtValue==null) {
//			return false;
//		}
//		return (districtValue.getCourseFlag()==AmsConst.COMMON.REGION_CUT_OVER_FLAG );
//	}
//
//	/**
//	 * @Function: nullToEmptyString
//	 * @Description: null 变成空串,否则为原字符串
//	 *
//	 * @return：
//	 */
//	public static String nullToEmptyString(String src) {
//		if (src == null ) {
//			return "";
//		}
//		return src;
//	}
//
//	/**
//	 * @Function: getProvinceCourseRegions
//	 * @Description:获取所有已割接地区(全省的)
//	 *
//	 * @return：已割接了的全省地区
//	 */
//	public static String[] getProvinceCourseRegions() throws Exception {
//		IBOBsDistrictValue[] districtValues = DistrictUtil.getRegion();
//		Vector regions = new Vector(districtValues.length+1);
//		for (int i = 0; i < districtValues.length; i++) {
//			if (isCursedDistrict(districtValues[i]) ) {
//				regions.addElement(districtValues[i].getRegionId());
//			}
//		}
//		return (String[]) regions.toArray(new String[0]);
//	}
//
//	/**
//	 * @Function: doTaskVirtualUserLogin
//	 * @Description: 从bs_para_detail表（缓存）中获取相应的虚拟操作号模拟登录
//	 *
//	 * @param taskOperParaCode TASK任务虚拟操作员编码
//	 * @return
//	 * @throws Exception
//	 *
//	 * @version: v1.0.0
//	 * @author: lixh6
//	 * @date: Jun 9, 2011 8:47:45 PM
//	 *
//	 * Modification History:
//	 * Date         Author          Version            Description
//	 *---------------------------------------------------------*
//	 */
//	public static boolean doTaskVirtualUserLogin(String taskOperParaCode) throws Exception {
//		if(StringUtils.isBlank(taskOperParaCode)){
//			return false;
//		}
//		try{
//			IBOBsParaDetailValue value = BsParaDetailCacheGetter.getParaDetailValue(AmsConst.PARA_DETAIL.PARA_DETAIL_REGION_X,
//					AmsConst.PARA_DETAIL.PARA_TYPE_AM_TASK_OPERATORS, taskOperParaCode.toUpperCase());
//			if(value==null){
//				return false;
//			}
//			UserManager userManager = UserManagerFactory.getUserManager();
//			UserInfoInterface newUser = userManager.getBlankUserInfo();
//			newUser.setID(Long.parseLong(value.getPara1()));//opId
//			newUser.setOrgId(Long.parseLong(value.getPara2()));//orgId
//			newUser.set(Constants.REGION_ID, value.getPara3());//regionId
//			newUser.set(Constants.COUNTY_ID, value.getPara4());//countyId
//			ServiceManager.setServiceUserInfo(newUser);
//			if(log.isDebugEnabled()){
//				log.debug(newUser);
//			}
//			return true;
//		}catch(Exception e){
//			log.error(e);
//			return false;
//		}
//	}
//
//	/**
//	 * @Function: parseMap
//	 * @Description: 将字符串解析成HashMap对象,其中KEY将统一使用大写
//	 *
//	 * @param paramString 要解析的字符串。多组键值之间用“|”分隔，键值之间用“^”分隔，如：key1^value1|key2^value2|key3^value3
//	 * @return
//	 * @throws Exception
//	 *
//	 * @version: v1.0.0
//	 * @author: lixh6
//	 * @date: Jun 14, 2011 2:12:19 PM
//	 *
//	 * Modification History:
//	 * Date         Author          Version            Description
//	 *---------------------------------------------------------*
//	 */
//	public static HashMap parseMap(String paramString) throws Exception {
//		HashMap arrayMap = new HashMap();
//		if(StringUtils.isNotBlank(paramString)){
//			String[] conditionArray = paramString.split("\\|");
//			for (int i = 0; i < conditionArray.length; i++) {
//				int index = conditionArray[i].indexOf('^');
//				if( index > 1 ){//有分割符^并且不是在第一字符
//					arrayMap.put(conditionArray[i].substring(0, index).toUpperCase(), conditionArray[i].substring(index+1));
//				}
//			}
//		}
//		return arrayMap;
//	}
//
//	//获取静态数据KEY与值取名map
//	/**
//	 * @Function: getStaticDataValueNameMap
//	 * @Description: 从bs_static_data表（缓存）中获取CODE_TYPE对应的CODE_VALUE值与VALUE名称MAP,
//	 *               主要用于返回时将CODE_VALUE翻译成CODE_NAME
//	 *
//	 * @param taskOperParaCode TASK任务虚拟操作员编码
//	 * @return 取值及名称MAP，其中KEY 为 CODE_VALUE,VALUE 为CODE_NAME
//	 * @throws Exception
//	 *
//	 * @version: v1.0.0
//	 * @author: yiys
//	 * @date: Jun 14, 2011 8:47:45 PM
//	 *
//	 * Modification History:
//	 * Date         Author          Version            Description
//	 *---------------------------------------------------------*
//	 */
//	public static Map getStaticDataValueNameMap(String codeType) throws Exception {
//		HashMap mapOut = new HashMap();
//		if (StringUtils.isNotBlank(codeType)) {
//			IBOBsStaticDataValue[] staticDatas = StaticDataCacheGetter.getStaticDataValues(codeType);
//			if ( staticDatas != null) {
//				for (int i = 0; i < staticDatas.length; i++) {
//					mapOut.put(staticDatas[i].getCodeValue(),staticDatas[i].getCodeName());
//				}
//			}
//		}
//		return mapOut;
//	}
//
//	//获取静态数据KEY与值取名map
//	/**
//	 * @Function: getStaticDataValueNameMap2
//	 * @Description: 从bs_static_data表（缓存）中获取CODE_TYPE对应的CODE_VALUE值与VALUE名称MAP,
//	 *               主要用于返回时将CODE_VALUE翻译成CODE_NAME
//	 *               PS:支持同KEY名的数据，value值叠加
//	 *
//	 * @param codeType 编码类型
//	 * @param strAppend 同KEY名value连接字符串
//	 *
//	 * @return 取值及名称MAP，其中KEY 为 CODE_VALUE,VALUE 为CODE_NAME
//	 * @throws Exception
//	 *
//	 * @version: v1.0.0
//	 * @author: wuzx
//	 * @date: 2013.11.26
//	 *
//	 * Modification History:
//	 * Date         Author          Version            Description
//	 *---------------------------------------------------------*
//	 */
//	public static Map getStaticDataValueNameMap2(String codeType,String strAppend) throws Exception {
//		HashMap mapOut = new HashMap();
//		if (StringUtils.isNotBlank(codeType)) {
//			IBOBsStaticDataValue[] staticDatas = StaticDataCacheGetter.getStaticDataValues(codeType);
//			if ( staticDatas != null) {
//				for (int i = 0; i < staticDatas.length; i++) {
//					mapOut.put(staticDatas[i].getCodeValue(),staticDatas[i].getCodeName());
//				}
//			}
//		}
//		return mapOut;
//	}
//
//	/**
//	 * 对字符串进行模糊化处理（第一个字符正常处理保留，后面的以*代替）
//	 * @Function: getMaskedStr
//	 * @Description: 对字符串进行模糊化处理（第一个字符保留，后面的以*代替）
//	 *
//	 * @param:	str 待处理的字符串
//	 * @return：返回结果描述
//	 * @throws：异常描述
//	 *
//	 * @version: v1.0.0
//	 * @author: zhangwq
//	 * @date: 2011-6-14 下午09:33:11
//	 *
//	 * Modification History:
//	 * Date         Author          Version            Description
//	 *---------------------------------------------------------*
//	 * 2011-6-14     zhangwq           v1.0.0               修改原因
//	 */
//	public static String getMaskedStr(String str) throws Exception, RemoteException
//	{
//		if(StringUtils.isNotBlank(str))
//		{
//			StringBuilder sb = new StringBuilder();
//			sb.append(str.charAt(0));
//			for(int i=1; i < str.length(); i++)
//			{
//				sb.append("*");
//			}
//			return sb.toString();
//		}
//		return str;
//	}
//	/**
//	 *
//	 * @Function getMaskedStr2
//	 * @Description 对字符串进行模糊化处理（第二个字符保留，其余的都以*代替）
//	 *
//	 * @param str
//	 * @return
//	 * @throws Exception
//	 * @throws java.rmi.RemoteException
//	 *
//	 * @version v1.0.0
//	 * @author zhangcan
//	 * @date 2012-9-11 上午11:29:20
//	 *
//	 * @ModificationHistory
//	 * Date               Author     Version           Description	    <br>
//	 *------------------------------------------------------------*	    <br>
//	 * 2012-9-11        zhangcan      v1.0.0                       	    <br>
//	 */
//	public static String getMaskedStr2(String str) throws Exception,RemoteException{
//		if(StringUtils.isNotBlank(str))
//		{
//			StringBuilder sb = new StringBuilder();
//			for(int i=0; i < str.length(); i++)
//			{
//				if (i==1) {
//					sb.append(str.charAt(1));
//				}else {
//					sb.append("*");
//				}
//			}
//			return sb.toString();
//		}
//		return str;
//	}
//
//	public static int getBillingCycleIdByOrderCode(String orderCode)throws Exception
//	{
//		if (StringUtils.isNotBlank(orderCode))
//		{
//			String dateStr = StringUtils.substring(orderCode, 2, 8);
//			return Integer.parseInt(dateStr);
//		}
//		return -1;
//	}
//
//	/**
//	 * @Function: com.asiainfo.crm.ams.common.util.AmsUtil.getBillingCycleIdByDate
//	 * @Description: 根据时间返回账期
//	 *
//	 * @param:参数描述
//	 * @return：返回结果描述
//	 * @throws：异常描述
//	 *
//	 * @version: v1.0.0
//	 * @author: zhangxl6
//	 * @date: 2013-11-14 下午3:03:22
//	 *
//	 * Modification History:
//	 * Date         Author          Version            Description
//	 *---------------------------------------------------------*
//	 * 2013-11-14     zhangxl6           v1.0.0               修改原因
//	 */
//	public static int getBillingCycleIdByDate(Date date) throws Exception{
//		if(null != date){
//			String billingCycleId = "";
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
//			billingCycleId = sdf.format(date);
//			return Integer.parseInt(billingCycleId);
//		}
//		return -1;
//	}
//
//	/**
//	 * <p>@Description 根据银行编码判断缴费金额是否在最大金额和最小金额限制范围内</p>
//	 * <p>@author 刘黎 </p>
//	 * <p>@createDate Jul 29, 2011</p>
//	 * <p>@modifyDate   </p>
//	 *
//	 * @param bankCodeType	银行编码
//	 * @param payAmountCent	缴费金额（分）
//	 * @return void
//	 * @throws Exception
//	 * @throws java.rmi.RemoteException
//	 */
//	public static void chekBankChargeMoneyLimit(String bankCodeType, long payAmountCent) throws Exception,RemoteException {
//		long singlePayLowLimit = 1000;
//
//		IBOBsAirDefValue airDefValue = AmAirDefCacheGetter.getByParam(bankCodeType, AmsConst.AIRPAY.SINGLE_PAY_LOW_LIMIT);
//		if(airDefValue!=null && StringUtils.isNotBlank(airDefValue.getParaValue()) && StringUtils.isNumeric(airDefValue.getParaValue())){
//			singlePayLowLimit = Long.parseLong(airDefValue.getParaValue());
//		}
//		if(payAmountCent<singlePayLowLimit){
//			ExceptionUtil.throwBusinessException("AMS0700257", BillHelper.fmMoney(payAmountCent), BillHelper.fmMoney(singlePayLowLimit));
//		}
//		long singlePayLowMax = 1000000;
//		airDefValue = AmAirDefCacheGetter.getByParam(bankCodeType, AmsConst.AIRPAY.SINGLE_PAY_LOW_MAX);
//		if(airDefValue!=null && StringUtils.isNotBlank(airDefValue.getParaValue()) && StringUtils.isNumeric(airDefValue.getParaValue())){
//			singlePayLowMax = Long.parseLong(airDefValue.getParaValue());
//		}
//		if(payAmountCent>singlePayLowMax){
//			ExceptionUtil.throwBusinessException("AMS0700327", BillHelper.fmMoney(payAmountCent), BillHelper.fmMoney(singlePayLowMax));
//		}
//	}
//
//	/**
//	 * <p>@Description 获取某一天的前后任意一天</p>
//	 * <p>@author 刘黎 </p>
//	 * <p>@createDate Jul 29, 2011</p>
//	 * <p>@modifyDate   </p>
//	 *
//	 * @param today	参数时间 YYYYMMDD
//	 * @param days	需要追溯的天数 负数表示向前追溯（昨天）；正数表示向后取（明天）；0表示取传入的日期
//	 * @return
//	 * @throws Exception
//	 * @throws java.rmi.RemoteException
//	 */
//	public static String getOtherDayYYYYMMDD(String today,int days) throws Exception,RemoteException {
//		if(!AmsUtil.isDateString(today)) {
//			// 无法解析传入的日期格式！
//			ExceptionUtil.throwBusinessException("AMS0350378");
//		}
//		DateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
//		Date date = dateformat.parse(today);
//		Calendar c=Calendar.getInstance();
//		c.setTime(new Date(date.getTime()+1000*60*60*24*days));
//		return dateformat.format(c.getTime());
//	}
//
//	/**
//	 * <p>@Description 获取离这个月之前以及之后的任意一个月</p>
//	 * <p>@author 刘黎 </p>
//	 * <p>@createDate Aug 2, 2011</p>
//	 * <p>@modifyDate   </p>
//	 *
//	 * @param month	当月
//	 * @param count	需要追溯的月数：正数表示向后取（下个月），负数代表向前取（上个月）
//	 * @return
//	 * @throws Exception
//	 * @throws java.rmi.RemoteException
//	 */
//	public static long getOtherMonth(String month,int count) throws Exception, RemoteException {
//		DateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
//		Date date = dateformat.parse(month + "01");
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(date);
//		cal.add(Calendar.MONTH, count);
//		return Long.parseLong(dateformat.format(cal.getTime()).substring(0, 6));
//	}
//
//	/**
//	 * <p>@Description 根据当前系统时间，获取上个季度起始月份（YYYYMMMM格式）
//	 * YYYYMMMM，表示起始年月份及结束月份，例如2012年第一季度为20120103（代表2012年1月-2012年3月）</p>
//	 * <p>@author zhaimm  </p>
//	 * <p>@createDate 20120618</p>
//	 * <p>@modifyDate   </p>
//	 *
//	 * @return
//	 * @throws Exception
//	 * @throws java.rmi.RemoteException
//	 */
//	public static String getLastQuarter(Date sysdate) throws Exception, RemoteException {
//		//获取上个季度中的任何一个月份
//		Date lastQMon = TimeUtil.addOrMinusMonth(sysdate.getTime(), -3);
//		DateFormat dateformat = new SimpleDateFormat("yyyyMM");
//		String lastQMonStr = dateformat.format(lastQMon);
//		int month = Integer.parseInt(lastQMonStr.substring(4,6));
//		int quarter = (month-1)/3;
//		String year = lastQMonStr.substring(0,4);
//		String[] quarters = {"0103","0406","0709","1012"};
//		return year+quarters[quarter];
//	}
//
//	/**
//	 * 根据业务编码判断是否后台业务操作
//	 * @Function isBackStageBusiness
//	 * @Description
//	 *
//	 * @param businessId
//	 * @return
//	 * @throws Exception
//	 *
//	 * @version v1.0.0
//	 * @author guowx
//	 * @date 2013-5-20 下午2:35:27
//	 *
//	 * <strong>Modification History:</strong><br>
//	 * Date         Author          Version            Description<br>
//	 * ---------------------------------------------------------<br>
//	 * 2013-5-20     guowx           v1.0.0               修改原因<br>
//	 */
//	public  static boolean isBackStageBusiness(long businessId) throws Exception{
//		boolean result = false;
//		IBOBsParaDetailValue stage = (IBOBsParaDetailValue) CacheFactory.get(BsParaDetailCacheImpl.class,
//				"X_STAGE_AM_PAYMENT");
//		if(stage != null){
//			String businessIds = stage.getPara1();
//			String separator = stage.getPara2();
//			result = businessIds.contains(separator+businessId+separator);
//		}
//		return result;
//	}
//
//	/**
//	 * 地市是否割接完毕
//	 * @Function isCutOverRegion
//	 * @Description
//	 *
//	 * @param regionId
//	 * @return
//	 * @throws Exception
//	 *
//	 * @version v1.0.0
//	 * @author guowx
//	 * @date 2013-5-20 下午3:20:02
//	 *
//	 * <strong>Modification History:</strong><br>
//	 * Date         Author          Version            Description<br>
//	 * ---------------------------------------------------------<br>
//	 * 2013-5-20     guowx           v1.0.0               修改原因<br>
//	 */
////	public  static boolean isCutOverRegion(String regionId) throws Exception{
////		boolean result = false;
////		IBOBsParaDetailValue cut = (IBOBsParaDetailValue) CacheFactory.get(BsParaDetailCacheImpl.class,
////				"X_CUT_AM_PAYMENT");
////		if(cut != null){
////			String regionIds = cut.getPara1();
////			String separator = cut.getPara2();
////			result = regionIds.contains(separator+regionId+separator);
////		}
////		return result;
////	}
//	public  static boolean isCutOverRegion(String regionId) throws Exception{
////		boolean result = false;
////		IBsStaticDataSV staticSV = (IBsStaticDataSV) ServiceFactory.getService(IBsStaticDataSV.class);
////		IBOBsStaticDataValue[] cut = staticSV.getBsStaticDataValues("X_CUT_AM_PAYMENT", regionId);
////		if(cut != null && cut.length > 0){
////			result = true;
////		}
////		return result;
//		return isCutOverRegionForLog(regionId);
//	}
//	public  static boolean isCutOverRegionForLog(String regionId) throws Exception{//add by guowx 2013-7-8
//		boolean result = false;
//		IBOBsStaticDataValue cut = StaticDataCacheGetter.
//				getBsStaticDataByTypeAndValue("X_CUT_AM_PAYMENT", regionId);
//		if(cut != null){
//			result = true;
//		}
//		return result;
//	}
//	/**
//	 * 通过业务编码获取渠道Id
//	 * @Function getChannelIdByBusinessId
//	 * @Description
//	 *
//	 * @param businessId
//	 * @return
//	 * @throws Exception
//	 *
//	 * @version v1.0.0
//	 * @author guowx
//	 * @date 2013-5-20 下午5:50:46
//	 *
//	 * <strong>Modification History:</strong><br>
//	 * Date         Author          Version            Description<br>
//	 * ---------------------------------------------------------<br>
//	 * 2013-5-20     guowx           v1.0.0               修改原因<br>
//	 */
//	public static String getChannelIdByBusinessId(long businessId) throws Exception{
//		String result = AmsConst.SO.SO_CHANNEL_TYPE_1;
//		IBOBsOperationValue operationValue = BsOperationCacheGetter.getBsOperationValue(businessId);
//		if(operationValue != null){
//			result = ""+operationValue.getChannelType();
//		}
//		return result;
//	}
//	/**
//	 * 日志时间转字符串
//	 * @Function logDateToString
//	 * @Description
//	 *
//	 * @param date
//	 * @return
//	 * @throws Exception
//	 *
//	 * @version v1.0.0
//	 * @author guowx
//	 * @date 2013-6-6 下午5:42:24
//	 *
//	 * <strong>Modification History:</strong><br>
//	 * Date         Author          Version            Description<br>
//	 * ---------------------------------------------------------<br>
//	 * 2013-6-6     guowx           v1.0.0               修改原因<br>
//	 */
//	public static String logDateToString(Date date) throws Exception{
//		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");//guowx 20131014 不能用static对象
//		return format.format(date);
//	}
//	/**
//	 * 日志字符串转时间
//	 * @Function logStringToDate
//	 * @Description
//	 *
//	 * @param date
//	 * @return
//	 * @throws Exception
//	 *
//	 * @version v1.0.0
//	 * @author guowx
//	 * @date 2013-6-6 下午5:42:28
//	 *
//	 * <strong>Modification History:</strong><br>
//	 * Date         Author          Version            Description<br>
//	 * ---------------------------------------------------------<br>
//	 * 2013-6-6     guowx           v1.0.0               修改原因<br>
//	 */
//	public static Date logStringToDate(String date) throws Exception{
//		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");//guowx 20131014 不能用static对象
//		return format.parse(date);
//	}
//
//
//	/**add by zhaimm 充值流程优化
//	 * 根据AmPsPaymentValue组装日志Map
//	 * @Function getLogMapByPsPayment
//	 * @Description
//	 *
//	 * @param logMap
//	 * @param temPsPaymentValue
//	 * @param startTime
//	 * @param endTime
//	 * @param stage
//	 * @param state
//	 * @param errCode
//	 * @param errReason
//	 * @return
//	 * @throws Exception
//	 *
//	 * @version v1.0.0
//	 * @author zhaimm
//	 * @date 2013-6-7 上午11:11:28
//	 *
//	 * <strong>Modification History:</strong><br>
//	 * Date         Author          Version            Description<br>
//	 * ---------------------------------------------------------<br>
//	 * 2013-6-7     zhaimm           v1.0.0               修改原因<br>
//	 */
//	public static void getLogMapByPsPayment(Map logMap,IBOAmPsPaymentValue temPsPaymentValue,Date startTime,Date endTime,String stage,
//			String state,String errCode,String errReason) throws Exception{
//		String hostAddress = InetAddress.getLocalHost().getHostName();
//		if (StringUtils.isNotBlank(hostAddress)) {
//			logMap.put(AmsLogConstants.LOG.SRC_IP, hostAddress);
//		}
//		if (StringUtils.isNotBlank(temPsPaymentValue.getPaymentId())) {
//			logMap.put(AmsLogConstants.LOG.PAYMENT_ID, temPsPaymentValue.getPaymentId());
//		}
//		logMap.put(AmsLogConstants.LOG.ACCT_ID, String.valueOf(temPsPaymentValue.getAcctId()));
//		if (StringUtils.isNotBlank(temPsPaymentValue.getPeerSeq())) {
//			logMap.put(AmsLogConstants.LOG.PEER_SEQ, temPsPaymentValue.getPeerSeq());
//		}
//		if (StringUtils.isNotBlank(temPsPaymentValue.getBillId())) {
//			logMap.put(AmsLogConstants.LOG.BILL_ID, temPsPaymentValue.getBillId());
//		}
//
//		if (StringUtils.isNotBlank(state)) {
//			logMap.put(AmsLogConstants.LOG.SATE, state);
//		}
//
//		logMap.put(AmsLogConstants.LOG.START_DATE, AmsUtil.logDateToString(startTime));
//		logMap.put(AmsLogConstants.LOG.END_DATE, AmsUtil.logDateToString(endTime));
//		logMap.put(AmsLogConstants.LOG.USE_TIME, String.valueOf(endTime.getTime() - startTime.getTime()));
//		logMap.put(AmsLogConstants.LOG.AMOUNT, String.valueOf(temPsPaymentValue.getAmount()));
//		String channelId = AmsUtil.getChannelIdByBusinessId(temPsPaymentValue.getBusinessId());
//		if (StringUtils.isNotBlank(channelId)) {
//			logMap.put(AmsLogConstants.LOG.CHANNEL_ID, channelId);
//		}
//		if (StringUtils.isNotBlank(temPsPaymentValue.getChannelId())) {
//			logMap.put(AmsLogConstants.LOG.SUB_CHANNEL_ID, temPsPaymentValue.getChannelId());
//		}
//		logMap.put(AmsLogConstants.LOG.BUSINESS_ID, String.valueOf(temPsPaymentValue.getBusinessId()));
//		if (StringUtils.isNotBlank(stage)) {
//			logMap.put(AmsLogConstants.LOG.STAGE, stage);
//		}
//
//		logMap.put(AmsLogConstants.LOG.SUB_STAGE,"0");
//		logMap.put(AmsLogConstants.LOG.END_FLAG, "0");
//		logMap.put(AmsLogConstants.LOG.REGION_ID, CenterFactory.getCenterInfo().getRegion());
//		logMap.put(AmsLogConstants.LOG.OP_ID, String.valueOf(SessionManager.getUser().getID()));
//		logMap.put(AmsLogConstants.LOG.BILLING_CYCLE_ID, String.valueOf(temPsPaymentValue.getBillingCycleId()));
//
//		if (StringUtils.isNotBlank(errCode)) {
//			logMap.put(AmsLogConstants.LOG.ERR_CODE, errCode);
//		}
//		if (StringUtils.isNotBlank(errReason)) {
//			logMap.put(AmsLogConstants.LOG.ERR_REASON, errReason);
//		}
//		logMap.put(AmsLogConstants.LOG.REMARKS,temPsPaymentValue.getRemarks());
//	}
//
//	/**add by zhaimm 充值流程优化
//	 * 根据成员地区paymentId获取户主账户地区paymentId（在异地统一支付充值使用）
//	 * @Function getUniPaymentId
//	 * @Description
//	 *
//	 * @param paymentId
//	 * @param unionRegionId
//	 * @return
//	 * @throws Exception
//	 *
//	 * @version v1.0.0
//	 * @author zhaimm
//	 * @date 2013-7-1 上午11:11:28
//	 *
//	 * <strong>Modification History:</strong><br>
//	 * Date         Author          Version            Description<br>
//	 * ---------------------------------------------------------<br>
//	 * 2013-7-1     zhaimm           v1.0.0               修改原因<br>
//	 */
//	public static String getUniPaymentId(String paymentId,String unionRegionId){
//		String tmpPaymentId = paymentId.replace('A', 'B');
//		StringBuffer newPaymentId = new StringBuffer();
//		newPaymentId.append(unionRegionId.substring(1, 3)).append(tmpPaymentId.substring(2, tmpPaymentId.length()));
//		return newPaymentId.toString();
//	}
//
//	/**add by zhaimm 充值流程优化
//	 * 根据户主地区paymentId获取成员账户地区paymentId（在异地统一支付充值使用）
//	 * @Function getUniPaymentId
//	 * @Description
//	 *
//	 * @param paymentId
//	 * @param unionRegionId
//	 * @return
//	 * @throws Exception
//	 *
//	 * @version v1.0.0
//	 * @author zhaimm
//	 * @date 2013-7-1 上午11:11:28
//	 *
//	 * <strong>Modification History:</strong><br>
//	 * Date         Author          Version            Description<br>
//	 * ---------------------------------------------------------<br>
//	 * 2013-7-1     zhaimm           v1.0.0               修改原因<br>
//	 */
//	public static String getMemPaymentId(String paymentId,String memRegionId){
//		String tmpPaymentId = paymentId.replace('B', 'A');
//		StringBuffer newPaymentId = new StringBuffer();
//		newPaymentId.append(memRegionId.substring(1, 3)).append(tmpPaymentId.substring(2, tmpPaymentId.length()));
//		return newPaymentId.toString();
//	}
//
//	public  static boolean insertLog(String regionId) throws Exception{
//		if(!isCutOverRegionForLog(regionId)){
//			return false;
//		}
//		IBOBsParaDetailValue value = BsParaDetailCacheGetter.getParaDetailValue("X", "AmsLog", "Client");
//		if (value != null) {
//			String isEnable = value.getPara1();
//			if ("true".equalsIgnoreCase(isEnable) || "1".equalsIgnoreCase(isEnable) || "y".equalsIgnoreCase(isEnable)) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	//add by zhaimm 充值流程优化 获取地市配置的线程数  默认10个线程，如果地市配置有记录则取地市配置
//	public static int getThreadNum(String regionId) throws Exception{
//		int threadNum = AmsConst.COMMON.PROCESS_THREAD_NUM;
//		if(StringUtils.isNotBlank(regionId)){
//			IBOBsParaDetailValue val = (IBOBsParaDetailValue) CacheFactory.get(BsParaDetailCacheImpl.class, regionId + "_THREAD_NUM_AM_PAYMENT");
//			if(null!=val){
//				threadNum = Integer.parseInt(val.getPara1());
//			}else{
//				IBOBsParaDetailValue stage = (IBOBsParaDetailValue) CacheFactory.get(BsParaDetailCacheImpl.class, "X_THREAD_NUM_AM_PAYMENT");
//				if(null!=stage){
//					threadNum = Integer.parseInt(stage.getPara1());
//				}
//			}
//		}else{
//			IBOBsParaDetailValue val = (IBOBsParaDetailValue) CacheFactory.get(BsParaDetailCacheImpl.class, "X_THREAD_NUM_AM_PAYMENT");
//			if(null!=val){
//				threadNum = Integer.parseInt(val.getPara1());
//			}
//		}
//		return threadNum;
//	}
//
//	public static String[] getBillingCycleIdArray(Date beginDate, Date endDate) throws Exception {
//		if (beginDate != null && endDate != null) {
//			List list = new ArrayList();
//			Calendar bCal = Calendar.getInstance();
//			Calendar eCal = Calendar.getInstance();
//			bCal.setTime(beginDate);
//			eCal.setTime(endDate);
//			if (eCal.compareTo(bCal) < 0) {
//				// 错误：开始时间大于结束时间，不能计算帐期！
//				ExceptionUtil.throwBusinessException("SCS0040078");
//			}
//			while (eCal.compareTo(bCal) >= 0
//					|| (eCal.compareTo(bCal) < 0 && (eCal.get(Calendar.YEAR) == bCal.get(Calendar.YEAR) && (eCal.get(Calendar.MONTH) == bCal.get(Calendar.MONTH))))) {
//				list.add(new Integer(bCal.get(Calendar.YEAR) * 100 + bCal.get(Calendar.MONTH) + 1));
//				bCal.add(Calendar.MONTH, 1);
//			}
//			if (list != null && list.size() > 0) {
//				String[] ids = new String[list.size()];
//				for (int i = 0; i < list.size(); i++) {
//					ids[i] = (list.get(i) != null) ? list.get(i).toString() : null;
//				}
//				return ids;
//			}
//		}
//
//		return null;
//	}
//
//	/**
//	 * 获取空充账户与账本的代表地市，每个中心一个
//	 * @Function getAirPayRepresentRegionIds
//	 * @Description
//	 *
//	 * @return
//	 * @throws Exception
//	 *
//	 * @version v1.0.0
//	 * @author wangxw3
//	 */
//	public static String[] getAirPayRepresentRegionIds() throws Exception {
//		IBOBsParaDetailValue para = BsParaDetailCacheGetter.
//		getParaDetailValue(AmsConst.PARA_DETAIL.PARA_DETAIL_REGION_X,
//				AmsConst.PARA_DETAIL.PARA_TYPE_AM_PAYMENT, AmsConst.PARA_DETAIL.PARA_CODE_AIR_PAY_REGIONS);
//		if(null != para && StringUtils.isNotBlank(para.getPara1())){
//			String[] regions  = StringUtils.split(para.getPara1(), ",");
//			return regions;
//		}else{
//			throw new Exception("空充账户与账本的代表地市没有配置！");
//		}
//	}
//
//	/**
//	 * 根据中心获取空充的代表地市
//	 * @Function getAirPayRepresentRegionIds
//	 * @Description
//	 *
//	 * @return
//	 * @throws Exception
//	 *
//	 * @version v1.0.0
//	 * @author wangxw3
//	 */
//	public static String getAirPayRepresentRegionId() throws Exception {
//		CenterInfo centerInfo = CenterFactory.getCenterInfo();
//		String center = centerInfo.getCenter();
//		IBOBsParaDetailValue para = BsParaDetailCacheGetter.
//		getParaDetailValue(AmsConst.PARA_DETAIL.PARA_DETAIL_REGION_X,
//				AmsConst.PARA_DETAIL.PARA_TYPE_AM_PAYMENT, AmsConst.PARA_DETAIL.PARA_CODE_AIR_PAY_REGIONS);
//		String regionId = "";
//		if(null != para && StringUtils.isNotBlank(para.getPara1())){
//			if(AmsConst.COMMON.CENTER_CODE_1.equals(center)){
//				regionId = para.getPara2();
//			}else if(AmsConst.COMMON.CENTER_CODE_2.equals(center)){
//				regionId = para.getPara3();
//			}else if(AmsConst.COMMON.CENTER_CODE_3.equals(center)){
//				regionId = para.getPara4();
//			}else{
//				regionId = para.getPara5();
//			}
//		}else{
//			throw new Exception("空充账户与账本的代表地市没有配置！");
//		}
//		return regionId;
//	}
//	/**
//	 * 联网托收采用新规则，这个是新规则开始的账期，之前的用旧规则
//	 * @Function getLWEntrustNewRuleBillCycle
//	 * @Description
//	 *
//	 * @return
//	 * @throws Exception
//	 *
//	 * @version v1.0.0
//	 * @author guowx
//	 * @date 2013-12-10 上午11:19:47
//	 *
//	 * <strong>Modification History:</strong><br>
//	 * Date         Author          Version            Description<br>
//	 * ---------------------------------------------------------<br>
//	 * 2013-12-10     guowx           v1.0.0               修改原因<br>
//	 */
//	public static String getLWEntrustNewRuleBillCycle() throws Exception {
//		//新托收导出规则 guowx 20131119
//		IBOBsParaDetailValue val = (IBOBsParaDetailValue) CacheFactory.get(BsParaDetailCacheImpl.class, "X_BILLING_CYCLE_AM_ENTRUST");
//		if(val != null){
//			return val.getPara1();
//		}//end
//		return null;
//	}
//
//	/**
//	 * RSA公钥加密(密文是16进制表示的字符串)
//	 * @param data	原文
//	 * @param strHexPublicKey	16进制字符串形式表示的公钥
//	 * @return
//	 * @throws Exception
//	 */
//	public static String encryptRSA(String data, String strHexPublicKey) throws Exception {
//		if (data==null||data.length()<=0
//				|| strHexPublicKey==null||strHexPublicKey.length()<=0) {
//			return null;
//		}
//		try{
//			//获取公钥
//			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//			RSAPublicKey rsaPublicKey = (RSAPublicKey)keyFactory.generatePublic(new X509EncodedKeySpec(decodeHexString2Byte(strHexPublicKey)));
//			//公钥加密
//			Cipher cipher = Cipher.getInstance("RSA");
//			cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
//			return encodeByte2HexString(cipher.doFinal(data.getBytes("GBK")));
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	/**
//	 * 将16进制的字符串转换成字节数组
//	 * @param hexStr
//	 * @return
//	 */
//	public static byte[] decodeHexString2Byte(String hexStr){
//		if (hexStr ==null||hexStr.length()<=0) {
//			return null;
//		}
//		try {
//			return Hex.decodeHex(hexStr.toCharArray());
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	/**
//	 * 将字节数组转换成16进制的字符串
//	 * @param bytes
//	 * @return
//	 */
//	public static String encodeByte2HexString(byte[] bytes){
//		if (bytes ==null||bytes.length<=0) {
//			return null;
//		}
//		try {
//			return new String(Hex.encodeHex(bytes));
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//	/**
//	 * isNull 0 不允许为空  1 允许为空
//	 * map    数据对象
//	 * key    参数
//	 * @param map
//	 * @param key
//	 * @param isNull
//	 * @return
//	 * @throws Exception
//	 */
//	public static String getEmptyStringValue(Map map, String key,int isNull) throws Exception{
//		String value = "";
//		if(map != null && StringUtils.isNotBlank(key)){
//			Object obj = map.get(key);
//			if(obj != null){
//				value = map.get(key).toString();
//			}
//		}
//		if (0==isNull) {
//			if (StringUtils.isBlank(value)) {
//				throw new Exception("参数[" + key + "]不允许为空!");
//			}
//		}
//
//		return value;
//	}
//	/**
//	 * isNull 0 不允许为空  1 允许为空
//	 * map    数据对象
//	 * key    参数
//	 * @param map
//	 * @param key
//	 * @param isNull
//	 * @return
//	 * @throws Exception
//	 */
//	public static long getEmptyLongValue(Map map, String key,int isNull) throws Exception{
//		long value = -1;
//		if(map != null && StringUtils.isNotBlank(key)){
//			Object obj = map.get(key);
//			if(obj != null){
//				value = Long.valueOf(map.get(key).toString());
//			}
//		}
//		if (0==isNull) {
//			if (value<0) {
//				throw new Exception("参数[" + key + "]不允许为空!");
//			}
//		}
//
//		return value;
//	}
//	/**
//	 * 发票资金池启用时间
//	 * @Function getInvoicePoolCutOverDate
//	 * @Description
//	 *
//	 * @return 返回时间为“yyyy-MM-dd HH:mm:ss”
//	 * @throws Exception
//	 *
//	 * @version v1.0.0
//	 * @author guowx
//	 * @date 2014-2-28 上午10:31:33
//	 *
//	 * <strong>Modification History:</strong><br>
//	 * Date         Author          Version            Description<br>
//	 * ---------------------------------------------------------<br>
//	 * 2014-2-28     guowx           v1.0.0               修改原因<br>
//	 */
//	public static  String getInvoicePoolRunDate() throws Exception{
//		IBOBsParaDetailValue date = (IBOBsParaDetailValue) CacheFactory.get(BsParaDetailCacheImpl.class, "X_DATE_AM_INVOICE_POOL");
//		String result = null;
//		if(null!=date){
//			result = date.getPara1();
//		}
//		if(result == null){
//			result = "2014-02-01 00:00:00";
//		}
//		return result;
//	}
//	/**
//	 * 发票资金池启用月份
//	 * @Function getInvoicePoolRunBillingCycle
//	 * @Description
//	 *
//	 * @return 返回时间为“yyyy-MM”
//	 * @throws Exception
//	 *
//	 * @version v1.0.0
//	 * @author guowx
//	 * @date 2014-2-28 上午10:32:11
//	 *
//	 * <strong>Modification History:</strong><br>
//	 * Date         Author          Version            Description<br>
//	 * ---------------------------------------------------------<br>
//	 * 2014-2-28     guowx           v1.0.0               修改原因<br>
//	 */
//	public static  String getInvoicePoolRunBillingCycle() throws Exception{
//		IBOBsParaDetailValue billingCycle = (IBOBsParaDetailValue) CacheFactory.get(BsParaDetailCacheImpl.class, "X_BILLINGCYCLE_AM_INVOICE_POOL");
//		String result = null;
//		if(null!=billingCycle){
//			result = billingCycle.getPara1();
//		}
//		if(result == null){
//			result = "2014-02";
//		}
//		return result;
//	}
//	/**
//	 *
//	 *
//	 * @Function: com.asiainfo.crm.ams.common.util.AmsUtil.getEffMonths
//	 * @Description:集团划帐预约月份配置
//	 *
//	 * @return
//	 * @throws java.rmi.RemoteException
//	 * @throws Exception
//	 *
//	 * @version:TODO
//	 * @author:shenxc
//	 * @date:2014-5-29下午3:23:22
//	 *
//	 * Modification History:
//	 * Date         Author      Version     Description
//	 * -----------------------------------------------------------------
//	 * 2014-5-29    shenxc      TODO     TODO
//	 */
//	public IBOBsStaticDataValue[] getEffMonths() throws RemoteException, Exception {
//		IBOBsStaticDataValue[] maxMonth = StaticDataUtil.getStaticData(AmsConst.STATIC_DATA.FUND_ADJUST_EFF_MONTH);
//		return maxMonth;
//	}
//
//	/**
//	 *
//	 * getChargeBankCode
//	 * @Function: com.asiainfo.crm.ams.common.util.AmsUtil.getChargeBankCode
//	 * @Description:根据银行编码获得充值银行编码
//	 *
//	 * @return
//	 * @throws java.rmi.RemoteException
//	 * @throws Exception
//	 *
//	 * @version:TODO
//	 * @author:kangzk
//	 * @date:2014-5-29下午3:23:22
//	 *
//	 * Modification History:
//	 * Date         Author      Version     Description
//	 * -----------------------------------------------------------------
//	 * 2014-10-16    kangzk      TODO     TODO
//	 */
//	public static String getChargeBankCode(String codeValue)throws Exception{
//		IBOBsStaticDataValue value = StaticDataUtil.getStaticData(AmsConst.STATIC_DATA.CODE_TYPE_BANK_CODE, codeValue);
//		if (value!=null) {
//			codeValue= StringUtils.isNotBlank(value.getCodeName()) ? value.getCodeName() : codeValue;
//		}
//		return codeValue;
//	}
//
//	/**
//	 *
//	 * getChargeBankCode
//	 * @Function: com.asiainfo.crm.ams.common.util.AmsUtil.getFtpPathCodeByCodeType
//	 * @Description:根据平台编码和业务编码获得FTP_PATH_CODE值
//	 *
//	 * @return
//	 * @throws java.rmi.RemoteException
//	 * @throws Exception
//	 *
//	 * @version:TODO
//	 * @author:kangzk
//	 * @date:2014-5-29下午3:23:22
//	 *
//	 * Modification History:
//	 * Date         Author      Version     Description
//	 * -----------------------------------------------------------------
//	 * 2014-10-16    kangzk      TODO     TODO
//	 */
//	public static String getFtpPathCodeByCodeType(String codeValue)throws Exception{
//		IBOBsStaticDataValue value = StaticDataUtil.getStaticData(AmsConst.STATIC_DATA.PLATFORM_CODE_PATH_FTP_CODE, codeValue);
//		if (value!=null) {
//			codeValue= StringUtils.isNotBlank(value.getCodeName()) ? value.getCodeName() : codeValue;
//		}else {
//			throw new Exception("请联系维护人员配置ftp_path_code值(PLATFORM_CODE_PATH_FTP_CODE)!");
//		}
//		return codeValue;
//	}
//	/**
//	 * 根据参数获得对象名称
//	 * @param codeType
//	 * @param codeValue
//	 * @return
//	 * @throws Exception
//	 */
//	public static String getCodeName(String codeType,String codeValue)throws Exception{
//		IBOBsStaticDataValue value = StaticDataUtil.getStaticData(codeType, codeValue);
//		if (value!=null) {
//			codeValue= StringUtils.isNotBlank(value.getCodeName()) ? value.getCodeName() : codeValue;
//		}
//		return codeValue;
//	}
//
//	/**
//	 * 根据参数获得对象名称
//	 * @param codeType
//	 * @param codeValue
//	 * @return
//	 * @throws Exception
//	 */
//	public static String getCodeTypeAlias(String codeType,String codeValue)throws Exception{
//		IBOBsStaticDataValue value = StaticDataUtil.getStaticData(codeType, codeValue);
//		if (value!=null) {
//			codeValue= StringUtils.isNotBlank(value.getCodeTypeAlias()) ? value.getCodeTypeAlias() : codeValue;
//		}
//		return codeValue;
//	}
//
//	/**
//	 * @Function: isChannelAgentCutOver
//	 * @Description: 判断代理商二级网点是否已割接(base.bs_static_data.code_type=AirPay_ChannelAgent_Cut,code_value=BankID)
//	 *
//	 * @return true 已割接 ，false 没有割接
//	 * @throws Exception
//	 *
//	 * @version: v1.0.0
//	 * @author: lixh6
//	 * @date: May 19, 2011 3:10:23 PM
//	 *
//	 * Modification History:
//	 * Date         Author          Version            Description
//	 *---------------------------------------------------------*
//	 */
//	public static boolean isChannelAgentCutOver(String bankCode) throws Exception {
//		IBOBsStaticDataValue value = StaticDataUtil.getStaticData("AirPay_ChannelAgent_Cut", bankCode);
////		IBOBsStaticDataValue value = StaticDataUtil.getStaticData("AirPay_ChannelAgent_Cut","BankCodes");
//		if (value!=null
////				&& StringUtils.isNotBlank(value.getCodeValue()) && value.getCodeValue().contains(bankCode)
//				) {
//			return true;
//		}
//		return false;
//	}


}
