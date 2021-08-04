package org.javaweb.vuls.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/XSS/")
public class XSSController {

	@RequestMapping("/callback.do")
	public @ResponseBody String callbackXSS(String str) {
		return str;
	}

}
