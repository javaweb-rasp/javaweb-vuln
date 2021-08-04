package org.javaweb.vuls.controller;

import org.javaweb.utils.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URL;

@Controller
@RequestMapping("/SSRF/")
public class SSRFController {

	@RequestMapping("/ssrf.do")
	public ResponseEntity<byte[]> ssrf(String url) throws Exception {
		byte[] bytes = IOUtils.toByteArray(new URL(url).openConnection().getInputStream());

		return new ResponseEntity<byte[]>(bytes, HttpStatus.OK);
	}

}
