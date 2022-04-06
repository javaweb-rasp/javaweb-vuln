package org.javaweb.vuln.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.apache.commons.io.FileUtils.copyInputStreamToFile;

@Controller
@RequestMapping(value = "/FileUpload/")
public class FileUploadController {

	@ResponseBody
	@RequestMapping(value = "/upload.do")
	public Map<String, Object> upload(@RequestParam("file") MultipartFile uploadFile, String dir) throws IOException {
		Map<String, Object> data     = new LinkedHashMap<String, Object>();
		String              fileName = uploadFile.getOriginalFilename();
		File                file     = new File(dir, fileName);

		copyInputStreamToFile(uploadFile.getInputStream(), file);

		data.put("file", file.getAbsolutePath());
		data.put("msg", "上传成功！");

		return data;
	}

}
