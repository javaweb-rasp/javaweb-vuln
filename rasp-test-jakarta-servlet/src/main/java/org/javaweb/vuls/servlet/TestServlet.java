package org.javaweb.vuls.servlet;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;

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
