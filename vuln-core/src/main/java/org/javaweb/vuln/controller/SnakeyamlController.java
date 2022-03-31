package org.javaweb.vuln.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yaml.snakeyaml.Yaml;

import java.net.URLDecoder;

@Controller
@RequestMapping("/Yaml/")
public class SnakeyamlController {

	@ResponseBody
	@RequestMapping("/yaml.do")
	public void snakeYaml(String poc) {
		Yaml yaml = new Yaml();
		yaml.load(URLDecoder.decode(poc));
	}

}
