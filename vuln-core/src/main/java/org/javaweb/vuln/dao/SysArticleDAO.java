package org.javaweb.vuln.dao;

import org.javaweb.vuln.entity.SysArticle;
import org.javaweb.vuln.repository.SysArticleRepository;
import org.javaweb.vuln.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

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

	@Autowired
	private SysArticleRepository sysArticleRepository;

	@Autowired
	private SysUserRepository sysUserRepository;

	public Page<SysArticle> getSysArticleList(Pageable pageable) {
		return sysArticleRepository.findAllByOrderByPublishDateDesc(pageable);
	}

	public SysArticle getSysArticle(String id) {
		try {
			// 更新文章点击数
			sysArticleRepository.updateSysArticleClickCount(id);

			SysArticle article = jdbcTemplate.queryForObject(
					"select * from sys_article where id = " + id, newInstance(SysArticle.class)
			);

			String userId = String.valueOf(article.getSysUser().getUserId());

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
					"values('" + article.getSysUser().getUserId() + "','" + article.getTitle() + "','" +
					article.getAuthor() + "','" + article.getContent() + "','" + getCurrentTime() + "', 0)";

			return jdbcTemplate.update(sql) > 0;
		} catch (DataAccessException e) {
			return false;
		}
	}

	public List<SysArticle> getSysArticleTops(int size) {
		return sysArticleRepository.getSysArticleTops(size);
	}

}
