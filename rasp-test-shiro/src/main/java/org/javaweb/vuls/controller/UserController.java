package org.javaweb.vuls.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("demo")
public class UserController {

	@RequestMapping("/logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String username, String password, HttpServletRequest request) {
		String rand    = (String) request.getSession().getAttribute("rand");
		String captcha = WebUtils.getCleanParam(request, "captcha");

		if (!rand.equals(captcha)) {
			request.setAttribute("message_login", "验证码不正确");
			return InternalResourceViewResolver.FORWARD_URL_PREFIX + "/";
		}

		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(true);
		Subject currentUser = SecurityUtils.getSubject();

		try {
			currentUser.login(token);
		} catch (UnknownAccountException uae) {
			request.setAttribute("message_login", "未知账户");
		} catch (IncorrectCredentialsException ice) {
			request.setAttribute("message_login", "密码不正确");
		} catch (LockedAccountException lae) {
			request.setAttribute("message_login", "账户已锁定");
		} catch (ExcessiveAttemptsException eae) {
			request.setAttribute("message_login", "用户名或密码错误次数过多");
		} catch (AuthenticationException ae) {
			ae.printStackTrace();
			request.setAttribute("message_login", "用户名或密码不正确");
		}

		//验证是否登录成功
		if (currentUser.isAuthenticated()) {
			return "main";
		} else {
			token.clear();
			return InternalResourceViewResolver.FORWARD_URL_PREFIX + "/";
		}
	}

}
