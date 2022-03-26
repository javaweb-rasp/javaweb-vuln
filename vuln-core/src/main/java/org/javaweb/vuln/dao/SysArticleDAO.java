package org.javaweb.vuln.dao;

import org.javaweb.vuln.entity.SysArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.apache.commons.lang.math.NumberUtils.isNumber;
import static org.javaweb.utils.StringUtils.getCurrentTime;
import static org.springframework.jdbc.core.BeanPropertyRowMapper.newInstance;

@Component
public class SysArticleDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private SysUserDAO sysUserDAO;

	@Autowired
	private SysCommentDAO sysCommentDAO;

	public List<SysArticle> getSysArticleList() {
		String           sql      = "select * from sys_article order by publish_date desc ";
		List<SysArticle> articles = jdbcTemplate.query(sql, newInstance(SysArticle.class));

		for (SysArticle article : articles) {
			String articleID = String.valueOf(article.getId());
			String userID    = String.valueOf(article.getUserId());

			article.setCommentCount(sysCommentDAO.getCommentCount(articleID));
			article.setSysComment(sysCommentDAO.getSysCommentList(articleID));
			article.setSysUser(sysUserDAO.getSysUserById(userID));
		}

		return articles;
	}

	public void updateClickCount(String id) {
		if (isNumber(id)) {
			String sql = "update sys_article set click_count = click_count +1 where id = " + id;
			jdbcTemplate.update(sql);
		}
	}

	public SysArticle getSysArticle(String id) {
		try {
			// 更新文章点击数
			updateClickCount(id);

			SysArticle article = jdbcTemplate.queryForObject(
					"select * from sys_article where id = " + id, newInstance(SysArticle.class)
			);

			String userId = String.valueOf(article.getUserId());

			article.setSysComment(sysCommentDAO.getSysCommentList(id));
			article.setSysUser(sysUserDAO.getSysUserById(userId));

			return article;
		} catch (DataAccessException e) {
			return null;
		}
	}

	public boolean addArticle(SysArticle article) {
		try {
			String sql = "insert into sys_article (user_id,title,author,content,publish_date,click_count) " +
					"values('" + article.getUserId() + "','" + article.getTitle() + "','" +
					article.getAuthor() + "','" + article.getContent() + "','" + getCurrentTime() + "', 0)";

			return jdbcTemplate.update(sql) > 0;
		} catch (DataAccessException e) {
			return false;
		}
	}

	public List<SysArticle> getSysArticleTopList(int size) {
		String sql = "select * from (select *,(select count(1) from sys_comment as c " +
				"where c.comment_article_id = a.id) as commentcount from sys_article as a ) as article" +
				" where article.commentcount >0 order by article.commentcount desc limit " + size;

		return jdbcTemplate.query(
				sql, new BeanPropertyRowMapper<SysArticle>(SysArticle.class)
		);
	}

}
