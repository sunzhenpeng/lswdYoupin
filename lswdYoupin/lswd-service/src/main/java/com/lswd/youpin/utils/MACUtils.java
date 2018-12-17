package com.lswd.youpin.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class MACUtils {
	
	private final static Logger _logger = LoggerFactory.getLogger(MACUtils.class);
	
	
	  //获取MAC地址
	  public static String getMacAddress() {
	    try {
	      Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
	      byte[] mac = null;
	      while (allNetInterfaces.hasMoreElements()) {
	        NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
	        if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
	          continue;
	        } else {
	          mac = netInterface.getHardwareAddress();
	          if (mac != null) {
	            StringBuilder sb = new StringBuilder();
	            for (int i = 0; i < mac.length; i++) {
	              sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
	            }
	            if (sb.length() > 0) {
	              return sb.toString();
	            }
	          }
	        }
	      }
	    } catch (Exception e) {
	    	_logger.error("获取MAC地址失败");
	    }
	    return "";
	  }

	  // 获取本机ip地址
	  public static String getIpAddress() {
	    try {
	      Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
	      InetAddress ip = null;
	      while (allNetInterfaces.hasMoreElements()) {
	        NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
	        if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
	          continue;
	        } else {
	          Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
	          while (addresses.hasMoreElements()) {
	            ip = addresses.nextElement();
	            if (ip != null && ip instanceof Inet4Address) {
	              return ip.getHostAddress();
	            }
	          }
	        }
	      }
	    } catch (Exception e) {
	    	_logger.error("获取IP地址失败");
	    }
	    return "";
	  }


	public static void main(String[] args) {
		System.out.println(getMacAddress());
	}

}
