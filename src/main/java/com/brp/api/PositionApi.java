package com.brp.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.brp.base.enums.PositionEnum;
import com.brp.entity.PositionEntity;
import com.brp.service.CompanyService;
import com.brp.service.PositionService;
import com.brp.util.JsonUtils;
import com.brp.util.SHA1Utils;
import com.brp.util.TryParseUtils;
import com.brp.util.api.model.ApiCode;
import com.brp.util.api.model.JsonData;
import com.brp.util.query.PositionQuery;

/** 
 * <p>Project: MyBase</p> 
 * <p>Title: v.java</p> 
 * <p>Description: TODO</p> 
 * <p>Copyright (c) 2016 xjw Consultancy Services</p>
 * <p>All Rights Reserved.</p>
 * @author <a href="mailto:shenyuchuan@itiaoling.com">申鱼川</a>
 */
@Controller
@RequestMapping("/api/position")
public class PositionApi {
	
	@Autowired
	private PositionService positionService;
	@Autowired
	private CompanyService companyService;
	@RequestMapping(value = "/getPositionByCompanyId", method = RequestMethod.POST)
	@ResponseBody
	public String getPositionByCompanyId(@RequestBody JSONObject jsonObject){
		JsonData<List<PositionEntity>> jsonData = new JsonData<List<PositionEntity>>();
		try{
			String companyId = jsonObject.getString("companyId");
			String secret = jsonObject.getString("secret");
			String cId = jsonObject.getString("cId");
			
			boolean auth = false;
			if(StringUtils.isNotBlank(cId) && TryParseUtils.tryParse(cId, Long.class)){
				String mybaseSecret = companyService.getSecretByCid(cId);
				Map<String,Object> maps = new HashMap<String, Object>();
				maps.put("companyId", companyId);
				maps.put("secret", mybaseSecret);
				maps.put("cId", cId);
				String md5 = SHA1Utils.SHA1(maps);
				if(md5.equals(secret)){
					auth = true;
				}else{
					jsonData.setCode(ApiCode.AUTH_FAIL);
					jsonData.setMessage("验证失败");
				}
			}else{
				jsonData.setCode(ApiCode.ARGS_EXCEPTION);
				jsonData.setMessage("参数异常");
			}
			
			if(auth){
				PositionQuery positionQuery = new PositionQuery();
				positionQuery.setCompanyId(Long.parseLong(companyId));
				positionQuery.setPositionType(PositionEnum.USER_DEFINED.getPositionType());
				List<PositionEntity> list = positionService.getPositionList(positionQuery);
				if(list == null || list.size() < 1){
					positionQuery.setCompanyId(null);
					positionQuery.setPositionType(PositionEnum.SYSTEM_DEFINED.getPositionType());
					positionService.getPositionList(positionQuery);
				}
				jsonData.setCode(ApiCode.OK);
				jsonData.setMessage("操作成功");
				jsonData.setData(list);
			}else{
				jsonData.setCode(ApiCode.ARGS_EXCEPTION);
				jsonData.setMessage("参数异常");
			}
		}catch(Exception e){
			e.printStackTrace();
			jsonData.setCode(ApiCode.EXCEPTION);
			jsonData.setMessage("操作失败");
		}
		
		String result = JsonUtils.json2Str(jsonData);
		
		return result;
	}
	
}

