package org.javaweb.vuln.dao;

import org.javaweb.vuln.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static org.javaweb.utils.StringUtils.getCurrentTime;
import static org.springframework.jdbc.core.BeanPropertyRowMapper.newInstance;

/**
 * Creator: yz
 * Date: 2020-05-05
 */
@Component
public class SysUserDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public SysUser getSysUserByUsername(String username) {
		try {
			String sql = "select * from sys_user where username = '" + username + "'";

			return jdbcTemplate.queryForObject(sql, newInstance(SysUser.class));
		} catch (DataAccessException e) {
			return null;
		}
	}

	public SysUser getSysUserById(String id) {
		try {
			String sql = "select * from sys_user where id = '" + id + "'";

			return jdbcTemplate.queryForObject(sql, newInstance(SysUser.class));
		} catch (DataAccessException e) {
			return null;
		}
	}

	public SysUser login(String username, String password) {
		try {
			String sql = "select * from sys_user where username = '" + username + "' and password = '" + password + "'";

			return jdbcTemplate.queryForObject(sql, newInstance(SysUser.class));
		} catch (DataAccessException e) {
			return null;
		}
	}

	public int register(SysUser u) {
		String defaultAvatar = "/res/images/avatar/default.png";

		String sql = "insert into sys_user (username, password, email, user_avatar, register_time) values" +
				" ('" + u.getUsername() + "', '" + u.getPassword() + "', '" + u.getEmail() + "', '" +
				defaultAvatar + "', '" + getCurrentTime() + "')";

		return jdbcTemplate.update(sql);
	}

}
