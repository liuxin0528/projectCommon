/*
 * @(#)ValidationUtil.java 
 * 
 * Copyright 2016 by 青岛众恒信息科技股份有限公司 . 
 * All rights reserved.
 *
 */
package com.zehin.common.util;

import java.util.regex.Pattern;

/**
 *	日期		:	2016年1月11日<br>
 *	作者		:	liuxin<br>
 *	项目		:	zehinCommon<br>
 *	功能		:	验证工具类<br>
 */
public class ValidationUtil {
	
	//验证整数的正则式
	private static final String P_INT = "^\\d+$";
	
	//验证浮点数的正则式
	private static final String P_FLOAT = "^\\d+(\\.\\d+){0,1}$";
	
	//验证电话号码的正则式
	private static final String P_PHONE = "^\\d+(-\\d+)*$";
	
	//验证 e-mail 的正则式
	private static final String P_EMAIL = "^[a-zA-Z_]\\w*@\\w+(\\.\\w+)+$";
	
	
	
	/**
	 * 
	 * Description : 是否为空
	 * @param value
	 * @return
	 */
	public static Boolean isNULL(Object... value) {
		if (null == value || value.length < 1) {
			return true;
		} else {
			for (int i = 0; i < value.length; i++) {
				if (null == value[i] || "".equals(value[i].toString())
						|| "null".equals(value[i].toString().toLowerCase())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * Description : 对字符串进行验证
	 * @param input 需要验证的字符串
	 * @param matcher 验证规则
	 * @return
	 */
	public static boolean validate(String input, String matcher) {
		if (isNULL(input)) {
			return false;
		}
		return Pattern.matches(matcher, input);
	}
	
	/**
	 * 
	 * Description : 检查字符串是否为指定的日期类型
	 * @param value
	 * @param fromatReg
	 * @return
	 */
	public static boolean isDateFormat(Object value, String fromatReg) {
		Pattern pattern = Pattern.compile(fromatReg);
		if (isNULL(value) || !pattern.matcher(value.toString()).matches()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 
	 * Description : 检查字符串是否为日期
	 * @param value
	 * @return
	 */
	public static boolean isDate(Object value) {
		return isDateFormat(value, "^\\d{4}-\\d{1,2}-\\d{1,2}$");
	}

}
