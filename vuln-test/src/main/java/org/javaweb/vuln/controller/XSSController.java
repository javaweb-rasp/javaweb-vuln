package org.javaweb.vuln.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/XSS/")
public class XSSController {

	@ResponseBody
	@RequestMapping("/callback.do")
	public String callbackXSS(String str) {
		return str;
	}

	@ResponseBody
	@PostMapping(value = "/xss.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, Object> xss(@RequestBody Map<String, Object> map) {
		map.put("msg", "请求成功！");
		return map;
	}

}
