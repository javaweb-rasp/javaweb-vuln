package org.javaweb.vuls.servlet;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

public class ReaderServlet extends HttpServlet {

	@Override
	public void service(ServletRequest request, ServletResponse response) throws IOException {
		Reader      reader = request.getReader();
		PrintWriter writer = response.getWriter();
		writer.println(reader.getClass().getName() + "\n");
		writer.println(writer.getClass().getName() + "\n");
		writer.println(IOUtils.toString(reader));
		writer.flush();
		writer.close();
	}

}
