package org.javaweb.vuls.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * 自定义的指定Shiro验证用户登录的类
 * 这里定义了两个用户：admin（拥有admin角色和admin:manage权限）、normal（无任何角色和权限）
 */
public class MyRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String                  currentUsername  = (String) super.getAvailablePrincipal(principals);
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();

		if ("admin".equals(currentUsername)) {
			simpleAuthorInfo.addRole("admin");
			simpleAuthorInfo.addStringPermission("admin:manage");
			return simpleAuthorInfo;
		}

		if ("normal".equals(currentUsername)) {
			return simpleAuthorInfo;
		}

		return null;
	}

	/**
	 * 验证当前登录的Subject
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authToken;

		if ("admin".equals(token.getUsername())) {
			AuthenticationInfo authInfo = new SimpleAuthenticationInfo("admin", "admin", this.getName());
			this.setAuthenticationSession("admin");
			return authInfo;
		}

		if ("normal".equals(token.getUsername())) {
			AuthenticationInfo authInfo = new SimpleAuthenticationInfo("normal", "normal", this.getName());
			this.setAuthenticationSession("normal");
			return authInfo;
		}

		return null;
	}

	private void setAuthenticationSession(Object value) {
		Subject currentUser = SecurityUtils.getSubject();

		if (null != currentUser) {
			Session session = currentUser.getSession();
			session.setTimeout(1000 * 60 * 60 * 2);
			session.setAttribute("currentUser", value);
		}
	}

}