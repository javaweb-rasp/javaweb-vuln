package org.javaweb.vuln.controller;

import com.sun.org.apache.xerces.internal.dom.DOMInputImpl;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSParser;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.w3c.dom.ls.DOMImplementationLS.MODE_SYNCHRONOUS;

@Controller
@RequestMapping("/XXE/")
public class XXEController {

	@ResponseBody
	@PostMapping(value = "/xerces.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, Object> xerces(@RequestBody Map<String, Object> map) throws Exception {
		String                    xml      = (String) ((Map) map.get("root")).get("xml");
		Map<String, Object>       data     = new LinkedHashMap<String, Object>();
		ByteArrayInputStream      bais     = new ByteArrayInputStream(xml.getBytes("UTF-8"));
		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
		DOMImplementationLS       domImpl     = (DOMImplementationLS) registry.getDOMImplementation("LS");

		// 创建DOMBuilder
		LSParser     builder  = domImpl.createLSParser(MODE_SYNCHRONOUS, null);
		DOMInputImpl domInput = new DOMInputImpl();
		domInput.setByteStream(bais);

		org.w3c.dom.Document doc     = builder.parse(domInput);
		Element              element = doc.getDocumentElement();

		// 获取<title>标签
		NodeList titleTag = element.getElementsByTagName("title");

		// 解析<title>标签的值
		String value = titleTag.item(0).getFirstChild().getNodeValue();

		data.put("title", value);

		return data;
	}


	@RequestMapping("/dom4j.do")
	public ResponseEntity<byte[]> dom4j(String xml) throws Exception {
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

		return new ResponseEntity<byte[]>(out.toByteArray(), OK);
	}

}
