package org.javaweb.vuln.dao;

import org.javaweb.vuln.entity.SysArticle;
import org.javaweb.vuln.entity.SysComment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static org.javaweb.utils.StringUtils.getCurrentTime;

@Component
public class SysCommentDAO {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private SysUserDAO sysUserDAO;

	public List<SysComment> getSysCommentList(String articleId) {
		String sql = "select * from sys_comment where comment_article_id='" +
				articleId + "' order by comment_date desc ";

		List<SysComment> commentList = jdbcTemplate.query(
				sql, new BeanPropertyRowMapper<SysComment>(SysComment.class)
		);

		for (SysComment comment : commentList) {
			comment.setSysUser(sysUserDAO.getSysUserById(String.valueOf(comment.getCommentUserId())));
		}

		return commentList;
	}

	public boolean addSysComments(SysComment comments) {
		try {
			String sql = "insert into sys_comment (comment_article_id,comment_user_id,comment_author," +
					"comment_content, comment_date) values('" + comments.getCommentArticleId() +
					"','" + comments.getCommentUserId() + "','" + comments.getCommentAuthor() +
					"','" + comments.getCommentContent() + "','" + getCurrentTime() + "')";

			return jdbcTemplate.update(sql) == 1;
		} catch (DataAccessException e) {
			return false;
		}
	}

	public Integer getCommentCount(String articleID) {
		String sql = "select count(1) from sys_comment where comment_article_id = " + articleID;

		try {
			return jdbcTemplate.queryForObject(sql, Integer.class);
		} catch (DataAccessException e) {
			return 0;
		}
	}

}
