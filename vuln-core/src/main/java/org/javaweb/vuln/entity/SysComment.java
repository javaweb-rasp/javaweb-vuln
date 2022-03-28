package org.javaweb.vuln.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * Created by yz on 2016/12/17.
 */
@javax.persistence.Entity
@jakarta.persistence.Entity
@javax.persistence.Table(name = "sys_comment")
@jakarta.persistence.Table(name = "sys_comment")
public class SysComment implements Serializable {

	/**
	 * 评论Id
	 */
	@javax.persistence.Id
	@jakarta.persistence.Id
	@javax.persistence.Column(name = "comment_id")
	@jakarta.persistence.Column(name = "comment_id")
	private Long commentId;

	/**
	 * 评论者昵称
	 */
	@javax.persistence.Column(name = "comment_author")
	@jakarta.persistence.Column(name = "comment_author")
	private String commentAuthor;

	/**
	 * 评论内容
	 */
	@javax.persistence.Column(name = "comment_content")
	@jakarta.persistence.Column(name = "comment_content")
	private String commentContent;

	/**
	 * 评论发布时间
	 */
	@javax.persistence.Column(name = "comment_date")
	@jakarta.persistence.Column(name = "comment_date")
	private String commentDate;

	@javax.persistence.OneToOne
	@jakarta.persistence.OneToOne
	@javax.persistence.JoinColumn(name = "user_id")
	@jakarta.persistence.JoinColumn(name = "user_id")
	private SysUser sysUser;

	@JsonBackReference
	@javax.persistence.ManyToOne
	@jakarta.persistence.ManyToOne
	@javax.persistence.JoinColumn(name = "article_id")
	@jakarta.persistence.JoinColumn(name = "article_id")
	private SysArticle sysArticle;

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public String getCommentAuthor() {
		return commentAuthor;
	}

	public void setCommentAuthor(String commentAuthor) {
		this.commentAuthor = commentAuthor;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public SysArticle getSysArticle() {
		return sysArticle;
	}

	public void setSysArticle(SysArticle sysArticle) {
		this.sysArticle = sysArticle;
	}

}