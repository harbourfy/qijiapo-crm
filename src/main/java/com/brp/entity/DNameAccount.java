package com.brp.entity;

import java.io.Serializable;

/**
 * 域名账户
 * 
 * @author Administrator
 *
 */
public class DNameAccount implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String account;
	private String password;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
