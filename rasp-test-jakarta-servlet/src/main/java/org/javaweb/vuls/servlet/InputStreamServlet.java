package org.javaweb.vuls.servlet;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class InputStreamServlet extends HttpServlet {

	@Override
	public void service(ServletRequest request, ServletResponse response) throws IOException {
		InputStream  in  = request.getInputStream();
		OutputStream out = response.getOutputStream();
		out.write((in.getClass().getName() + "\n").getBytes());
		out.write((out.getClass().getName() + "\n").getBytes());
		out.write(IOUtils.toByteArray(in));
		out.flush();
		out.close();
	}

}
