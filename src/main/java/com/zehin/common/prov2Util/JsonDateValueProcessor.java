/*
 * @(#)JsonDateValueProcessor.java 
 * 
 * Copyright 2016 by 青岛众恒信息科技股份有限公司 . 
 * All rights reserved.
 *
 */
package com.zehin.common.prov2Util;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 *	日期		:	2016年1月11日<br>
 *	作者		:	liuxin<br>
 *	项目		:	zehinCommon<br>
 *	功能		:	<br>
 */
public class JsonDateValueProcessor  implements JsonValueProcessor{

	private String format = "yyyy-MM-dd HH:mm:ss";

	public JsonDateValueProcessor() {
	}

	public JsonDateValueProcessor(String format) {
		this.format = format;
	}

	@Override
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		String[] obj = {};
		if (value instanceof Date[]) {
			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date[] dates = (Date[]) value;
			obj = new String[dates.length];
			for (int i = 0; i < dates.length; i++) {
				obj[i] = sf.format(dates[i]);
			}
		}
		return obj;
	}

	@Override
	public Object processObjectValue(String key, Object value,
			JsonConfig jsonConfig) {
		if (value instanceof Date) {
			String str = new SimpleDateFormat(format).format((Date) value);
			return str;
		}
		return value == null ? null : value.toString();

	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
