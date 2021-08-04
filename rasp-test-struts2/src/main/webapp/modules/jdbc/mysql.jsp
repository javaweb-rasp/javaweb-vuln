<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.Properties" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Properties pro = new Properties();
    pro.load(new FileInputStream(this.getClass().getClassLoader().getResource("/").getPath() + "config/jdbc.properties"));
    String driver = pro.getProperty("jdbc.driver");
    String url = pro.getProperty("jdbc.url");
    String username = pro.getProperty("jdbc.username");
    String password = pro.getProperty("jdbc.password");

    String sql = "select post_content from sys_posts where post_id =" + request.getParameter("id");
    Class.forName(driver);

    out.println("<p>" + sql + "</p>");

    Connection connection = DriverManager.getConnection(url, username, password);
    PreparedStatement pstt = connection.prepareStatement(sql);
    ResultSet rs = pstt.executeQuery();

    while (rs.next()) {
        out.println("<font color='red'>" + rs.getObject(1) + "</font>");
    }

    rs.close();
    pstt.close();
    connection.close();
%>