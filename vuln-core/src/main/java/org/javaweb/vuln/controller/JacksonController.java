package org.javaweb.vuln.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javaweb.vuln.entity.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping(value = "/Jackson/")
public class JacksonController {

	@ResponseBody
	@RequestMapping(value = "/readValue.do")
	public Object jacksonReadValue(String json) throws IOException {
		return new ObjectMapper().enableDefaultTyping().readValue(json, SysUser.class);
	}

}
