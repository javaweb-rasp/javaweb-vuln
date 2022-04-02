package org.javaweb.vuln.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.javaweb.vuln.utils.HttpServletRequestUtils.getRemoteAddr;
import static org.javaweb.vuln.utils.HttpServletRequestUtils.getRequestURL;

@RestController
@RequestMapping("/Blacklist/")
public class BlacklistController {

	@RequestMapping("/blacklist.do")
	public Map<String, Object> blacklist() {
		final String remoteAddr = getRemoteAddr();

		return new HashMap<String, Object>() {{
			put("ip", remoteAddr);
		}};
	}

	@RequestMapping("/url.do")
	public Map<String, Object> url() {
		return new HashMap<String, Object>() {{
			put("url", getRequestURL());
		}};
	}

}
