package org.javaweb.vuln.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_XML_VALUE;

@RestController
@RequestMapping("/SQL/")
public class SQLInjectionController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Map<String, Object> getSysUserByUsername(String username) {
		String sql = "select * from sys_user where username = '" + username + "'";

		return jdbcTemplate.queryForMap(sql);
	}

	@GetMapping("/get/sql.do")
	public Map<String, Object> getSQL(String username) {
		return getSysUserByUsername(username);
	}

	@PostMapping("/post/sql.do")
	public Map<String, Object> postSQL(String username) {
		return getSysUserByUsername(username);
	}

	@PostMapping("/cookie/sql.do")
	public Map<String, Object> cookieSQL(@CookieValue(name = "username") String username) {
		return getSysUserByUsername(username);
	}

	@PostMapping("/header/sql.do")
	public Map<String, Object> headerSQL(@RequestHeader(name = "username") String username) {
		return getSysUserByUsername(username);
	}

	@PostMapping(value = "/xml/sql.do", consumes = APPLICATION_XML_VALUE)
	public Map<String, Object> xmlSQL(@RequestBody Map<String, Object> map) {
		return getSysUserByUsername((String) map.get("username"));
	}

	@PostMapping(value = "/json/sql.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, Object> jsonSQL(@RequestBody Map<String, Object> map) {
		return getSysUserByUsername((String) map.get("username"));
	}

	@PostMapping("/form/sql.do")
	public Map<String, Object> multipartSQL(MultipartFile file) {
		return getSysUserByUsername(file.getOriginalFilename());
	}

}
