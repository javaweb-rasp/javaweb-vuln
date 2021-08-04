<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.io.File" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.net.URLClassLoader" %>
<%@ page import="java.lang.reflect.Method" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.management.ManagementFactory" %>
<%
    String jdiJVMKey = "__JDI__JVM__";
    Object jvm = application.getAttribute(jdiJVMKey);

    if (jvm == null) {
        String         classLoaderKey = "__JDI__CLASSLOADER__";
        URLClassLoader classLoader    = (URLClassLoader) application.getAttribute(classLoaderKey);

        if (classLoader == null) {
            File  toolsJar = new File(System.getenv("JAVA_HOME"), "lib/tools.jar");
            URL[] urls     = new URL[]{toolsJar.toURI().toURL()};

            classLoader = new URLClassLoader(urls);
            application.setAttribute(classLoaderKey, classLoader);
        }

        try {
            Class<?> bootClass  = classLoader.loadClass("com.sun.jdi.Bootstrap");
            Object   vmManager  = bootClass.getMethod("virtualMachineManager").invoke(null);
            List<?>  connectors = (List<?>) vmManager.getClass().getMethod("attachingConnectors").invoke(vmManager);

            String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];

            for (Object connector : connectors) {
                if (connector.getClass().getName().equals("com.sun.jdi.ProcessAttach")) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    out.println(jvm);

%>