package org.javaweb.vuln.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/DisableMethod/")
public class DisableMethodController {

	@RequestMapping(value = "/log4j.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, Object> log4j(@RequestBody Map<String, Object> map) {
		Map<String, Object> data   = new LinkedHashMap<String, Object>();
		String              userId = (String) ((Map) map.get("root")).get("userId");
		Logger              logger = LogManager.getLogger(DisableMethodController.class);

		try {
			Integer.parseInt(userId);
			data.put("msg", "请求成功！");
		} catch (NumberFormatException e) {
			logger.error(userId);
			data.put("msg", logger);
		}

		return data;
	}

}
