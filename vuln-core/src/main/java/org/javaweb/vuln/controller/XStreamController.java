package org.javaweb.vuln.controller;

import com.thoughtworks.xstream.XStream;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/XStream/")
public class XStreamController {

	@RequestMapping(value = "/xStream.do")
	@ResponseBody
	public Map<String, Object> xStream(String xml) {
		Map<String, Object> data    = new LinkedHashMap<String, Object>();
		XStream             xStream = new XStream();
		Object              obj     = xStream.fromXML(xml);

		data.put("data", obj);

		return data;
	}
}
