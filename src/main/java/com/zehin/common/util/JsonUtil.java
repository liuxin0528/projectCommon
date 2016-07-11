/*
 * @(#)JsonUtil.java 
 * 
 * Copyright 2016 by 青岛众恒信息科技股份有限公司 . 
 * All rights reserved.
 *
 */
package com.zehin.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.zehin.common.prov2Util.JsonDateValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

/**
 *	日期		:	2016年1月11日<br>
 *	作者		:	liuxin<br>
 *	项目		:	zehinCommon<br>
 *	功能		:	json工具类<br>
 */
public class JsonUtil {
	/**
	 * 
	 * Description : 从一个JSON 对象字符格式中得到一个java对象
	 * @param jsonString
	 * @param pojoCalss
	 * @return
	 */
	public static Object getObject4JsonString(String jsonString, Class pojoCalss) {
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		pojo = JSONObject.toBean(jsonObject, pojoCalss);
		return pojo;
	}

	/**
	 * 
	 * Description : 从json HASH表达式中获取一个map，改map支持嵌套功能
	 * @param jsonString
	 * @return
	 */
	public static Map getMap4Json(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map valueMap = new HashMap();
		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}
		return valueMap;
	}

	/**
	 * 
	 * Description : 将一个java对象转换为object对象
	 * @param object
	 * @return
	 */
	public static Map getMap4Object(Object object) {
		String josn = getJsonStringJavaPOJO(object);
		return getMap4Json(josn);
	}

	/**
	 * 
	 * Description : 从json数组中得到相应java数组
	 * @param jsonString
	 * @return
	 */
	public static Object[] getObjectArrayJson(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray.toArray();
	}

	/**
	 * 
	 * Description : 从json对象集合表达式中得到一个java对象列表
	 * @param jsonString
	 * @param pojoClass
	 * @return
	 */
	public static List getListJson(String jsonString, Class pojoClass) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object pojoValue;
		List list = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = JSONObject.toBean(jsonObject, pojoClass);
			list.add(pojoValue);
		}
		return list;
	}

	/**
	 * 
	 * Description :  从json数组中解析出java字符串数组
	 * @param jsonString
	 * @return
	 */
	public static String[] getStringArrayJson(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		String[] stringArray = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			stringArray[i] = jsonArray.getString(i);

		}
		return stringArray;
	}

	/**
	 * 
	 * Description : 从json数组中解析出javaLong型对象数组
	 * @param jsonString
	 * @return
	 */
	public static Long[] getLongArrayJson(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Long[] longArray = new Long[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			longArray[i] = jsonArray.getLong(i);

		}
		return longArray;
	}

	/**
	 * 
	 * Description : 从json数组中解析出java Integer型对象数组
	 * @param jsonString
	 * @return
	 */
	public static Integer[] getIntegerArrayJson(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Integer[] integerArray = new Integer[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			integerArray[i] = jsonArray.getInt(i);

		}
		return integerArray;
	}

	/**
	 * 
	 * Description :从json数组中解析出java Double型对象数组
	 * @param jsonString
	 * @return
	 */
	public static Double[] getDoubleArrayJson(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Double[] doubleArray = new Double[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			doubleArray[i] = jsonArray.getDouble(i);

		}
		return doubleArray;
	}

	/**
	 * 
	 * Description : ��java����ת����json�ַ�
	 * @param javaObj
	 * @return
	 */
	public static String getJsonStringJavaPOJO(Object javaObj) {
		JSONObject json;
		json = JSONObject.fromObject(javaObj);
		return json.toString();

	}


	/**
	 * 
	 * Description : 将java对象转换成json字符串
	 * @param javaObj
	 * @param dataFormat
	 * @return
	 */
	public static String getJsonStringJavaPOJO(Object javaObj,
			String dataFormat) {
		JSONObject json;
		JsonConfig jsonConfig = configJson(dataFormat);
		json = JSONObject.fromObject(javaObj, jsonConfig);
		return json.toString();

	}

	/**
	 * 
	 * Description :  JSON 时间解析器具
	 * @param datePattern
	 * @return
	 */
	public static JsonConfig configJson(String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "" });
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor(datePattern));

		return jsonConfig;
	}

	/**
	 * 
	 * Description : 除去不想生成的字段（特别适合去掉级联的对象）+时间转换
	 * @param excludes
	 * @param datePattern
	 * @return
	 */
	public static JsonConfig configJson(String[] excludes, String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		jsonConfig.setIgnoreDefaultExcludes(true);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor(datePattern));
		return jsonConfig;
	}
	
	/**
	 * 
	 * Description :将list转换为json字符串
	 * @param list
	 * @return
	 */
	public static String getJsonArray4JavaList(List list) {
		JSONArray jsonArray;
		jsonArray = JSONArray.fromObject(list);
		String jsonArrayStr = jsonArray.toString();
		jsonArrayStr = jsonArrayStr.substring(jsonArrayStr.indexOf("["));
		return jsonArray.toString();
	}

}
