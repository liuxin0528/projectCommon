/*
 * @(#)HttpRequest.java 
 * 
 * Copyright 2016 by 青岛众恒信息科技股份有限公司 . 
 * All rights reserved.
 *
 */
package com.zehin.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * 日期 : 2016年4月1日<br>
 * 作者 : liuxin<br>
 * 项目 : zehinCommon<br>
 * 功能 : 请求工具类<br>
 */
public class HttpRequest {

	/**
	 * Description:向指定url发送get方法的请求
	 * @param url:发送请求的url
	 * @param param:请求参数，请求参数应该是  name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realurl = new URL(urlNameString);
			// 打开和url之间的连接
			URLConnection connection = realurl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取url的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送get请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * Description:向指定 url 发送post方法的请求
	 * @param url: 发送请求的 url
	 * @param param:请求参数，请求参数应该是      name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendpost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realurl = new URL(url);
			// 打开和url之间的连接
			URLConnection conn = realurl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送post请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取urlConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取url的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 post 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

}
