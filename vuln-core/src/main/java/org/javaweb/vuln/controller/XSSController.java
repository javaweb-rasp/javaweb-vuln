package org.javaweb.vuln.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_XML_VALUE;

@RestController
@RequestMapping("/XSS/")
public class XSSController {

	@GetMapping("/get/xss.do")
	public String getXss(String str) {
		return str;
	}

	@PostMapping("/post/xss.do")
	public String postXss(String str) {
		return str;
	}

	@PostMapping("/cookie/xss.do")
	public String cookieXss(@CookieValue(name = "str") String str) {
		return str;
	}

	@PostMapping("/header/xss.do")
	public String headerXss(@RequestHeader(name = "str") String str) {
		return str;
	}

	@PostMapping(value = "/xml/xss.do", consumes = APPLICATION_XML_VALUE)
	public Map<String, Object> xmlXss(@RequestBody Map<String, Object> map) {
		map.put("msg", "请求成功！");

		return map;
	}

	@PostMapping(value = "/json/xss.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, Object> jsonXss(@RequestBody Map<String, Object> map) {
		map.put("msg", "请求成功！");

		return map;
	}

	@PostMapping("/form/xss.do")
	public String multipartXss(MultipartFile file) {
		return file.getOriginalFilename();
	}

}
