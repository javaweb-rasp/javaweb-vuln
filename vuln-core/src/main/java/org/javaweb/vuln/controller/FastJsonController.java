package org.javaweb.vuln.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/FastJson/")
public class FastJsonController {

	/**
	 * Fastjson 1.2.2 - 1.2.4反序列化RCE示例
	 *
	 * @param json JSON字符串
	 * @return 反序列化结果
	 */
	@RequestMapping(value = "/fastJsonParseObject.do")
	public JSONObject fastJsonParseObject(String json) {
		// 使用FastJson反序列化，但必须启用SupportNonPublicField特性
		return JSON.parseObject(json, Object.class, new ParserConfig(), Feature.SupportNonPublicField);
	}

}
