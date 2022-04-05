package org.javaweb.vuln.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/SSRF/")
public class SSRFController {

	@PostMapping(value = "/ssrf.do", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<byte[]> ssrf(@RequestBody Map<String, Object> map) throws IOException {
		String url   = (String) ((Map) map.get("root")).get("url");
		byte[] bytes = IOUtils.toByteArray(new URL(url).openConnection().getInputStream());

		return new ResponseEntity<byte[]>(bytes, OK);
	}

	@RequestMapping(value = "/okHttp3.do", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<byte[]> okHttp3(@RequestBody Map<String, Object> map) throws IOException {
		String  url   = (String) ((Map) map.get("root")).get("url");
		Request build = new Request.Builder().url(url).get().build();
		byte[]  bytes = new OkHttpClient().newCall(build).execute().body().bytes();

		return new ResponseEntity<byte[]>(bytes, OK);
	}

	@ResponseBody
	@RequestMapping(value = "/httpClient.do", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<byte[]> httpClient(@RequestBody Map<String, Object> map) throws IOException {
		String              url            = (String) ((Map) map.get("root")).get("url");
		CloseableHttpClient httpClient     = HttpClientBuilder.create().build();
		HttpEntity          responseEntity = httpClient.execute(new HttpGet(url)).getEntity();
		byte[]              bytes          = IOUtils.toByteArray(responseEntity.getContent());

		httpClient.close();

		return new ResponseEntity<byte[]>(bytes, OK);
	}

}
