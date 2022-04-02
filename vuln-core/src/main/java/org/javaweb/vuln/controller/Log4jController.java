package org.javaweb.vuln.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/Log4j/")
public class Log4jController {

	@RequestMapping("/log4j.do")
	public Map<String, Object> log4j(String log) {
		Map<String, Object> data   = new LinkedHashMap<String, Object>();
		Logger              logger = LogManager.getLogger(Log4jController.class);
		data.put("logger", logger);
		logger.error(log);
		return data;
	}
}
