/**
 * 
 */
package com.confress.lovewall.Utils;

import com.amap.api.location.AMapLocation;
import com.confress.lovewall.model.LocationInfo;

/**
 * 辅助工具类
 * @创建时间： 2015年11月24日 上午11:46:50
 * @项目名称： AMapLocationDemo2.x
 * @author hongming.wang
 * @文件名称: Utils.java
 * @类型名称: Utils
 */
public class Utils {
	/**
	 *  开始定位
	 */
	public final static int MSG_LOCATION_START = 0;
	/**
	 * 定位完成
	 */
	public final static int MSG_LOCATION_FINISH = 1;
	/**
	 * 停止定位
	 */
	public final static int MSG_LOCATION_STOP= 2;
	
	public final static String KEY_URL = "URL";
	public final static String URL_H5LOCATION = "file:///android_asset/location.html";
    private static LocationInfo locationInfo;


	public  static synchronized LocationInfo getLocationInfo(AMapLocation location){
		if(null == location){
			return null;
		}
		if (locationInfo==null){
			locationInfo=new LocationInfo();
		}
		//定位成功
		if (location.getErrorCode() == 0){
			locationInfo.setLatitude(location.getLatitude());
			locationInfo.setLongtitude(location.getLongitude());
			locationInfo.setAddress(location.getAddress());
		}else {
		//定位失败
			locationInfo.setLatitude(0);
			locationInfo.setLongtitude(0);
			locationInfo.setAddress("");
		}
		return  locationInfo;
	}
	/**
	 * 根据定位结果返回定位信息的字符串
	 * //@param loc
	 * @return
	 */
	public synchronized static String getLocationStr(AMapLocation location){
		if(null == location){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		//errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
		if(location.getErrorCode() == 0){
			sb.append("定位成功" + "\n");
			sb.append("定位类型: " + location.getLocationType() + "\n");
			sb.append("经    度    : " + location.getLongitude() + "\n");
			sb.append("纬    度    : " + location.getLatitude() + "\n");
			sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
			sb.append("提供者    : " + location.getProvider() + "\n");
			
			if (location.getProvider().equalsIgnoreCase(
					android.location.LocationManager.GPS_PROVIDER)) {
				// 以下信息只有提供者是GPS时才会有
				sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
				sb.append("角    度    : " + location.getBearing() + "\n");
				// 获取当前提供定位服务的卫星个数
				sb.append("星    数    : "
						+ location.getSatellites() + "\n");
			} else {
				// 提供者是GPS时是没有以下信息的
				sb.append("国    家    : " + location.getCountry() + "\n");
				sb.append("省            : " + location.getProvince() + "\n");
				sb.append("市            : " + location.getCity() + "\n");
				sb.append("城市编码 : " + location.getCityCode() + "\n");
				sb.append("区            : " + location.getDistrict() + "\n");
				sb.append("区域 码   : " + location.getAdCode() + "\n");
				sb.append("地    址    : " + location.getAddress() + "\n");
				sb.append("兴趣点    : " + location.getPoiName() + "\n");
			}
		} else {
			//定位失败
			sb.append("定位失败" + "\n");
			sb.append("错误码:" + location.getErrorCode() + "\n");
			sb.append("错误信息:" + location.getErrorInfo() + "\n");
			sb.append("错误描述:" + location.getLocationDetail() + "\n");
		}
		return sb.toString();
	}
}
