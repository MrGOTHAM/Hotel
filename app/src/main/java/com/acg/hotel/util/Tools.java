package com.acg.hotel.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 开发常用工具类
 * @author 方坚
 *
 */
public class Tools {
	/**
	 * 
	 * @param value 字符串
	 * @return 如果字符串不为空或者长度不为零返回true
	 */
	public static boolean isNotNull( String value ) {
		if( value == null || "".equals( value.trim()) || "null".equalsIgnoreCase(value) ) {
			return false;
		}
		return true;
	}
	
	/**
	 * ISO编码转换成UTF8编码
	 * @param s 字符串
	 * @return UTF编码字符串
	 */
	public static String ISOtoUTF8(String s) { 
		try { 
			s = new String(s.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception ignored) {
			
		} 
		return s; 
	}
	
	/**
	 * 是否为num
	 * @param str 字符串
	 * @return boolean
	 */
	public static boolean isNum(String str){	
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");	
	}


	/*  <br>　　　　　2019年1月16日已知
   中国电信号段
       133,149,153,173,174,177,180,181,189,199
   中国联通号段
       130,131,132,145,146,155,156,166,175,176,185,186
   中国移动号段
       134(0-8),135,136,137,138,139,147,148,150,151,152,157,158,159,165,178,182,183,184,187,188,198
   上网卡专属号段（用于上网和收发短信，不能打电话）
       如中国联通的是145
   虚拟运营商
       电信：1700,1701,1702
       移动：1703,1705,1706
       联通：1704,1707,1708,1709,171
   卫星通信： 1349 <br>　　　　　未知号段：141、142、143、144、154
   */

	/**
	 * 判断是不是手机号
	 * @param str 字符串
	 * @return boolean
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		String s2="^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";// 验证手机号
		if(isNotNull(str)){
			p = Pattern.compile(s2);
			m = p.matcher(str);
			b = m.matches();
		}
		return b;
	}
}
