package org.javaweb.vuln.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/Test/")
public class TestController {

	@ResponseBody
	@GetMapping("/test.do")
	public Map<String, Object> test(String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("msg", "请求成功！");
		return map;
	}

	@ResponseBody
	@PostMapping(value = "/test.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, Object> testJson(@RequestBody Map<String, Object> map) {
		map.put("msg", "请求成功！");
		return map;
	}

}
