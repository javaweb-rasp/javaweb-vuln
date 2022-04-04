package org.javaweb.vuln.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

/**
 * Creator: yz
 * Date: 2020-05-04
 */
@javax.persistence.Entity
@jakarta.persistence.Entity
@javax.persistence.Table(name = "sys_article")
@jakarta.persistence.Table(name = "sys_article")
public class SysArticle {

	@javax.persistence.Id
	@jakarta.persistence.Id
	@javax.persistence.Column(name = "article_id")
	@jakarta.persistence.Column(name = "article_id")
	private Long articleId;

	private String title;

	private String author;

	private String content;

	@javax.persistence.Column(name = "publish_date")
	@jakarta.persistence.Column(name = "publish_date")
	private String publishDate;

	@javax.persistence.Column(name = "click_count")
	@jakarta.persistence.Column(name = "click_count")
	private Long clickCount;

	private transient Long userId;

	@javax.persistence.ManyToOne
	@jakarta.persistence.ManyToOne
	@javax.persistence.JoinColumn(name = "user_id")
	@jakarta.persistence.JoinColumn(name = "user_id")
	private SysUser sysUser;

	@JsonManagedReference
	@javax.persistence.OneToMany(fetch = javax.persistence.FetchType.EAGER)
	@javax.persistence.JoinColumn(name = "article_id")
	@jakarta.persistence.OneToMany(fetch = jakarta.persistence.FetchType.EAGER)
	@jakarta.persistence.JoinColumn(name = "article_id")
	private List<SysComment> sysComment;

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public Long getClickCount() {
		return clickCount;
	}

	public void setClickCount(Long clickCount) {
		this.clickCount = clickCount;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<SysComment> getSysComment() {
		return sysComment;
	}

	public void setSysComment(List<SysComment> sysComment) {
		this.sysComment = sysComment;
	}

}
