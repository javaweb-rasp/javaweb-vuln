package org.javaweb.vuls.controller;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringReader;

@Controller
@RequestMapping("/XXE/")
public class XXEController {

	@RequestMapping("/xxe.do")
	public void xxe(String xml, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();

		if (xml != null && !"".equals(xml)) {
			SAXReader    reader = new SAXReader();
			StringReader in     = new StringReader(xml);
			Document     doc    = reader.read(in);
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");

			XMLWriter writer = new XMLWriter(out, format);
			writer.write(doc);
			writer.flush();
			writer.close();
		}
	}

}
