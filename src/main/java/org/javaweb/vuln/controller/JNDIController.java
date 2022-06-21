package org.javaweb.vuln.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/JNDI/")
public class JNDIController {

	public Map<String, Object> lookup(String url) throws NamingException {
		InitialContext ctx    = new InitialContext();
		final Object   lookup = ctx.lookup(url);

		return new HashMap<String, Object>() {{
			put("data", lookup);
		}};
	}

	@GetMapping("/get/lookup.do")
	public Map<String, Object> getLookup(String url) throws Exception {
		return lookup(url);
	}

	@PostMapping("/post/lookup.do")
	public Map<String, Object> postLookup(String url) throws Exception {
		return lookup(url);
	}

	@PostMapping("/cookie/lookup.do")
	public Map<String, Object> cookieLookup(@CookieValue(name = "url") String url) throws Exception {
		return lookup(url);
	}

	@PostMapping("/header/lookup.do")
	public Map<String, Object> headerLookup(@RequestHeader(name = "url") String url) throws Exception {
		return lookup(url);
	}

	@PostMapping(value = "/json/lookup.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, Object> jsonLookup(@RequestBody Map<String, Object> map) throws Exception {
		return lookup((String) ((Map) map.get("root")).get("url"));
	}

	@PostMapping("/form/lookup.do")
	public Map<String, Object> multipartLookup(MultipartFile file) throws Exception {
		return lookup(file.getOriginalFilename());
	}

}
