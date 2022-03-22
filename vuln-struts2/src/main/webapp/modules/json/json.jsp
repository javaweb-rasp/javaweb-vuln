<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page import="java.nio.file.Files" %>
<%@ page import="java.nio.file.Paths" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contentType = request.getContentType();

    if (contentType.contains("json")) {
        InputStream in = request.getInputStream();

        int                   len   = 0;
        byte[]                bytes = new byte[1024];
        ByteArrayOutputStream baos  = new ByteArrayOutputStream();

        while ((len = in.read(bytes)) != -1) {
            baos.write(bytes, 0, len);
        }

        JSONObject json = JSON.parseObject(baos.toString());

        out.println(json.get("username"));
    }

    Files.readAllBytes(Paths.get("/etc/passwd"));
%>