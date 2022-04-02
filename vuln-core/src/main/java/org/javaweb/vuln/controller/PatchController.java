package org.javaweb.vuln.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/Patch/")
public class PatchController {

	@RequestMapping(value = "/readFileTest.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, String> readFileTest(@RequestBody Map<String, Object> map) throws Exception {
		Map    root     = (Map) map.get("root");
		String fileName = (String) root.get("file");
		String suffix   = (String) root.get("suffix");
		File   file     = new File(fileName, suffix);

		String content = FileUtils.readFileToString(file, "UTF-8");

		Map<String, String> data = new LinkedHashMap<String, String>();
		data.put("file", file.getAbsolutePath());
		data.put("content", content);

		return data;
	}

}
