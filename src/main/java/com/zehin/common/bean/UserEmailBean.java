/*
 * @(#)UserEmailBean.java 
 * 
 * Copyright 2016 by 青岛众恒信息科技股份有限公司 . 
 * All rights reserved.
 *
 */
package com.zehin.common.bean;

/**
 *	日期		:	2016年1月15日<br>
 *	作者		:	liuxin<br>
 *	项目		:	zehinCommon<br>
 *	功能		:	用户邮箱、地址<br>
 */
public class UserEmailBean {
	
	private String email;// email地址
	private String name;// 自定义名称
	
	
	public UserEmailBean() {
		
	}
	
	public UserEmailBean(String email, String name) {
		super();
		this.email = email;
		this.name = name;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
