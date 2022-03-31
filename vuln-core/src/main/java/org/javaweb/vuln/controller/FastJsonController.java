package org.javaweb.vuln.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/FastJson/")
public class FastJsonController {

	@RequestMapping(value = "/fastJsonParseObject.do")
	public JSONObject fastJsonParser(String json) {
		return JSON.parseObject(json);
	}

}
