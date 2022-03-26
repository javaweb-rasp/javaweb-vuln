package org.javaweb.vuln.controller;

import org.javaweb.vuln.commons.APIMapping;
import org.javaweb.vuln.dao.SysArticleDAO;
import org.javaweb.vuln.entity.SysArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ArticleController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private SysArticleDAO sysArticleDAO;

	@APIMapping(value = "/getArticles.do")
	public List<SysArticle> getArticles(@RequestBody Map<String, Object> map) {
		return sysArticleDAO.getSysArticleList();
	}

	@APIMapping(value = "/getArticleById.do")
	public Map<String, Object> getArticleById(@RequestBody Map<String, Object> map) {
		String sql = "select * from sys_article where id = " + map.get("id");

		return jdbcTemplate.queryForMap(sql);
	}

}
