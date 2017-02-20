package com.brp.util;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 发送邮件需要使用的基本信息
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MailSenderInfo {
	// 发送邮件的服务器的IP和端口
	@Value("${mail.server.addr}")
	private String mailServerHost;
	@Value("${mail.server.port}")
	private String mailServerPort;
	// 邮件发送者的地址
	@Value("${mail.fromuser}")
	private String fromAddress;
	// 邮件接收者的地址
	@Value("${mail.touser}")
	private String toAddress; 
	
	//邮件抄送接受者地址
	private String[] msAddress = {};
	//private String[] msAddress = {"shenyuchuan@itiaoling.com"};
	// 登陆邮件发送服务器的用户名和密码 
	@Value("${mail.validate.user}")
	private String userName;
	@Value("${mail.validate.pwd}")
	private String password;
	// 是否需要身份验证
	private boolean validate = true;
	// 邮件主题
	private String subject;
	// 邮件的文本内容
	private String content;
	// 邮件附件的文件名
	private String[] attachFileNames;

	/**
	 * 获得邮件会话属性
	 */
	public Properties getProperties() {
		Properties p = new Properties();
		p.put("mail.smtp.host", this.mailServerHost);
		p.put("mail.smtp.port", this.mailServerPort);
		p.put("mail.smtp.auth", validate ? "true" : "false");
	    p.put("mail.smtp.starttls.enable","true"); 
	    p.put("mail.smtp.auth","true");
	    
		return p;
	}

	public String getMailServerHost() {
		return mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public String getMailServerPort() {
		return mailServerPort;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public String[] getAttachFileNames() {
		return attachFileNames;
	}

	public void setAttachFileNames(String[] fileNames) {
		this.attachFileNames = fileNames;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String textContent) {
		this.content = textContent;
	}

	public String[] getMsAddress() {
		return msAddress;
	}

	public void setMsAddress(String[] msAddress) {
		this.msAddress = msAddress;
	}
}
