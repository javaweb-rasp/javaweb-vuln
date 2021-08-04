<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.io.File" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.net.URLClassLoader" %>
<%@ page import="java.lang.reflect.Method" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
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

            if (connectors.size() > 0) {
                Object   connector      = connectors.get(0);
                Class<?> connectorClass = connector.getClass();
                Map      argMap         = (Map) connectorClass.getMethod("defaultArguments").invoke(connector);
                Method   getMethod      = Map.class.getDeclaredMethod("get", Object.class);

                Object hostname       = getMethod.invoke(argMap, "hostname");
                Object port           = getMethod.invoke(argMap, "port");
                Method setValueMethod = hostname.getClass().getMethod("setValue", String.class);
                setValueMethod.setAccessible(true);

                setValueMethod.invoke(hostname, "localhost");
                setValueMethod.invoke(port, "5005");

                jvm = connectorClass.getMethod("attach", Map.class).invoke(connector, argMap);

                application.setAttribute(jdiJVMKey, jvm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    out.println(jvm);

    Class<?> jvmClass = jvm.getClass();
    Method disposeMethod = jvmClass.getMethod("dispose");
    disposeMethod.setAccessible(true);
    disposeMethod.invoke(jvm);
%>