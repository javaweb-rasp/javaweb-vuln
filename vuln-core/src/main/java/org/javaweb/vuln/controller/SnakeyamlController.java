package org.javaweb.vuln.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yaml.snakeyaml.Yaml;

import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/Yaml/")
public class SnakeyamlController {

	@ResponseBody
	@PostMapping(value = "/yaml.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, Object> snakeYaml(@RequestBody Map<String, Object> map) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();

		String xml  = (String) map.get("xml");
		Yaml   yaml = new Yaml();
		data.put("data", yaml.load(URLDecoder.decode(xml)));

		return data;
	}

}
