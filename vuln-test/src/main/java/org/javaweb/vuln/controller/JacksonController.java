package org.javaweb.vuln.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping(value = "/Jackson/")
public class JacksonController {

	@ResponseBody
	@RequestMapping(value = "/readValue.do")
	public Object fastJsonParseObject(String json) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enableDefaultTyping();
		return mapper.readValue(json, Object.class);
	}

}
