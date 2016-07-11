/*
 * @(#)StringUtil.java 
 * 
 * Copyright 2016 by 青岛众恒信息科技股份有限公司 . 
 * All rights reserved.
 *
 */
package com.zehin.common.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 *	日期		:	2016年1月11日<br>
 *	作者		:	liuxin<br>
 *	项目		:	zehinCommon<br>
 *	功能		:	字符串工具类<br>
 */
public class StringUtil {
	
	public static final Pattern PDigit = Pattern.compile("^\\d*$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	
	//空字符串
    public static final String EMPTY_STRING = "";
	
	/**
	 * 
	 * Description : 获得一个 32 位UUID
	 * @return String
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
	}
	
	/**
	 * 
	 * Description : 格式化对象，为空时输出""
	 * @param obj
	 * @return String
	 */
	public static String nvl(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString();
		}
	}
	
	/**
	 * 
	 * Description : 把map数组的第一个值返回重新组成数组
	 * @param params
	 * @return
	 */
	public static Map<String, String> getParamMap(Map<String, String[]> params) {

		Map<String, String> map = new HashMap<String, String>();

		Iterator<String> it = params.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String[] values = params.get(key);
			if (values.length > 0) {
				map.put(key, values[0]);
			}
		}
		return map;
	}
	
	/**
	 * 
	 * Description : Object to Long
	 * @param obj
	 * @return long
	 */
	public static long toLong(Object obj) {
		if (obj == null) {
			return 0;
		} else {
			try {
				return Long.valueOf(obj.toString()).longValue();
			} catch (NumberFormatException e) {
				return 0;
			}
		}
	}
	
	/**
	 * 
	 * Description : Object to BigInteger
	 * @param obj
	 * @return BigInteger
	 */
	public static BigInteger toBigInteger(Object obj) {
		if (obj == null) {
			return BigInteger.valueOf(0);
		} else {
			try {
				return BigInteger.valueOf(toLong(obj));
			} catch (NumberFormatException e) {
				return BigInteger.valueOf(0);
			}
		}
	}
	
	/**
	 * 
	 * Description : Object to Double
	 * @param obj
	 * @return Double
	 */
	public static Double toDouble(Object obj) {
		if (obj == null || "null".equals(nvl(obj)) || "".equals(obj)) {
			return 0.00;
		} else {
			try {
				return Double.valueOf(obj.toString()).doubleValue();
			} catch (NumberFormatException e) {
				return 0.00;
			}
		}
	}

	/**
	 * 
	 * Description : Object to Int
	 * @param obj
	 * @return int
	 */
	public static int toInt(Object obj) {
		if (obj == null) {
			return 0;
		} else {
			try {
				return Integer.valueOf(obj.toString()).intValue();
			} catch (NumberFormatException e) {
				return 0;
			}
		}
	}

	/**
	 * 
	 * Description : Object to Short
	 * @param obj
	 * @return short
	 */
	public static short toShort(Object obj) {
		if (obj == null) {
			return 0;
		} else {
			try {
				return Short.valueOf(obj.toString()).shortValue();
			} catch (NumberFormatException e) {
				return 0;
			}
		}
	}

	/**
	 * 
	 * Description : 格式化 double 
	 * @param d
	 * @param format
	 * @return
	 */
	public static String parseDouble(double d,String format) {
		DecimalFormat df = new DecimalFormat(format);
		return df.format(d);
	}
	
	/**
	 * Description 格式化 Object
	 * @param format "" ##.## ,###.## ,000.00 0.00 ,### ,000
	 * @param value 
	 * @return
	 */
	public static String toNumber(String format, Object value) {
		if (isNULL(value)) {
			return "";
		}
		DecimalFormat df = new DecimalFormat(format);
		StringBuffer sb = new StringBuffer();
		df.format(new BigDecimal(value.toString()), sb, new FieldPosition(0));
		return sb.toString();
	}
	
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
	 * Description :变更id的长度 changIdLength("1",6) => 000001
	 * @param (String)strId 字符串id
	 * @param (int)intLen 长度
	 * @return String
	 */
	public static String changIdLength(String strId, int intLen) {
		if (strId.length() < intLen) {
			int len = intLen - strId.length();
			String zeroStr = "";
			for (int i = 0; i < len; i++) {
				zeroStr += "0";
			}
			strId = zeroStr + strId;
		}
		return strId;
	}
	
	/**
	 * 
	 * Description : 将字符串的指定位置转化为大写字母
	 * @param str 
	 * @param index 
	 * @return
	 */
	public static String upperBetter(String str, int index) {
		char letters[] = new char[str.length()];
		for (int i = 0; i < str.length(); i++) {
			char letter = str.charAt(i);
			if (i == index) {
				letter = (char) (letter - 32);
			}
			letters[i] = letter;
		}
		return new String(letters);
	}
	
	/**
	 * 
	 * Description : 将字符串的第一个字母转化为大写字母
	 * @param str
	 * @return
	 */
	public static String uppperFirstBetter(String str) {
		return upperBetter(str, 0);
	}

	/**
	 * 
	 * Description : 将字符串的指定位置转化为大写字母
	 * @param str
	 * @param indexs
	 * @return
	 */
	public static String upperBetterLoop(String str, List<Integer> indexs) {
		char letters[] = new char[str.length()];
		for (int i = 0; i < str.length(); i++) {
			char letter = str.charAt(i);
			for (int index : indexs) {
				if (index == i) {
					letter = (char) (letter - 32);
				}
			}
			letters[i] = letter;
		}
		return new String(letters);
	}
	
	/**
	 * 
	 * Description : 字符串是否为空，null或空字符串时返回true,其他情况返回false
	 * @param str 
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 
	 * Description : 字符串是否不为空，null或空字符串时返回false,其他情况返回true
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !StringUtil.isEmpty(str);
	}

	/**
	 * 
	 * Description :将一个数组拼成一个字符串，数组项之间以逗号分隔
	 * @param arr
	 * @return
	 */
	public static String join(Object[] arr) {
		return join(arr, ",");
	}

	/**
	 * 
	 * Description : 将一个二维数组拼成一个字符串，第二维以逗号分隔，第一维以换行分隔
	 * @param arr
	 * @return
	 */
	public static String join(Object[][] arr) {
		return join(arr, "\n", ",");
	}

	/**
	 * 
	 * Description : 将一个数组以指定的分隔符拼成一个字符串
	 * @param arr
	 * @param spliter �ָ���
	 * @return
	 */
	public static String join(Object[] arr, String spliter) {
		if (arr == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i != 0) {
				sb.append(spliter);
			}
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	/**
	 * 
	 * Description : 将一个二维数组拼成一个字符串，第二维以指定的spliter2参数分隔，第一维以换行spliter1分隔
	 * @param arr
	 * @param spliter1
	 * @param spliter2
	 * @return
	 */
	public static String join(Object[][] arr, String spliter1, String spliter2) {
		if (arr == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i != 0) {
				sb.append(spliter2);
			}
			sb.append(join(arr[i], spliter2));
		}
		return sb.toString();
	}

	/**
	 * 
	 * Description : 将一个List拼成一个字符串，数据项之间以逗号分隔
	 * @param list
	 * @return
	 */
	public static <T> String join(List<T> list) {
		return join(list, ",");
	}

	/**
	 * 
	 * Description : 将一个List拼成一个字符串，数据项之间以指定的参数spliter分隔
	 * @param list
	 * @param spliter �ָ���
	 * @return
	 */
	public static <T> String join(List<T> list, String spliter) {
		if (list == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if (i != 0) {
				sb.append(spliter);
			}
			sb.append(list.get(i));
		}
		return sb.toString();
	}

	/**
	 * 
	 * Description : 将字符串数组中 每个形如member_id的字段转化成 memberId
	 * @param strs
	 * @return
	 */
	public static String[] convertColumnToProperty(String[] strs) {
		String[] arr = new String[strs.length];
		for (int i = 0; i < strs.length; i++) {
			if (nvl(strs[i]).indexOf("_") < 0) {
				arr[i] = strs[i];
			} else {
				if (strs[i].indexOf("_") >= 0) {
					String[] tempArr = strs[i].split("_");
					StringBuffer sbBuffer = new StringBuffer();
					sbBuffer.append(tempArr[0]);
					for (int j = 1; j < tempArr.length; j++) {
						sbBuffer.append(uppperFirstBetter(tempArr[j]));
					}
					arr[i] = sbBuffer.toString();
				}
			}
		}
		return arr;
	}

	/**
	 * 
	 * Description : java版本的escape
	 * @param src
	 * @return
	 */
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	/**
	 * 
	 * Description : java版本的unescape函数 
	 * @param src
	 * @return
	 */
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
	
	/**
	 * 
	 * Description : str to Double 
	 * @param str
	 * @return
	 */
	public static Double strToDouble(String str){
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * Description : str to Integer 
	 * @param str
	 * @return
	 */
	public static Integer strToInteger(String str){
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * Description : 判断字符串是否全部由数字组成
	 * @param str
	 * @return
	 */
	public static boolean isDigit(String str) {
		if (StringUtil.isEmpty(str)) {
			return false;
		}
		return PDigit.matcher(str).find();
	}
	
	/**
	 * 
	 * Description :  计算一个字符串中某一子串出现的次数
	 * @param str 
	 * @param findStr 
	 * @return
	 */
	public static int count(String str, String findStr) {
		int lastIndex = 0;
		int length = findStr.length();
		int count = 0;
		int start = 0;
		while ((start = str.indexOf(findStr, lastIndex)) >= 0) {
			lastIndex = start + length;
			count++;
		}
		return count;
	}
	
	/**
     *  比较两个字符串（大小写敏感）。
     * StringUtil.equals(null, null)   = true
     * StringUtil.equals(null, "abc")  = false
     * StringUtil.equals("abc", null)  = false
     * StringUtil.equals("abc", "abc") = true
     * StringUtil.equals("abc", "ABC") = false
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     *
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean equals(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }
        return str1.equals(str2);
    }
    
    /**
     * 比较两个字符串（大小写不敏感）。
     * <pre>
     * StringUtil.equalsIgnoreCase(null, null)   = true
     * StringUtil.equalsIgnoreCase(null, "abc")  = false
     * StringUtil.equalsIgnoreCase("abc", null)  = false
     * StringUtil.equalsIgnoreCase("abc", "abc") = true
     * StringUtil.equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     *
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }
        return str1.equalsIgnoreCase(str2);
    }
    
    /**
     * 在字符串中查找指定字符串，并返回第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code>。
     * <pre>
     * StringUtil.indexOf(null, *)          = -1
     * StringUtil.indexOf(*, null)          = -1
     * StringUtil.indexOf("", "")           = 0
     * StringUtil.indexOf("aabaabaa", "a")  = 0
     * StringUtil.indexOf("aabaabaa", "b")  = 2
     * StringUtil.indexOf("aabaabaa", "ab") = 1
     * StringUtil.indexOf("aabaabaa", "")   = 0
     * </pre>
     *
     * @param str 要扫描的字符串
     * @param searchStr 要查找的字符串
     *
     * @return 第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code>
     */
    public static int indexOf(String str, String searchStr) {
        if ((str == null) || (searchStr == null)) {
            return -1;
        }
        return str.indexOf(searchStr);
    }
    
    /**
     * 取指定字符串的子串。
     * 负的索引代表从尾部开始计算。如果字符串为<code>null</code>，则返回<code>null</code>。
     * @param str 字符串
     * @param start 起始索引，如果为负数，表示从尾部计算
     * @param end 结束索引（不含），如果为负数，表示从尾部计算
     *
     * @return 子串，如果原始串为<code>null</code>，则返回<code>null</code>
     */
    public static String substring(String str, int start, int end) {
        if (str == null) return null;
        if (end < 0) end = str.length() + end;
        if (start < 0) start = str.length() + start;
        if (end > str.length()) end = str.length();
        if (start > end) return EMPTY_STRING;
        if (start < 0) start = 0;
        if (end < 0) end = 0;
        return str.substring(start, end);
    }
    
    /**
     * 检查字符串中是否包含指定的字符串。如果字符串为<code>null</code>，将返回<code>false</code>。
     * <pre>
     * StringUtil.contains(null, *)     = false
     * StringUtil.contains(*, null)     = false
     * StringUtil.contains("", "")      = true
     * StringUtil.contains("abc", "")   = true
     * StringUtil.contains("abc", "a")  = true
     * StringUtil.contains("abc", "z")  = false
     * </pre>
     *
     * @param str 要扫描的字符串
     * @param searchStr 要查找的字符串
     *
     * @return 如果找到，则返回<code>true</code>
     */
    public static boolean contains(String str, String searchStr) {
        if ((str == null) || (searchStr == null)) {
            return false;
        }

        return str.indexOf(searchStr) >= 0;
    }
    
   /**
    * 
    * Description :  去掉空字符
    * @param str
    * @return
    */
	public static String toTrim(String str) {
		String s = "";
		if (null != str) {
			s = str.trim();
		}
		return s;
	}
    

}
