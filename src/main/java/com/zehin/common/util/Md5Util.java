/*
 * @(#)Md5Util.java 
 * 
 * Copyright 2016 by 青岛众恒信息科技股份有限公司 . 
 * All rights reserved.
 *
 */
package com.zehin.common.util;

import java.security.MessageDigest;

/**
 *	日期		:	2016年4月1日<br>
 *	作者		:	liuxin<br>
 *	项目		:	zehinCommon<br>
 *	功能		:	MD5加密工具类<br>
 */
public class Md5Util {
	
	private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7","8", "9", "a", "b", "c", "d", "e", "f"};
	
	/**
	 * 
	 * Description : MD5加密
	 * @param origin 要加密的字符串
	 * @return
	 */
	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToString(md.digest(resultString.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}
	
	
	/**
	 * 
	 * Description : 转换字节数组为16进制字串
	 * @param b
	 * @return
	 */
	private static String byteArrayToString(byte[] b){
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));//若使用本函数转换则可得到加密结果的16进制表示，即数字字母混合的形式
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b){
		int n = b;
		if(n < 0){
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	
}
