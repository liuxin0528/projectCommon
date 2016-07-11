/*
 * @(#)EmailUtil.java 
 * 
 * Copyright 2016 by 青岛众恒信息科技股份有限公司 . 
 * All rights reserved.
 *
 */
package com.zehin.common.util;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.commons.net.util.Base64;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.zehin.common.bean.UserEmailBean;

/**
 *	日期		:	2016年1月15日<br>
 *	作者		:	liuxin<br>
 *	项目		:	zehinCommon<br>
 *	功能		:	邮件工具类<br>
 */
public class EmailUtil {
	
	public static String sendServer;
	
	public static String userName;
	public static String userPwd;
	
	private static String from;
	private static String fromName;
	
	
	static{
		sendServer = PropertiesUtils.getProperties("email.send.server");
		
		// 设置用户名密码
		userName = PropertiesUtils.getProperties("email.send.userName");
		userPwd = PropertiesUtils.getProperties("email.send.userPwd");
				
		from = PropertiesUtils.getProperties("email.send.from");
		fromName = PropertiesUtils.getProperties("email.send.fromName");
	}
	
	/**
	 * 
	 * Description : 发送简单文字邮件
	 * @param subject 主题
	 * @param msg 消息内容
	 * @param userEmails 用户邮箱地址列表
	 * @param replyEmails 回复邮箱地址列表
	 * @throws EmailException
	 * @throws UnsupportedEncodingException
	 */
	public static void sendSimpleEmail(String subject, String msg,List<UserEmailBean> userEmails, List<UserEmailBean> replyEmails) throws EmailException, UnsupportedEncodingException {
		SimpleEmail email = new SimpleEmail();
		
		email.setHostName(sendServer);
		
		email.setAuthentication(userName, userPwd);

		if (!StringUtil.isEmpty(fromName)) {
			try {
				fromName = new String(fromName.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				fromName = null;
			}
		}
		email.setFrom(from, fromName);
		email.setCharset("UTF-8");

		email.setSubject(subject);
		email.setMsg(msg);

		for (UserEmailBean userEmail : userEmails) {
			email.addTo(userEmail.getEmail(), userEmail.getName());
		}
		if(replyEmails != null && replyEmails.size() > 0){
			List<InternetAddress> replyToList = new ArrayList<InternetAddress>();
			for (UserEmailBean replyEmail : replyEmails) {
				InternetAddress replyTo = new InternetAddress(replyEmail.getEmail(),replyEmail.getName(),"UTF-8");
				replyToList.add(replyTo);
			}
			email.setReplyTo(replyToList);
		}

		email.send();
	}
	
	/**
	 * 发送带附件的文本邮件 Description :
	 * 
	 * @param subject
	 * @param msg
	 * @param files
	 * @param userEmails
	 * @param replyEmails 回复邮箱地址列表
	 * @throws EmailException
	 * @throws UnsupportedEncodingException 
	 */
	public static void sendEmail(String subject, String msg, List<File> files,
			List<UserEmailBean> userEmails, List<UserEmailBean> replyEmails) throws EmailException, UnsupportedEncodingException {
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(sendServer);
		email.setAuthentication(userName, userPwd);

		if (!StringUtil.isEmpty(fromName)) {
			try {
				fromName = new String(fromName.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				fromName = null;
			}
		}
		email.setFrom(from, fromName);
		email.setCharset("UTF-8");

		email.setSubject(subject);
		email.setMsg(msg);
		for (File file : files) {
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(file.getPath());
			attachment.setName(file.getName());
			// 设置附件描述
			attachment.setDescription(subject);
			// 设置附件类型
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			// 添加附件
			email.attach(attachment);
		}

		for (UserEmailBean userEmail : userEmails) {
			email.addTo(userEmail.getEmail(), userEmail.getName());
		}
		if(replyEmails != null && replyEmails.size() > 0){
			List<InternetAddress> replyToList = new ArrayList<InternetAddress>();
			for (UserEmailBean replyEmail : replyEmails) {
				InternetAddress replyTo = new InternetAddress(replyEmail.getEmail(),replyEmail.getName(),"UTF-8");
				replyToList.add(replyTo);
			}
			email.setReplyTo(replyToList);
		}

		email.send();
	}

	/**
	 * Description :发送带附件的HTML邮件
	 * 
	 * @param subject
	 * @param htmlMsg  HTML格式
	 * @param files
	 * @param userEmails
	 * @param replyEmails 回复邮箱地址列表
	 * @throws EmailException
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException 
	 */
	public static void sendHTMLEmail(String subject, String htmlMsg,
			List<File> files, List<UserEmailBean> userEmails, List<UserEmailBean> replyEmails)throws EmailException, MalformedURLException, UnsupportedEncodingException {
		HtmlEmail email = new HtmlEmail();
		email.setHostName(sendServer);
		email.setAuthentication(userName, userPwd);

		if (!StringUtil.isEmpty(fromName)) {
			try {
				fromName = new String(fromName.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				fromName = null;
			}
		}
		email.setFrom(from, fromName);
		email.setCharset("UTF-8");

		email.setSubject(subject);
		String tempMsg = new String(htmlMsg);
		Pattern pattern = Pattern.compile("src=[\",\'](.*?)[\",\']");
		Matcher matcher = pattern.matcher(htmlMsg);
		int index = 0;
		while (matcher.find()) {
			int start = matcher.start(1);
			int end = matcher.end(1);
			String imgUrl = tempMsg.substring(start, end);
			htmlMsg = htmlMsg.replace(imgUrl,
					"cid:" + email.embed(new URL(imgUrl), "image" + index));
			index++;
		}
		email.setHtmlMsg(htmlMsg);
		email.setTextMsg("客户端不支持HTML格式邮件，请尝试使用其他邮件客户端");

		for (File file : files) {
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(file.getPath());
			attachment.setName(file.getName());
			// 设置附件描述
			attachment.setDescription(subject);
			// 设置附件类型
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			// 添加附件
			email.attach(attachment);
		}

		for (UserEmailBean userEmail : userEmails) {
			email.addTo(userEmail.getEmail(), userEmail.getName());
		}
		if(replyEmails != null && replyEmails.size() > 0){
			List<InternetAddress> replyToList = new ArrayList<InternetAddress>();
			for (UserEmailBean replyEmail : replyEmails) {
				InternetAddress replyTo = new InternetAddress(replyEmail.getEmail(),replyEmail.getName(),"UTF-8");
				replyToList.add(replyTo);
			}
			email.setReplyTo(replyToList);
		}

		email.send();
	}

	/**
	 * Description :接收邮件 每次接受一封未读邮件
	 * @param callback
	 * @return
	 * @throws Exception
	 */
	public static EmailInfo receiveEmail(EmailCallback callback) throws Exception {
		Store store = connectToEmailServer();
		IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);
		Message message[] = folder.getMessages();
		EmailInfo email = null;
		for (int i = message.length - 1; i >= 0; i--) {
			email = new EmailInfo();
			MimeMessage mimeMessage = (MimeMessage) message[i];
			//是否中断收取邮件 
			if (email.hasRead(mimeMessage)) {
				email = null;
				continue;
			}
			//初始化邮件标题、内容、附件等
			email.initEmailInfo(mimeMessage);
			// 设置已读 
			message[i].setFlag(Flags.Flag.SEEN, true);
			if(callback.checkEmail(email)){
				email.setCheckState(true);
				if(email.hasAttachment){
					email.callback = callback;
					email.saveAttachMent(mimeMessage);
				}
			}else{
				email.setCheckState(false);
				email.callback = null;
				//email.saveAttachMent(mimeMessage);
			}
			return email;
		}

		closeConnection(store);
		return email;
	}

	/**
	 * 
	 * Description : 连接到邮箱服务器
	 * @return
	 * @throws MessagingException
	 */
	private static Store connectToEmailServer() throws MessagingException {
		Properties props = PropertiesUtils.getProp();
		props.put("mail.imap.host", PropertiesUtils.getProperties("email.receive.server"));
		props.put("mail.store.protocol", PropertiesUtils.getProperties("email.receive.protocol"));

		props.put("mail.smtp.auth", "true");

		// 用户名密码
		String userName = PropertiesUtils.getProperties("email.receive.userName");
		String userPwd = PropertiesUtils.getProperties("email.receive.userPwd");
		
		Session session = Session.getDefaultInstance(props);
		IMAPStore store = (IMAPStore) session.getStore();
		store.connect(userName, userPwd);
		System.out.println("成功连接邮箱");
		return store;
	}

	/**
	 * 
	 * Description : 关闭连接
	 * @param store
	 */
	private static void closeConnection(Store store) {
		try {
			Folder folder = store.getFolder("INBOX");
			if (folder.isOpen()) {
				folder.close(true);
			}
			store.close();
		} catch (MessagingException e) {
			System.out.println("关闭邮箱连接失败");
			e.printStackTrace();
		}
	}

	public static interface EmailCallback {
		//检查邮件 验证是否收取邮件
		public boolean checkEmail(EmailInfo email);
		//保存附件
		public void saveAttachment(EmailInfo email, String fileName, InputStream inputStream) throws Exception;
		
		
	}
	
	/**
	 * 日期 : 2013-6-27<br>
	 * 作者 : liuxin<br>
	 * 项目 : accident<br>
	 * 功能 : <br>
	 */
	public static class EmailInfo {
		
		private StringBuffer bodyText = new StringBuffer();// 存放邮件内容
		private StringBuffer otherContent = new StringBuffer();// 其他内容
		
		private String from;
		private String fromStaff;
		private String toAddress;
		private String ccAddress;
		private String bccAddress;
		private String subject;
		private String sentDate;
		private boolean replySign;
		private boolean hasRead;
		private boolean hasAttachment;
		private List<String> attachment = new ArrayList<String>(); // 附件列表
		private String messageId;//邮件id
		private int messageNumber;//邮件在邮箱中相对位置，可直接定位邮件
		private String dateformat = "yy-MM-dd HH:mm"; // 默认的日前显示格式
		
		private boolean checkState;
		private EmailCallback callback;
		
		public EmailInfo(){
		}
		
		public void initEmailInfo(MimeMessage mimeMessage) throws Exception{
			this.from = getFrom(mimeMessage);
			this.toAddress = getMailAddress(mimeMessage, "to");
			this.ccAddress = getMailAddress(mimeMessage, "cc");
			this.bccAddress = getMailAddress(mimeMessage, "bcc");
			this.subject = getSubject(mimeMessage);
			this.sentDate = getSentDate(mimeMessage);
			this.replySign = getReplySign(mimeMessage);
			this.hasRead = hasRead(mimeMessage);
			this.hasAttachment = isContainAttach(mimeMessage);
			this.messageId = getMessageId(mimeMessage);
			this.messageNumber = getMessageNumber(mimeMessage);
			getMailContent(mimeMessage);
		}
		
		/**
		 * 
		 * Description : 获得发件人的地址和姓名
		 * @param mimeMessage
		 * @return
		 * @throws Exception
		 */
		private String getFrom(MimeMessage mimeMessage) throws Exception {
			InternetAddress address[] = (InternetAddress[]) mimeMessage
					.getFrom();
			String from = address[0].getAddress();
			if (from == null)
				from = "";
			String personal = address[0].getPersonal();
			if (personal == null)
				personal = "";
			String fromaddr = personal + "<" + from + ">";
			return fromaddr;
		}

		/**
		 * 
		 * Description : 获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同 "to"----收件人 "cc"---抄送人地址                 "bcc"---密送人地址
		 * @param mimeMessage
		 * @param type
		 * @return
		 * @throws Exception
		 */
		private String getMailAddress(MimeMessage mimeMessage, String type) throws Exception {
			String mailaddr = "";
			String addtype = type.toUpperCase();
			InternetAddress[] address = null;
			if (addtype.equals("TO") || addtype.equals("CC") || addtype.equals("BCC")) {
				if (addtype.equals("TO")) {
					address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.TO);
				} else if (addtype.equals("CC")) {
					address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.CC);
				} else {
					address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.BCC);
				}
				if (address != null) {
					for (int i = 0; i < address.length; i++) {
						String email = address[i].getAddress();
						if (email == null){
							email = "";
						}else {
							email = MimeUtility.decodeText(email);
						}
						String personal = address[i].getPersonal();
						if (personal == null){
							personal = "";
						}else {
							personal = MimeUtility.decodeText(personal);
						}
						String compositeto = personal + "<" + email + ">";
						mailaddr += "," + compositeto;
					}
					mailaddr = mailaddr.substring(1);
				}
			} else {
				throw new Exception("Error emailaddr type!");
			}
			return mailaddr;
		}

