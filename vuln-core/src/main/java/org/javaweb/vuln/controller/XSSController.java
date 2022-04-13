package org.javaweb.vuln.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/XSS/")
public class XSSController {

	@ResponseBody
	@GetMapping("/get/xss.do")
	public String getXss(String str) {
		return str;
	}

	@ResponseBody
	@PostMapping("/post/xss.do")
	public String postXss(String str) {
		return str;
	}

	@ResponseBody
	@PostMapping("/cookie/xss.do")
	public String cookieXss(@CookieValue(name = "str") String str) {
		return str;
	}

	@ResponseBody
	@PostMapping("/header/xss.do")
	public String headerXss(@RequestHeader(name = "str") String str) {
		return str;
	}

	@PostMapping(value = "/json/xss.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, Object> jsonXss(@RequestBody Map<String, Object> map) {
		map.put("msg", "请求成功！");

		return map;
	}

	@ResponseBody
	@PostMapping("/form/xss.do")
	public String multipartXss(MultipartFile file) {
		return file.getOriginalFilename();
	}

}
