package org.javaweb.vuls.servlet;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class InputStreamServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		InputStream  in  = request.getInputStream();
		OutputStream out = response.getOutputStream();
		out.write(IOUtils.toByteArray(in));
		out.flush();
		out.close();
	}

}
