package org.javaweb.vuls.servlet;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;

public class TestServlet extends HttpServlet {

	@Override
	public void service(ServletRequest req, ServletResponse resp) throws IOException {
		new RuntimeException().printStackTrace();
		PrintWriter out = resp.getWriter();
		out.write(this.getClass().getName());
		out.flush();
		out.close();
	}

}
