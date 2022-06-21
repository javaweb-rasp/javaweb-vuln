<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.util.Map" %>
<%@ page import="java.util.Properties" %>
<%@ page import="java.util.Enumeration" %>
<%@page session="false" %>

<pre>
<%="ContextPath:" + request.getContextPath()%>
<%="RequestURL:" + request.getRequestURL()%>
<%="RequestURI:" + request.getRequestURI()%>
<%="ServletPath:" + request.getServletPath()%>
<%="PathInfo:" + request.getPathInfo()%>
<%="PathTranslated:" + request.getPathTranslated()%>

<%
    out.println("id: " + request.getParameter("id"));
    out.println("ClassLoader: " + this.getClass().getClassLoader());
    out.println("\n\n----------------------ENV----------------------\n\n");
    Map<String, String> map = System.getenv();

    for (String k : map.keySet()) {
        out.println(k + ": " + map.get(k));
    }

    out.println("\n\n----------------------Properties----------------------\n\n");
    Properties properties = System.getProperties();
    Enumeration<?> enumeration = properties.propertyNames();

    while (enumeration.hasMoreElements()) {
        Object k = enumeration.nextElement();
        out.println(k + ": " + properties.get(k));
    }

%>
</pre>