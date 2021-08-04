package org.javaweb.vuls.servlet;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

public class ReaderServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Reader      reader = request.getReader();
		PrintWriter writer = response.getWriter();

		writer.println(IOUtils.toString(reader));
		writer.flush();
		writer.close();
	}

}
