package org.javaweb.vuln.entity;

/**
 * Creator: yz
 * Date: 2020-05-05
 */
@javax.persistence.Entity
@jakarta.persistence.Entity
@javax.persistence.Table(name = "sys_user")
@jakarta.persistence.Table(name = "sys_user")
public class SysUser {

	@javax.persistence.Id
	@jakarta.persistence.Id
	@javax.persistence.Column(name = "user_id")
	@jakarta.persistence.Column(name = "user_id")
	private Long userId;

	private String username;

	private String password;

	private String email;

	@javax.persistence.Column(name = "user_avatar")
	@jakarta.persistence.Column(name = "user_avatar")
	private String userAvatar;

	@javax.persistence.Column(name = "register_time")
	@jakarta.persistence.Column(name = "register_time")
	private String registerTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

}
