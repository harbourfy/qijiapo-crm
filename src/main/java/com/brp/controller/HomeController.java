package com.brp.controller;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.brp.entity.UserEntity;
import com.brp.service.UserService;


@Controller
@RequestMapping("/")
public class HomeController {

	private final static Logger LOG = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private UserService userService;
	

	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		UserEntity loginUser = null;
		ModelAndView mv = new ModelAndView("out/login");
		if (isEmpty(account, password) || !"POST".equalsIgnoreCase(request.getMethod())) {
			return mv;
		}else{
			loginUser = userService.login(account, password);
			if(loginUser != null){
				HttpSession seesion = request.getSession();
				seesion.setAttribute("loginUser",loginUser);
				Long userId = loginUser.getId();
				/*List<MenuEntity> list = menuService.getMenuList(userId.toString());
				seesion.setAttribute("menuList",list);*/
			}else{
				mv.addObject("msg", "用户名或者密码有误");
				return mv;
			}
		}
		
		mv.setViewName("redirect:home");
		try {
			// 设置记住密码
			if ("true".equals(request.getParameter("rememberMe"))) {
				Cookie accountC = new Cookie("account", URLEncoder.encode(account, "UTF-8"));
				Cookie passwordC = new Cookie("password", password);

				accountC.setPath("/");
				passwordC.setPath("/");

				accountC.setMaxAge(7 * 24 * 60 * 60);
				passwordC.setMaxAge(7 * 24 * 60 * 60);

				response.addCookie(accountC);
				response.addCookie(passwordC);
			}
		} catch (Exception e) {
			
		} 
		
		return mv;
	}

	private boolean isEmpty(String account, String password) {
		return StringUtils.isEmpty(account) || StringUtils.isEmpty(password);
	}

	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie c : cookies) {
				if ("account".equals(c.getName())) {
					c.setMaxAge(0);
					response.addCookie(c);
				}
				if ("password".equals(c.getName())) {
					c.setMaxAge(0);
					response.addCookie(c);
				}
			}
		}

        HttpSession seesion = request.getSession();
		UserEntity loginUser = (UserEntity) seesion.getAttribute("loginUser");
        if(loginUser != null){
        	seesion.removeAttribute("loginUser");
        }

		return new ModelAndView("redirect:login");
	}

	@RequestMapping("/list_demo")
	public ModelAndView list(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/gateway/system/list");
		return mv;
	}
	
	@RequestMapping("/home")
	public ModelAndView home(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/home");
		HttpSession seesion = request.getSession();
		UserEntity loginUser = (UserEntity) seesion.getAttribute("loginUser");
		mav.addObject("loginUser", loginUser); //不想直接取
		
		return mav;
	}
	
	@RequestMapping("/register")
	public ModelAndView register(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/register");
		return mav;
	}


}
