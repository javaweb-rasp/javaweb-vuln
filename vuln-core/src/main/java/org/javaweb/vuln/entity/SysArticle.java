package org.javaweb.vuln.entity;

import java.util.List;

/**
 * Creator: yz
 * Date: 2020-05-04
 */
public class SysArticle {

	private Long id;

	private Long userId;

	private String title;

	private String author;

	private String content;

	private String publishDate;

	private Long clickCount;

	private SysUser sysUser;

	private Integer commentCount = 0;

	private List<SysComment> sysComment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public List<SysComment> getSysComment() {
		return sysComment;
	}

	public void setSysComment(List<SysComment> sysComment) {
		this.sysComment = sysComment;
	}

}
