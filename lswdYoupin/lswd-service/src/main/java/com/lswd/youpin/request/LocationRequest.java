package com.lswd.youpin.request;

/**
 * @category 封装经纬度请求的实体类
 * @author liruilong
 */

public class LocationRequest implements Request {

	// 纬度
	private String latitude = "";
	// 经度
	private String longitude = "";

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}