		/**
		 * 
		 * Description : 获得邮件主题
		 * @param mimeMessage
		 * @return
		 * @throws MessagingException
		 */
		private String getSubject(MimeMessage mimeMessage) throws MessagingException {
			String subject = "";
			try {
				subject = MimeUtility.decodeText(mimeMessage.getSubject());
				if (subject == null)
					subject = "";
			} catch (Exception exce) {
			}
			return subject;
		}

		/**
		 * 
		 * Description : 获得邮件发送日期
		 * @param mimeMessage
		 * @return
		 * @throws Exception
		 */
		private String getSentDate(MimeMessage mimeMessage) throws Exception {
			Date sentdate = mimeMessage.getSentDate();
			SimpleDateFormat format = new SimpleDateFormat(dateformat);
			return format.format(sentdate);
		}

		/**
		 * 
		 * Description : 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件                          主要是根据MimeType类型的不同执行不同的操作，一步一步的解析
		 * @param part
		 * @throws Exception
		 */
		private void getMailContent(Part part) throws Exception {
			String contentType = part.getContentType();
			int nameIndex = contentType.indexOf("name");
			boolean conName = false;
			if (nameIndex != -1){
				conName = true;
			}
			if (part.isMimeType("text/plain") && !conName) {
				bodyText.append(StringUtil.nvl(part.getContent()));
			} else if (part.isMimeType("text/html") && !conName) {
				bodyText.append(StringUtil.nvl(part.getContent()));
			} else if (part.isMimeType("multipart/*")) {
				Multipart multipart = (Multipart) part.getContent();
				int counts = multipart.getCount();
				for (int i = 0; i < counts; i++) {
					getMailContent(multipart.getBodyPart(i));
				}
			} else if (part.isMimeType("message/rfc822")) {
				getMailContent((Part) part.getContent());
			} else {
				otherContent.append(StringUtil.nvl(part.getContent()));
			}
		}

