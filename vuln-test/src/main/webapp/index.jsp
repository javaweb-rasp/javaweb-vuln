<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.util.Map" %>
<%@page session="false" %>

<pre>
<%="ContextPath:" + request.getContextPath()%>
<%="RequestURL:" + request.getRequestURL()%>
<%="RequestURI:" + request.getRequestURI()%>
<%="ServletPath:" + request.getServletPath()%>
<%="PathInfo:" + request.getPathInfo()%>
<%="PathTranslated:" + request.getPathTranslated()%>
</pre>

<pre>
<%
    out.println(request.getParameter("id"));
    out.println("-------------------------------------------------");
    out.println(this.getClass().getClassLoader());
    out.println("-------------------------------------------------");
    Map<String, String> map = System.getenv();

    for (String k : map.keySet()) {
        out.println(k + ":" + map.get(k));
    }
%>
</pre>