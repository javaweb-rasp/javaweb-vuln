package org.javaweb.vuln.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/SQL/")
public class SQLInjectionController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@RequestMapping(value = "/getSysUserByUsername.do")
	public Map<String, Object> getSysUserByUsername(String username) {
		String sql = "select * from sys_user where username = '" + username + "'";

		return jdbcTemplate.queryForMap(sql);
	}

	@PostMapping(value = "/getSysUserByUsernameJSON.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, Object> getSysUserByUsernameJSON(@RequestBody Map<String, Object> map) {
		String sql = "select * from sys_user where username = '" + map.get("username") + "'";

		return jdbcTemplate.queryForMap(sql);
	}

}