		/**
		 * 
		 * Description : 判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false"
		 * @param mimeMessage
		 * @return
		 * @throws MessagingException
		 */
		private boolean getReplySign(MimeMessage mimeMessage) throws MessagingException {
			boolean replysign = false;
			String needreply[] = mimeMessage
					.getHeader("Disposition-Notification-To");
			if (needreply != null) {
				replysign = true;
			}
			return replysign;
		}

		/**
		 * 
		 * Description : 获得此邮件的Message-ID
		 * @param mimeMessage
		 * @return
		 * @throws MessagingException
		 */
		private String getMessageId(MimeMessage mimeMessage) throws MessagingException {
			return mimeMessage.getMessageID();
		}

		/**
		 * 
		 * Description : 获得此邮件的Message-number
		 * @param mimeMessage
		 * @return
		 * @throws MessagingException
		 */
		private int getMessageNumber(MimeMessage mimeMessage) throws MessagingException {
			return mimeMessage.getMessageNumber();
		}

		/**
		 * 
		 * Description : 【判断此邮件是否已读，如果未读返回返回false,反之返回true】
		 * @param mimeMessage
		 * @return
		 * @throws MessagingException
		 */
		private boolean hasRead(MimeMessage mimeMessage) throws MessagingException {
			boolean hasRead = false;
			Flags flags = mimeMessage.getFlags();
			if (flags.contains(Flags.Flag.SEEN)) {
				hasRead = true;
			}
			return hasRead;
		}

