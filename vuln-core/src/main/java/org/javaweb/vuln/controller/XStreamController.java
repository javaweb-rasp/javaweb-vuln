package org.javaweb.vuln.controller;

import com.thoughtworks.xstream.XStream;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/XStream/")
public class XStreamController {

	@PostMapping(value = "/xStream.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, Object> xStream(@RequestBody Map<String, Object> map) {
		Map<String, Object> data    = new LinkedHashMap<String, Object>();
		String              xml     = (String) map.get("xml");
		XStream             xStream = new XStream();
		Object              obj     = xStream.fromXML(xml);

		data.put("data", obj);

		return data;
	}

}
