package org.javaweb.vuln.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/JNDI/")
public class JNDIController {

	@ResponseBody
	@RequestMapping("/jdbcRowSetImpl.do")
	public Map<String, Object> inject(final com.sun.rowset.JdbcRowSetImpl jdbc) {
		return new HashMap<String, Object>() {{
			put("data", jdbc);
		}};
	}

}
