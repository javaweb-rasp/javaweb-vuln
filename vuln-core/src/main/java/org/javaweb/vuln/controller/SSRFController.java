package org.javaweb.vuln.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/SSRF/")
public class SSRFController {

	public ResponseEntity<byte[]> httpConnection(String url) throws IOException {
		byte[] bytes = IOUtils.toByteArray(new URL(url).openConnection().getInputStream());

		return new ResponseEntity<byte[]>(bytes, OK);
	}

	public ResponseEntity<byte[]> okHttp3(String url) throws IOException {
		Request build = new Request.Builder().url(url).get().build();
		byte[]  bytes = new OkHttpClient().newCall(build).execute().body().bytes();

		return new ResponseEntity<byte[]>(bytes, OK);
	}

	public ResponseEntity<byte[]> httpClient(String url) throws IOException {
		CloseableHttpClient httpClient     = HttpClientBuilder.create().build();
		HttpEntity          responseEntity = httpClient.execute(new HttpGet(url)).getEntity();
		byte[]              bytes          = IOUtils.toByteArray(responseEntity.getContent());

		httpClient.close();

		return new ResponseEntity<byte[]>(bytes, OK);
	}

	@GetMapping("/get/httpConnection.do")
	public ResponseEntity<byte[]> getHttpConnection(String url) throws Exception {
		return httpConnection(url);
	}

	@PostMapping("/post/httpConnection.do")
	public ResponseEntity<byte[]> postHttpConnection(String url) throws Exception {
		return httpConnection(url);
	}

	@PostMapping("/cookie/httpConnection.do")
	public ResponseEntity<byte[]> cookieHttpConnection(@CookieValue(name = "url") String url) throws Exception {
		return httpConnection(url);
	}

	@PostMapping("/header/httpConnection.do")
	public ResponseEntity<byte[]> headerHttpConnection(@RequestHeader(name = "url") String url) throws Exception {
		return httpConnection(url);
	}

	@PostMapping(value = "/json/httpConnection.do", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<byte[]> jsonHttpConnection(@RequestBody Map<String, Object> map) throws Exception {
		return httpConnection((String) ((Map) map.get("root")).get("url"));
	}

	@PostMapping("/form/httpConnection.do")
	public ResponseEntity<byte[]> multipartHttpConnection(MultipartFile file) throws Exception {
		return httpConnection(file.getOriginalFilename());
	}

	@GetMapping("/get/okHttp3.do")
	public ResponseEntity<byte[]> getOkHttp3(String url) throws Exception {
		return okHttp3(url);
	}

	@PostMapping("/post/okHttp3.do")
	public ResponseEntity<byte[]> postOkHttp3(String url) throws Exception {
		return okHttp3(url);
	}

	@PostMapping("/cookie/okHttp3.do")
	public ResponseEntity<byte[]> cookieOkHttp3(@CookieValue(name = "url") String url) throws Exception {
		return okHttp3(url);
	}

	@PostMapping("/header/okHttp3.do")
	public ResponseEntity<byte[]> headerOkHttp3(@RequestHeader(name = "url") String url) throws Exception {
		return okHttp3(url);
	}

	@PostMapping(value = "/json/okHttp3.do", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<byte[]> jsonOkHttp3(@RequestBody Map<String, Object> map) throws Exception {
		return okHttp3((String) ((Map) map.get("root")).get("url"));
	}

	@PostMapping("/form/okHttp3.do")
	public ResponseEntity<byte[]> multipartOkHttp3(MultipartFile file) throws Exception {
		return okHttp3(file.getOriginalFilename());
	}

	@GetMapping("/get/httpClient.do")
	public ResponseEntity<byte[]> getHttpClient(String url) throws Exception {
		return httpClient(url);
	}

	@PostMapping("/post/httpClient.do")
	public ResponseEntity<byte[]> postHttpClient(String url) throws Exception {
		return httpClient(url);
	}

	@PostMapping("/cookie/httpClient.do")
	public ResponseEntity<byte[]> cookieHttpClient(@CookieValue(name = "url") String url) throws Exception {
		return httpClient(url);
	}

	@PostMapping("/header/httpClient.do")
	public ResponseEntity<byte[]> headerHttpClient(@RequestHeader(name = "url") String url) throws Exception {
		return httpClient(url);
	}

	@PostMapping(value = "/json/httpClient.do", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<byte[]> jsonHttpClient(@RequestBody Map<String, Object> map) throws Exception {
		return httpClient((String) ((Map) map.get("root")).get("url"));
	}

	@PostMapping("/form/httpClient.do")
	public ResponseEntity<byte[]> multipartHttpClient(MultipartFile file) throws Exception {
		return httpClient(file.getOriginalFilename());
	}

}
