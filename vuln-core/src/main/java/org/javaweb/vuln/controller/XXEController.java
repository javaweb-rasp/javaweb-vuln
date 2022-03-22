package org.javaweb.vuln.controller;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.StringReader;

@Controller
@RequestMapping("/XXE/")
public class XXEController {

	@RequestMapping("/xxe.do")
	public ResponseEntity<byte[]> xxe(String xml) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

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

		return new ResponseEntity<byte[]>(out.toByteArray(), HttpStatus.OK);
	}

}