		/**
		 * 
		 * Description : 判断此邮件是否包含附件
		 * @param part
		 * @return
		 * @throws Exception
		 */
		private boolean isContainAttach(Part part) throws Exception {
			boolean attachflag = false;
			if (part.isMimeType("multipart/*")) {
				Multipart mp = (Multipart) part.getContent();
				for (int i = 0; i < mp.getCount(); i++) {
					BodyPart mpart = mp.getBodyPart(i);
					String disposition = mpart.getDisposition();
					if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))){
						attachflag = true;
					}else if (mpart.isMimeType("multipart/*")) {
						attachflag = isContainAttach((Part) mpart);
					} else {
						String contentType = mpart.getContentType();
						if (contentType.toLowerCase().indexOf("application") != -1)
							attachflag = true;
						if (contentType.toLowerCase().indexOf("name") != -1)
							attachflag = true;
					}
				}
			} else if (part.isMimeType("message/rfc822")) {
				attachflag = isContainAttach((Part) part.getContent());
			}
			return attachflag;
		}

		/**
		 * 
		 * Description : 【保存附件】
		 * @param part
		 * @return
		 * @throws Exception
		 */
		private String saveAttachMent(Part part) throws Exception {
			String fileName = "";
	        StringBuffer stb=new StringBuffer();
	        if (part.isMimeType("multipart/*")) {
	            Multipart mp = (Multipart) part.getContent();
	            for (int i = 0; i < mp.getCount(); i++) {
	                BodyPart mpart = mp.getBodyPart(i);
	                String disposition = mpart.getDisposition();
	                if ((disposition != null)
	                        && ((disposition.equals(Part.ATTACHMENT)) || (disposition
	                                .equals(Part.INLINE)))) {
	                    fileName = mpart.getFileName();
	                    if (fileName.toLowerCase().indexOf("gb2312") != -1) {
	                    	fileName = MimeUtility.decodeText(fileName).trim();
	                    }
	                    if (fileName.toLowerCase().indexOf("gbk") != -1) {
	                    	String s = fileName.substring(8, fileName.lastIndexOf("?="));
	                    	if(s.indexOf(".") == -1){
	                    		fileName = new String(Base64.decodeBase64(s), "gbk");
	                    	}else{
	                    		fileName = s;
	                    	}
	                    }else if(fileName.toLowerCase().indexOf("utf") != -1){
	                    	String s = fileName.substring(10, fileName.lastIndexOf("?="));
	                    	fileName = new String(Base64.decodeBase64(s), "utf-8");
	                    }
	                    
	                    stb.append(fileName).append(",");
	                    saveFile(fileName, mpart.getInputStream());
	                } else if (mpart.isMimeType("multipart/*")) {
	                    saveAttachMent(mpart);
	                } else {
	                    fileName = mpart.getFileName();
	                    if ((fileName != null) && (fileName.toLowerCase().indexOf("GB2312") != -1)) {
	                    	fileName = MimeUtility.decodeText(fileName);
	                        stb.append(fileName).append(",");
	                        saveFile(fileName, mpart.getInputStream());
	                    }
	                }
	            }
	        } else if (part.isMimeType("message/rfc822")) {
	            saveAttachMent((Part) part.getContent());
	        }
	        return stb.toString();
		}

		/**
		 * 
		 * Description : 【真正的保存附件到指定目录里】
		 * @param fileName
		 * @param inputStream
		 * @throws Exception
		 */
		private void saveFile(String fileName, InputStream inputStream) throws Exception{
			attachment.add(fileName);
			if(callback != null){
				callback.saveAttachment(this, fileName, inputStream);
			}
		}

		// getter setter
		public String getFrom() {
			return from;
		}

		public StringBuffer getBodyText() {
			return bodyText;
		}

		public void setBodyText(StringBuffer bodyText) {
			this.bodyText = bodyText;
		}

		public void setFrom(String from) {
			this.from = from;
		}

		public StringBuffer getOtherContent() {
			return otherContent;
		}

		public void setOtherContent(StringBuffer otherContent) {
			this.otherContent = otherContent;
		}

		public String getFromStaff() {
			return fromStaff;
		}

		public void setFromStaff(String fromStaff) {
			this.fromStaff = fromStaff;
		}

		public String getToAddress() {
			return toAddress;
		}

		public void setToAddress(String toAddress) {
			this.toAddress = toAddress;
		}

		public String getCcAddress() {
			return ccAddress;
		}

		public void setCcAddress(String ccAddress) {
			this.ccAddress = ccAddress;
		}

		public String getBccAddress() {
			return bccAddress;
		}

		public void setBccAddress(String bccAddress) {
			this.bccAddress = bccAddress;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getSentDate() {
			return sentDate;
		}

		public void setSentDate(String sentDate) {
			this.sentDate = sentDate;
		}

		public boolean isReplySign() {
			return replySign;
		}

		public void setReplySign(boolean replySign) {
			this.replySign = replySign;
		}

		public boolean isHasRead() {
			return hasRead;
		}

		public void setHasRead(boolean hasRead) {
			this.hasRead = hasRead;
		}

		public boolean isHasAttachment() {
			return hasAttachment;
		}

		public void setHasAttachment(boolean hasAttachment) {
			this.hasAttachment = hasAttachment;
		}

		public List<String> getAttachment() {
			return attachment;
		}

		public void setAttachment(List<String> attachment) {
			this.attachment = attachment;
		}

		public String getMessageId() {
			return messageId;
		}

		public void setMessageId(String messageId) {
			this.messageId = messageId;
		}

		public int getMessageNumber() {
			return messageNumber;
		}

		public void setMessageNumber(int messageNumber) {
			this.messageNumber = messageNumber;
		}

		public String getDateformat() {
			return dateformat;
		}

		public void setDateformat(String dateformat) {
			this.dateformat = dateformat;
		}

		public boolean getCheckState() {
			return checkState;
		}

		public void setCheckState(boolean checkState) {
			this.checkState = checkState;
		}
		
	}

}
