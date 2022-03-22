package org.javaweb.vuln.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class ArticleController {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@RequestMapping(value = "/article.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, Object> getArticleByID(@RequestBody Map<String, Object> map) {
		String sql = "select * from sys_article where id = " + map.get("id");

		return jdbcTemplate.queryForMap(sql);
	}

}
