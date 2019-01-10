package com.asiainfo.batchwriteoff.util;

import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.common.cache.DistrictCacheImpl;
import com.ai.common.ivalues.IBOBsDistrictValue;

public final class DistrictUtil {
	public static IBOBsDistrictValue[] getRegion()
		    throws Exception
		  {
		    return (IBOBsDistrictValue[])CacheFactory.get(DistrictCacheImpl.class, "PIR^");
		  }

		  public static IBOBsDistrictValue[] getCountyByRegionDistrictId(long districtId)
		    throws Exception
		  {
		    return (IBOBsDistrictValue[])CacheFactory.get(DistrictCacheImpl.class, "RIC^" + districtId);
		  }

		  public static IBOBsDistrictValue[] getAllDistrict()
		    throws Exception
		  {
		    return (IBOBsDistrictValue[])CacheFactory.get(DistrictCacheImpl.class, "ADL^");
		  }

		  public static IBOBsDistrictValue getDistrictByDistrictId(long districtId)
		    throws Exception
		  {
		    return (IBOBsDistrictValue)CacheFactory.get(DistrictCacheImpl.class, "ADD^" + districtId);
		  }

		  public static IBOBsDistrictValue getDistrictByRegionId(String regionId)
		    throws Exception
		  {
		    return (IBOBsDistrictValue)CacheFactory.get(DistrictCacheImpl.class, regionId);
		  }

		  public static String getCenterByRegionId(String regionid)
		    throws Exception
		  {
		    return getDistrictByRegionId(regionid).getCenterInfoCode();
		  }
}
