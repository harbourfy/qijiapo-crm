package com.brp.service;

import java.util.List;

import com.brp.entity.PositionEntity;
import com.brp.util.query.PositionQuery;

/** 
 * <p>Project: MyBase</p> 
 * <p>Title: PositionService.java</p> 
 * <p>Description: TODO</p> 
 * <p>Copyright (c) 2016 xjw Consultancy Services</p>
 * <p>All Rights Reserved.</p>
 * @author <a href="mailto:shenyuchuan@itiaoling.com">申鱼川</a>
 */
public interface CompanyService {
	/**
	 * 该接口需要改造，不然secret很容易暴露
	 * @param cid
	 * @return
	 */
	String getSecretByCid(String cid);

}

