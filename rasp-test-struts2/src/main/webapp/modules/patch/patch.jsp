<%@ page import="org.apache.commons.codec.binary.Base64" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>动态补丁拦截测试测试</title>
</head>
<%
    String string = "[{\"des\":\"SQL Patch\",\"create_time\":1.587114322E9,\"harmLevel\":2.0,\"user_id\":\"1111\",\"site_id\":1.0,\"scriptName\":\"/modules/jdbc/mysql.jsp\",\"systemPatch\":0.0,\"trigger\":\"[{\\\"triggerIndex\\\":1,\\\"key\\\":\\\"id\\\",\\\"keyValMethod\\\":\\\"preg\\\",\\\"val\\\":\\\".*\\\"}]\",\"title\":\"SQL Injection\",\"hotfixId\":2.0,\"content\":\"[{\\\"content_funArgs\\\":[{\\\"before\\\":\\\"(?=\\\\\\\\D)(?<=\\\\\\\\d{1,}).*\\\",\\\"after\\\":\\\" \\\"}],\\\"contentIndex\\\":2,\\\"param\\\":\\\"id\\\",\\\"functionName\\\":\\\"preg_replace\\\"}]\"},{\"des\":\"Directory Patch\",\"create_time\":1.587117135E9,\"harmLevel\":1.0,\"user_id\":\"1111\",\"site_id\":1.0,\"scriptName\":\"/modules/servlet/xss.jsp\",\"systemPatch\":0.0,\"trigger\":\"[{\\\"triggerIndex\\\":1,\\\"key\\\":\\\"input\\\",\\\"keyValMethod\\\":\\\"like\\\",\\\"val\\\":\\\"script\\\"}]\",\"title\":\"Directory\",\"hotfixId\":3.0,\"content\":\"[{\\\"content_funArgs\\\":[],\\\"contentIndex\\\":2,\\\"param\\\":\\\"input\\\",\\\"functionName\\\":\\\"addslashes\\\"}]\"},{\"des\":\"Command Patch\",\"create_time\":1.587117561E9,\"harmLevel\":2.0,\"user_id\":\"1111\",\"site_id\":1.0,\"scriptName\":\"/modules/cmd/cmmd.jsp\",\"systemPatch\":0.0,\"trigger\":\"[{\\\"triggerIndex\\\":\\\"1\\\",\\\"key\\\":\\\"cmd\\\",\\\"keyValMethod\\\":\\\"equal\\\",\\\"val\\\":\\\"id\\\"}]\",\"title\":\"Command\",\"hotfixId\":4.0,\"content\":\"[{\\\"content_funArgs\\\":[],\\\"contentIndex\\\":2,\\\"param\\\":\\\"cmd\\\",\\\"functionName\\\":\\\"deny\\\"}]\"}]";
    String patch = new String(Base64.encodeBase64(string.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
%>
<body>
<div align="center">
在本站点的配置文件中，patch_list= 字段添加如下配置，以进行以下场景的补丁拦截测试：<br>
<textarea cols="80" rows="25"><%= patch%></textarea><br>
<b>1. 使用补丁拦截防御整型注入</b><br>
补丁使用正则替换，将数字型参数后面可能出现的攻击payload替换为空。<br>
添加配置后，访问以下页面观看效果：<a href="../jdbc/mysql.jsp?id=6+or+1=1+--+-">整型注入测试</a><br>
<b>2. 使用补丁替换标签防止 XSS 代码。</b><br>
补丁使用字符串判断，如果包含script标签字样，将对参数内容进行HTML编码。<br>
添加配置后，访问以下页面观看效果：<a href="../servlet/xss.jsp?input=%3cscript%3ealert(1)%3c%2fscript%3e">XSS测试</a><br>
<b>3. 使用补丁禁止执行 id 命令。</b><br>
补丁使用字符串判断，执行了 id 命令，将拦截此项请求。<br>
添加配置后，访问以下页面观看效果：<a href="../cmd/cmmd.jsp?cmd=id">命令测试</a><br>


以上用例仅为理想环境下测试使用，在真实环境下请根据实际情况进行处置。
</div>
</body>
</html>