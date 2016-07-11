/*
 * @(#)PropertiesUtils.java 
 * 
 * Copyright 2016 by 青岛众恒信息科技股份有限公司 . 
 * All rights reserved.
 *
 */
package com.zehin.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *	日期		:	2016年1月15日<br>
 *	作者		:	liuxin<br>
 *	项目		:	zehinCommon<br>
 *	功能		:	配置文件工具类<br>
 */
public class PropertiesUtils {
	
	private final static String CONFIG_PROPERTIES = "config.properties";
	
	private static Properties prop;
	private static Map<String,Properties> propMap = new HashMap<String,Properties>();
	
	
	static {
		InputStream input = PropertiesUtils.class.getClassLoader().getResourceAsStream(CONFIG_PROPERTIES);
    	prop = new Properties();
    	try {
			prop.load(input);
			propMap.put(CONFIG_PROPERTIES, prop);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Description : 获取默认配置文件的配置属性
	 * @param propName
	 * @return
	 */
	public static String getProperties(String propName){
		if(prop != null){
			return prop.getProperty(propName);
		}
		return null;
	}
	
	/**
	 * 
	 * Description : 初始化读取配置文件---初始化自定义的文件信息
	 * @param fileName
	 * @return
	 */
	private static Properties getPropByName(String fileName){
		Properties prop = null;
		if(propMap.get(fileName) != null){
			prop = propMap.get(fileName);
		}else{
			InputStream input = PropertiesUtils.class.getClassLoader().getResourceAsStream("/" + fileName);
	    	prop = new Properties();
	    	try {
				prop.load(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return prop;
	}
	
	/**
	 * 
	 * Description :获取默认配置文件 属性 --自定义文件信息
	 * @param propName
	 * @param propFileName
	 * @return
	 */
	public static String getProperties(String propName, String propFileName){
		Properties prop = getPropByName(propFileName);
		if(prop != null){
			return prop.getProperty(propName);
		}
		return null;
	}
	
	/**
	 * 
	 * Description : 返回文件的Properties对象
	 * @return
	 */
	public static Properties getProp(){
		return prop;
	}

}
