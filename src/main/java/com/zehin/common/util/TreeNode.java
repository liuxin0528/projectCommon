/*
 * @(#)TreeNode.java 
 * 
 * Copyright 2016 by 青岛众恒信息科技股份有限公司 . 
 * All rights reserved.
 *
 */
package com.zehin.common.util;

import java.io.Serializable;
import java.util.List;

/**
 * 日期 : 2016年4月1日<br>
 * 作者 : liuxin<br>
 * 项目 : zehinCommon<br>
 * 功能 : 通用树节点--适用于jeasyui中的树节点 <br>
 */
public class TreeNode<T> implements Serializable {

	private String id;

	private String name;

	private String text;

	private String state;

	private boolean checked;

	private T attributes;

	private String iconCls;

	private List<TreeNode<T>> children;

	/**
	 * 无参的构造函数
	 */
	public TreeNode() {

	}

	/**
	 * 带全部参数的构造函数
	 * 
	 * @param id
	 * @param name
	 * @param text
	 * @param state
	 * @param checked
	 * @param attributes
	 * @param iconCls
	 * @param children
	 */
	public TreeNode(String id, String name, String text, String state, boolean checked, T attributes, String iconCls,
			List<TreeNode<T>> children) {
		super();
		this.id = id;
		this.name = name;
		this.text = text;
		this.state = state;
		this.checked = checked;
		this.attributes = attributes;
		this.iconCls = iconCls;
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public T getAttributes() {
		return attributes;
	}

	public void setAttributes(T attributes) {
		this.attributes = attributes;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public List<TreeNode<T>> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode<T>> children) {
		this.children = children;
	}

}
