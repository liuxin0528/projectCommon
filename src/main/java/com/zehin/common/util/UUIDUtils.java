/*
 * @(#)UUIDUtils.java 
 * 
 * Copyright 2016 by 青岛众恒信息科技股份有限公司 . 
 * All rights reserved.
 *
 */
package com.zehin.common.util;

import java.util.UUID;

/**
 *	日期		:	2016年1月31日<br>
 *	作者		:	liuxin<br>
 *	项目		:	zehinCommon<br>
 *	功能		:	UUID工具类<br>
 */
public class UUIDUtils {
	
	/**
	 * 返回UUID，做为主键使用
	 * Description : 
	 * @return
	 */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}
