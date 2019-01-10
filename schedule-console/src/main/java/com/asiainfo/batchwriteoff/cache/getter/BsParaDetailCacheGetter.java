package com.asiainfo.batchwriteoff.cache.getter;

import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.common.cache.BsParaDetailCacheImpl;
import com.ai.common.ivalues.IBOBsParaDetailValue;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> @Description </p>
 * <p> @company Asiainfo-linkage (NJ) </p>
 * <p> @author 闫瑞国 </p>
 * <p> @Email yanrg@asiainfo-linkage.com </p>
 * <p> @createDate Dec 28, 2010 5:40:29 PM </p>
 * <p> @modifyDate </p>
 * <p> @version 1.0 </p>
 */
public class BsParaDetailCacheGetter {

	public static IBOBsParaDetailValue getParaDetailValue(String regionId,String paraType,String paraCode)throws Exception{
		IBOBsParaDetailValue retValue = null;
		if(StringUtils.isNotBlank(regionId) && StringUtils.isNotBlank(paraType) && StringUtils.isNotBlank(paraCode)){
			String cacheKey = regionId+"_" +paraCode + "_" + paraType;
			retValue = (IBOBsParaDetailValue) CacheFactory.get(BsParaDetailCacheImpl.class, cacheKey);
		}
		return retValue;
	}
	
	public static IBOBsParaDetailValue[] getParaDetailValues(String regionId, String paraType, String paraCode) throws Exception {
		List tmpList = new ArrayList();
		IBOBsParaDetailValue[] retValues = null;
		if(StringUtils.isNotBlank(regionId) && StringUtils.isNotBlank(paraType) && StringUtils.isNotBlank(paraCode)){
			String cacheKey = "Sp_" + regionId+"_" +paraCode + "_" + paraType;
			tmpList = (ArrayList) CacheFactory.get(BsParaDetailCacheImpl.class, cacheKey);
		}
		
		retValues = (IBOBsParaDetailValue[]) tmpList.toArray(new IBOBsParaDetailValue[] {});
		return retValues;
	}
	
	public static IBOBsParaDetailValue[] getParaDetailValuesNew(String regionId, String paraType, String paraCode) throws Exception {
		List tmpList = new ArrayList();
		IBOBsParaDetailValue[] retValues = null;
		if(StringUtils.isNotBlank(regionId) && StringUtils.isNotBlank(paraType) && StringUtils.isNotBlank(paraCode)){
			String cacheKey = "Sp_" + regionId+"_" +paraCode + "_" + paraType;
			tmpList = (ArrayList) CacheFactory.get(BsParaDetailCacheImpl.class, cacheKey);
		}
		
		if(tmpList !=null)
		{
			retValues = (IBOBsParaDetailValue[]) tmpList.toArray(new IBOBsParaDetailValue[] {});
		}
		
		return retValues;
	}

}
