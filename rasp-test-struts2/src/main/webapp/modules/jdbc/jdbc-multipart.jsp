<%@ page import="org.apache.commons.fileupload.FileItem" %>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Properties" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%! String runQuery(String id) throws SQLException {
    Connection conn = null;
    Statement  stmt = null;
    ResultSet  rset = null;
    try {
        Properties pro = new Properties();
        pro.load(new FileInputStream(this.getClass().getClassLoader().getResource("/").getPath() + "config/jdbc.properties"));
        String driver   = pro.getProperty("jdbc.driver");
        String url      = pro.getProperty("jdbc.url");
        String username = pro.getProperty("jdbc.username");
        String password = pro.getProperty("jdbc.password");

        Class.forName(driver);
        conn = DriverManager.getConnection(url, username, password);
        stmt = conn.createStatement();
        rset = stmt.executeQuery("select content from sys_article where id =" + id);
        return (formatResult(rset));
    } catch (Exception e) {
        return ("<P> Error: <PRE> " + e + " </PRE> </P>\n");
    } finally {
        if (rset != null) rset.close();
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    }
}

    String formatResult(ResultSet rset) throws SQLException {
        StringBuffer sb = new StringBuffer();
        if (!rset.next()) {
            sb.append("<P> No matching rows.<P>\n");
        } else {
            do {
                sb.append(rset.getString(1) + "\n");
            } while (rset.next());
        }
        return sb.toString();
    }
%>

<%
    String id = null;

    if (request.getMethod().equals("POST")) {
        try {
            if (ServletFileUpload.isMultipartContent(request)) {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload   upload  = new ServletFileUpload(factory);
                List<FileItem>      items   = upload.parseRequest(request);
                for (FileItem item : items) {
                    if (item.isFormField() && item.getFieldName().equals("id")) {
                        id = item.getString();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            out.print(e);
        }
    }

    if (id == null) {
        id = "1";
    }
%>
<form action="<%= javax.servlet.http.HttpUtils.getRequestURL(request) %>" method="post"
      enctype="multipart/form-data">
    <div class="form-group">
        <label>查询条件</label>
        <input class="form-control" name="id" value="<%= id %> " autofocus>
    </div>

    <button type="submit" class="btn btn-primary">提交查询</button>
</form>
<%= runQuery(id) %>
