package org.javaweb.vuln.controller;

import okhttp3.*;
import org.javaweb.utils.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URL;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/SSRF/")
public class SSRFController {

	@RequestMapping("/ssrf.do")
	public ResponseEntity<byte[]> ssrf(String url) throws IOException {
		byte[] bytes = IOUtils.toByteArray(new URL(url).openConnection().getInputStream());
		return new ResponseEntity<byte[]>(bytes, OK);
	}

	@RequestMapping("/okHttp3.do")
	public ResponseEntity<byte[]> okHttp3(String url) throws IOException {
		Request build = new Request.Builder().url(url).get().build();
		byte[]  bytes = new OkHttpClient().newCall(build).execute().body().bytes();

		return new ResponseEntity<byte[]>(bytes, OK);
	}

}
