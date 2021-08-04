<%@ page language="java" pageEncoding="UTF-8"%>
当前登录的用户为：${currentUser}<br><br>
<a href="${pageContext.request.contextPath}/user/info-anon.jsp" target="_blank">匿名用户可访问的页面</a><br><br>
<a href="${pageContext.request.contextPath}/user/info.jsp" target="_blank">普通用户可访问的页面</a><br><br>
<a href="${pageContext.request.contextPath}/admin/list.jsp" target="_blank">管理员可访问的页面</a><br><br>
<a href="${pageContext.request.contextPath}/demo/logout" target="_blank">Logout</a>