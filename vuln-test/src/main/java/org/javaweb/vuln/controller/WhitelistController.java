package org.javaweb.vuln.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Whitelist/")
public class WhitelistController {

	@RequestMapping("/url.do")
	public Map<String, Object> url(final String xss) {
		return new HashMap<String, Object>() {{
			put("xss", xss);
		}};
	}

}
